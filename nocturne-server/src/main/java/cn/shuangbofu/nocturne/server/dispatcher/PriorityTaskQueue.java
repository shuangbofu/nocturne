package cn.shuangbofu.nocturne.server.dispatcher;


import cn.shuangbofu.nocturne.core.domain.TaskInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.PriorityBlockingQueue;

/**
 * Created by shuangbofu on 2020/7/22 14:38
 */
public class PriorityTaskQueue implements TaskQueue {
    protected static final Logger LOGGER = LoggerFactory.getLogger(PriorityTaskQueue.class);
    private final PriorityBlockingQueue<TaskInfo> queue = new PriorityBlockingQueue<>(100, (t1, t2) -> t2.getPriority() - t1.getPriority());

    @Override
    public void put(TaskInfo info) {
        queue.put(info);
    }

    @Override
    public TaskInfo get() {
        TaskInfo info;
        try {
            info = queue.take();
            LOGGER.debug("take task[{}] from queue.", info.getId());
            return info;
        } catch (InterruptedException e) {
            LOGGER.error("", e);
        }
        return null;
    }

    @Override
    public void clear() {
        queue.clear();
    }

    @Override
    public void remove(String key) {
        queue.removeIf(info -> info.getId().equalsIgnoreCase(key));
    }

    @Override
    public int size() {
        return queue.size();
    }
}
