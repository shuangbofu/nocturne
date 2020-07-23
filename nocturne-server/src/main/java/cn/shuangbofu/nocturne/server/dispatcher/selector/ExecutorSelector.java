package cn.shuangbofu.nocturne.server.dispatcher.selector;

import cn.shuangbofu.nocturne.core.domain.ExecutorInfo;

/**
 * Created by shuangbofu on 2020/7/22 16:03
 */
public interface ExecutorSelector {

    ExecutorInfo select(long executorGroupId);
}
