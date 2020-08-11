package cn.shuangbofu.nocturne.dao.loader;

import cn.shuangbofu.nocturne.dao.Executor;
import cn.shuangbofu.nocturne.dao.ExecutorGroup;
import cn.shuangbofu.nocturne.dao.enums.ExecutorGroupStatus;
import io.github.biezhi.anima.Anima;
import io.github.biezhi.anima.core.AnimaQuery;

import java.util.List;
import java.util.UUID;

/**
 * Created by shuangbofu on 2020/7/29 下午2:28
 */
public class ExecutorLoader {

    private static AnimaQuery<Executor> executorFrom() {
        return Anima.select().from(Executor.class);
    }

    private static AnimaQuery<ExecutorGroup> groupFrom() {
        return Anima.select().from(ExecutorGroup.class)
                .notEq(ExecutorGroup::getStatus, ExecutorGroupStatus.DELETED);
    }

    public static List<Executor> listAllExecutors() {
        return executorFrom().all();
    }

    public static List<ExecutorGroup> listAllGroups() {
        return groupFrom().all();
    }

    public static ExecutorGroup getGroupByKey(String authKey) {
        return groupFrom().where(ExecutorGroup::getAuthKey, authKey).one();
    }

    public static void createGroup(String name) {
        new ExecutorGroup()
                .setName(name)
                .setAuthKey(UUID.randomUUID().toString().replace("-", ""))
                .setStatus(ExecutorGroupStatus.ENABLED)
        ;
    }
}

