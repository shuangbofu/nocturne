package cn.shuangbofu.nocturne.server.dispatcher;

import cn.shuangbofu.nocturne.core.NamedThreadFactory;
import cn.shuangbofu.nocturne.core.domain.ExecutorEntry;
import cn.shuangbofu.nocturne.core.domain.RetryType;
import cn.shuangbofu.nocturne.core.domain.TaskInfo;
import cn.shuangbofu.nocturne.core.netty.message.ResponseMessage;
import cn.shuangbofu.nocturne.core.netty.protobuf.RequestFactory;
import cn.shuangbofu.nocturne.protobuf.TaskProto;
import cn.shuangbofu.nocturne.server.dispatcher.selector.ExecutorSelector;
import cn.shuangbofu.nocturne.server.guice.Injectors;
import cn.shuangbofu.nocturne.server.scheduler.task.TaskRetryScheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

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
    private final ExecutorSelector selector = Injectors.getInstance(ExecutorSelector.class);
    private final TaskRetryScheduler retryScheduler = TaskRetryScheduler.INSTANCE;
    public boolean running = false;

    public void start(int threadNum) {
        running = true;
        ExecutorService executorService = new ThreadPoolExecutor(threadNum, threadNum,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(), new NamedThreadFactory("task-dispatcher"));
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
            TaskInfo task = queues.get();
            if (task == null) {
                return;
            }
            long executorGroupId = task.getExecutorGroupId();
            ExecutorEntry entry = selector.select(executorGroupId);

            // 没有可用executor重试
            if (entry == null) {
                retryScheduler.addTask(task, RetryType.AUTO);
                LOGGER.warn("没有找到可用的执行服务器！任务「{}」重新进入队列", task.getId());
                return;
            }

            LOGGER.info("任务「{}」出队列分发", task.getId());

            LOGGER.info("选择执行服务器 「{}」", entry);
            ResponseMessage response = entry.getChannel().request(RequestFactory.submitTaskRequest(task.getId(), task.getName()));

            if (!response.isSuccess()) {
                LOGGER.error("异常:{}", response.getMessage());
                LOGGER.info("任务「{}」出错，重新入队列", task.getId());
                retryScheduler.addTask(task, RetryType.FAIL);
                return;
            }

            TaskProto.SubmitTaskResponse res = response.getData(TaskProto.SubmitTaskResponse.class);

            if (!res.getAccept()) {
                retryScheduler.addTask(task, RetryType.REJECT);
                LOGGER.warn("任务「{}」被拒绝，重新进入队列", task.getId());
                return;
            }

            LOGGER.info("任务「{}」接收成功！", task.getId());

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
