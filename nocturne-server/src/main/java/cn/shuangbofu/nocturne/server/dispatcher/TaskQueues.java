package cn.shuangbofu.nocturne.server.dispatcher;

import cn.shuangbofu.nocturne.core.domain.TaskInfo;
import cn.shuangbofu.nocturne.core.domain.TaskType;
import cn.shuangbofu.nocturne.core.utils.DTUtil;
import com.google.common.collect.Lists;
import com.google.inject.Singleton;

import java.util.List;

/**
 * Created by shuangbofu on 2020/7/22 15:10
 */
@Singleton
public class TaskQueues implements TaskQueue {
    private final TaskQueue tmpTaskQueue = new PriorityTaskQueue();
    private final TaskQueue scheduleTaskQueue = new PriorityTaskQueue();
    private final TaskQueue rerunTaskQueue = new PriorityTaskQueue();

    @Override
    public void put(TaskInfo info) {
        getSpecificQueue(info.getTaskType())
                .put(info);
    }

    @Override
    public TaskInfo get() {
        // TODO 队列优先级
        List<TaskQueue> taskQueues = Lists.newArrayList(scheduleTaskQueue, tmpTaskQueue);
        return DTUtil.ofElse(roundRobinGet(taskQueues).get(), rerunTaskQueue.get());
    }

    @Override
    public void clear() {
        tmpTaskQueue.clear();
        scheduleTaskQueue.clear();
        rerunTaskQueue.clear();
    }

    @Override
    public void remove(String key) {
        Lists.newArrayList(tmpTaskQueue, scheduleTaskQueue, rerunTaskQueue).forEach(i -> i.remove(key));
    }

    @Override
    public int size() {
        return tmpTaskQueue.size() +
                scheduleTaskQueue.size() +
                rerunTaskQueue.size();
    }

    private TaskQueue getSpecificQueue(TaskType type) {
        switch (type) {
            case TMP:
                return tmpTaskQueue;
            case SCHEDULE:
                return scheduleTaskQueue;
            case RERUN:
                return rerunTaskQueue;
            default:
                throw new RuntimeException("not found queue");
        }
    }

    /**
     * 轮询
     *
     * @param queueList
     * @return
     */
    public TaskQueue roundRobinGet(List<TaskQueue> queueList) {
        return queueList.get(0);
    }
}
