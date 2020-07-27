package cn.shuangbofu.nocturne.server.guice;

import cn.shuangbofu.nocturne.server.dispatcher.selector.ExecutorSelector;
import cn.shuangbofu.nocturne.server.dispatcher.selector.RoundRobinExecutorSelector;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;

/**
 * Created by shuangbofu on 2020/7/22 13:38
 */
public class GuiceModule extends AbstractModule {

    @Provides
    @Singleton
    private ExecutorSelector provideExecutorSelector() {
        // TODO 根据配置
        return new RoundRobinExecutorSelector();
    }

    @Override
    protected void configure() {

    }
}
