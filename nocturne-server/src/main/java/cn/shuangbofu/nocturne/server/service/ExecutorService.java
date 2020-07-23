package cn.shuangbofu.nocturne.server.service;

import cn.shuangbofu.nocturne.core.domain.ExecutorInfo;
import com.google.common.collect.Lists;
import com.google.inject.Singleton;

import java.util.List;

/**
 * Created by shuangbofu on 2020/7/22 16:53
 */
@Singleton
public class ExecutorService {

    public List<ExecutorInfo> allActiveExecutors() {
        return Lists.newArrayList();
    }
}
