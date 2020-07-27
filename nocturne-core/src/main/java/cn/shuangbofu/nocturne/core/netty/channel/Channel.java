package cn.shuangbofu.nocturne.core.netty.channel;

import cn.shuangbofu.nocturne.core.domain.Pair;
import cn.shuangbofu.nocturne.core.netty.listener.MessageListener;
import cn.shuangbofu.nocturne.protobuf.MessageProto;
import io.netty.channel.ChannelFuture;

/**
 * Created by shuangbofu on 2020/7/26 下午9:59
 */
public interface Channel {

    MessageListener getListener();

    io.netty.channel.Channel getChannel();

    ChannelFuture writeAndFlush(MessageProto.Message message);

    Pair<String, Integer> getLocalInfo();

    Pair<String, Integer> getRemoteInfo();

    String getRemoteAddress();

    String getLocalAddress();
}
