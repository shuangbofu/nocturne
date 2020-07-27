package cn.shuangbofu.nocturne.core.netty.event;

import cn.shuangbofu.nocturne.core.netty.channel.RequestChannel;

/**
 * Created by shuangbofu on 2020/7/25 上午11:42
 */
@FunctionalInterface
public interface EventHandler {
    void handle(RequestChannel channel);
}
