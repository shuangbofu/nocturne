package cn.shuangbofu.nocturne.server.dispatcher.selector;

import cn.shuangbofu.nocturne.core.domain.ExecutorEntry;
import cn.shuangbofu.nocturne.server.service.ExecutorStore;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by shuangbofu on 2020/7/25 下午8:34
 */
public class RandomExecutorSelector implements ExecutorSelector {
    private static final RandomExecutorSelector SELECTOR = new RandomExecutorSelector();

    public static RandomExecutorSelector getInstance() {
        return SELECTOR;
    }

    @Override
    public ExecutorEntry select(long executorGroupId) {
        List<ExecutorEntry> entries = ExecutorStore.INSTANCE.getExecutorEntries(1);
        if (entries.size() > 0) {
            return entries.get(ThreadLocalRandom.current().nextInt(0, entries.size()));
        }
        return null;
    }
}
