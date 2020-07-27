package cn.shuangbofu.nocturne.core.netty.listener;

import cn.shuangbofu.nocturne.core.netty.channel.ResponseChannel;
import cn.shuangbofu.nocturne.core.netty.message.ResponseMessage;

/**
 * Created by shuangbofu on 2020/7/23 下午8:09
 */
@FunctionalInterface
public interface ResponseHandler {
    void onReceiveResponse(ResponseMessage response, ResponseChannel channel);
}
