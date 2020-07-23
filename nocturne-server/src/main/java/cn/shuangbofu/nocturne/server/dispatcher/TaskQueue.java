package cn.shuangbofu.nocturne.server.dispatcher;

import cn.shuangbofu.nocturne.core.domain.TaskInfo;

/**
 * Created by shuangbofu on 2020/7/22 14:39
 */
public interface TaskQueue {

    void put(TaskInfo info);

    TaskInfo get();

    void clear();

    void remove(String key);

    int size();
}
