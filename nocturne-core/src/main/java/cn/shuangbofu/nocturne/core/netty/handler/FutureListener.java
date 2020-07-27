package cn.shuangbofu.nocturne.core.netty.handler;

import cn.shuangbofu.nocturne.core.netty.event.Event;
import cn.shuangbofu.nocturne.core.netty.server.NettyClient;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;

/**
 * Created by shuangbofu on 2020/7/23 13:22
 */
public class FutureListener implements ChannelFutureListener {

    private static Logger LOGGER = LoggerFactory.getLogger(NettyClient.class);

    private final NettyClient client;
    private final CountDownLatch latch;

    public FutureListener(NettyClient client, CountDownLatch latch) {
        this.client = client;
        this.latch = latch;
    }

    @Override
    public void operationComplete(ChannelFuture future) throws Exception {
        if (future.isSuccess()) {
            Channel channel = future.channel();
            client.setServerChannel(channel);
            LOGGER.info("============连接成功=============");
            LOGGER.info("当前服务: {}", client.getServerChannel().getLocalAddress());
            LOGGER.info("连接服务: {}", client.getServerChannel().getRemoteAddress());
            LOGGER.info("================================");
            latch.countDown();
            client.getClientHandler().fire(Event.CONNECTED, client.getServerChannel());
        } else {
            LOGGER.error("连接服务失败！", future.cause());
        }
    }
}
