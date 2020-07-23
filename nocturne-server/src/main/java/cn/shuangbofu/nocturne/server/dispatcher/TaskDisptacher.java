package cn.shuangbofu.nocturne.server.dispatcher;

import cn.shuangbofu.nocturne.core.domain.ExecutorInfo;
import cn.shuangbofu.nocturne.core.domain.RetryType;
import cn.shuangbofu.nocturne.core.domain.TaskInfo;
import cn.shuangbofu.nocturne.server.dispatcher.selector.ExecutorSelector;
import cn.shuangbofu.nocturne.server.guice.Injectors;
import cn.shuangbofu.nocturne.server.scheduler.task.TaskRetryScheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by shuangbofu on 2020/7/22 13:22
 */
public enum TaskDisptacher {

    /**
     *
     */
    INSTANCE;
    protected static final Logger LOGGER = LoggerFactory.getLogger(TaskDisptacher.class);

    private final TaskQueues queues = Injectors.getInstance(TaskQueues.class);
    private final ExecutorSelector executorSelector = Injectors.getInstance(ExecutorSelector.class);
    public boolean running = false;

    public void start(int threadNum) {
        running = true;
        ExecutorService executorService = Executors.newFixedThreadPool(threadNum);
        for (int i = 0; i < threadNum; i++) {
            executorService.submit(new DispatcherThread());
        }
        executorService.shutdown();
    }

    public void shutdown() {
        running = false;
    }

    public void run0() {
        // TODO 任务分发
        try {
            TaskInfo info = queues.get();
            if (info == null) {
                return;
            }
            long executorGroupId = info.getExecutorGroupId();
            ExecutorInfo executorInfo = executorSelector.select(executorGroupId);

            // 没有可用executor重试
            if (executorInfo == null) {
                TaskRetryScheduler.INSTANCE.addTask(info, RetryType.AUTO);
            }

        } catch (Exception e) {
            LOGGER.error("任务分发出错", e);
        }
    }

    class DispatcherThread extends Thread {
        @Override
        public void run() {
            while (running) {
                run0();
            }
            LOGGER.info("分发器停止");
        }
    }
}
