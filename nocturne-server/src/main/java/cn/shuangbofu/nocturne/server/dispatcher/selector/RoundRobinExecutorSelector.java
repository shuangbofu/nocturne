package cn.shuangbofu.nocturne.server.dispatcher.selector;

import cn.shuangbofu.nocturne.core.domain.ExecutorInfo;
import cn.shuangbofu.nocturne.core.utils.DTUtil;
import cn.shuangbofu.nocturne.server.service.ExecutorService;
import cn.shuangbofu.nocturne.server.guice.Injectors;

import java.util.List;

/**
 * Created by shuangbofu on 2020/7/22 16:23
 */
public class RoundRobinExecutorSelector implements ExecutorSelector {

    ExecutorService executorService = Injectors.getInstance(ExecutorService.class);

    @Override
    public ExecutorInfo select(long executorGroupId) {
        List<ExecutorInfo> executorInfos = executorService.allActiveExecutors();
        return DTUtil.roundRobinGet(executorInfos);
    }
}
