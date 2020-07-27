package cn.shuangbofu.nocturne.server.dispatcher.selector;

import cn.shuangbofu.nocturne.core.domain.ExecutorEntry;

/**
 * Created by shuangbofu on 2020/7/22 16:03
 */
public interface ExecutorSelector {

    ExecutorEntry select(long executorGroupId);
}
