package cn.shuangbofu.nocturne.core.netty.listener;


import cn.shuangbofu.nocturne.core.netty.channel.ResponseChannel;
import cn.shuangbofu.nocturne.core.netty.message.ReceivedMessage;
import com.google.protobuf.MessageLite;

/**
 * Created by shuangbofu on 2020/7/23 15:48
 */
@FunctionalInterface
public interface MessageHandler<T extends MessageLite> {
    void onReceive(ReceivedMessage<T> message, ResponseChannel responseChannel);
}
