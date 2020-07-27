package cn.shuangbofu.nocturne.core.netty.listener;

import cn.shuangbofu.nocturne.core.netty.channel.RequestChannel;
import cn.shuangbofu.nocturne.core.netty.event.Event;

/**
 * Created by shuangbofu on 2020/7/27 上午12:07
 */
@FunctionalInterface
public interface EventListener {
    void fire(Event event, RequestChannel channel);
}
