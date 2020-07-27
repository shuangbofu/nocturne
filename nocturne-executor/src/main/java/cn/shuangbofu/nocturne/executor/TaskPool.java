package cn.shuangbofu.nocturne.executor;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;

/**
 * Created by shuangbofu on 2020/7/25 下午8:03
 */
public enum TaskPool {
    /**
     *
     */
    INSTANCE;
    Map<Long, Object> pool = Maps.newConcurrentMap();

    public int size() {
        return pool.size();
    }

    public void put(Long id) {
        pool.put(id, "test");
    }

    public void remove(Long id) {
        pool.remove(id);
    }

    public List<Long> getTaskList() {
        return Lists.newArrayList(pool.keySet());
    }
}
