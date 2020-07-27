package cn.shuangbofu.nocturne.server.scheduler.task;

import cn.shuangbofu.nocturne.core.domain.RetryType;
import cn.shuangbofu.nocturne.core.domain.TaskInfo;
import cn.shuangbofu.nocturne.server.dispatcher.TaskManager;
import cn.shuangbofu.nocturne.server.dispatcher.TaskQueues;
import cn.shuangbofu.nocturne.server.guice.Injectors;
import cn.shuangbofu.nocturne.server.scheduler.GlobalController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by shuangbofu on 2020/7/22 16:08
 */
public enum TaskRetryScheduler {
    /**
     *
     */
    INSTANCE;
    protected static final Logger LOGGER = LoggerFactory.getLogger(TaskRetryScheduler.class);
    private final TaskManager taskManager = Injectors.getInstance(TaskManager.class);
    private final TaskQueues queues = Injectors.getInstance(TaskQueues.class);
    private final GlobalController controller = GlobalController.getInstance();
    private volatile boolean running;
    private int rejectRetryInterval;
    private int autoRetryInterval;

    public void addTask(TaskInfo info, RetryType retryType) {

    }

    public void start(int rejectRetryInterval, int autoRetryInterval) {
        running = true;
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(new RetryThread());
        executorService.shutdown();
        LOGGER.info("Task retry scheduler started.");
        this.rejectRetryInterval = rejectRetryInterval;
        this.autoRetryInterval = autoRetryInterval;
    }

    public void shutdown() {
        running = false;
    }


    class RetryThread extends Thread {
        @Override
        public void run() {
            while (running) {
                // TODO 根据重试次数，间隔处理

            }
        }
    }
}
