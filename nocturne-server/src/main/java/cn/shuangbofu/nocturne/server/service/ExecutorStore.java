package cn.shuangbofu.nocturne.server.service;

import cn.shuangbofu.nocturne.core.constant.ConfigKeys;
import cn.shuangbofu.nocturne.core.domain.ExecutorEntry;
import cn.shuangbofu.nocturne.core.netty.channel.RequestChannel;
import cn.shuangbofu.nocturne.server.NocturneServer;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Created by shuangbofu on 2020/7/24 下午10:16
 */
public enum ExecutorStore {
    /**
     *
     */
    INSTANCE;

    private static final Logger LOGGER = LoggerFactory.getLogger(ExecutorStore.class);
    private static final int MAX_HEART_BEAT_EXPIRE_SECONDS = 10;
    private final Map<RequestChannel, ExecutorEntry> executorEntries = Maps.newConcurrentMap();
    private final Map<Integer, Cache<ExecutorEntry, List<Long>>> CACHES = Maps.newConcurrentMap();
    private final Set<ExecutorEntry> OFFLINE_EXECUTORS = Sets.newConcurrentHashSet();

    public void register(RequestChannel channel, String serverKey, String executorKey) {
        String key = NocturneServer.CONFIG.getString(ConfigKeys.NOCTURNE_SERVER_KEY);
        if (!key.equals(serverKey)) {
            throw new RuntimeException("server key not supported");
        }
        // TODO 根据 executorKey get GroupId
        int groupId = executorKey.hashCode();

        ExecutorEntry executorEntry = new ExecutorEntry(channel, groupId);
        executorEntries.put(channel, executorEntry);
        LOGGER.info("Executor({})注册！", executorEntry);
        printAll();
    }

    public void heartbeat(RequestChannel channel, List<Long> tasksList) {
        ExecutorEntry executorEntry = executorEntries.get(channel);
        if (executorEntry != null) {
            int groupId = executorEntry.getGroupId();
            Cache<ExecutorEntry, List<Long>> cache = CACHES.get(groupId);
            if (cache == null) {
                cache = CacheBuilder.newBuilder().expireAfterAccess(MAX_HEART_BEAT_EXPIRE_SECONDS, TimeUnit.SECONDS).build();
                CACHES.put(groupId, cache);
            }
            cache.put(executorEntry, tasksList);
        }
    }

    public synchronized List<ExecutorEntry> getExecutorEntries(int groupId) {
        if (!CACHES.containsKey(groupId)) {
            return Lists.newArrayList();
        }
        Cache<ExecutorEntry, List<Long>> cache = CACHES.get(groupId);
        return Lists.newArrayList(cache.asMap().keySet());
    }

    public void unregister(RequestChannel channel) {
        ExecutorEntry removed = executorEntries.remove(channel);
        if (removed != null) {
            LOGGER.info("Executor({})注销！", removed);
            printAll();
        }
    }

    public void printAll() {
        LOGGER.info("=======当前已注册Executors({})=======", executorEntries.values().size());
        for (ExecutorEntry info : executorEntries.values()) {
            LOGGER.info(info.toString());
        }
        LOGGER.info("===================================");
    }
}
