package cn.shuangbofu.nocturne.core.netty.server;

import cn.shuangbofu.nocturne.core.netty.channel.RequestChannel;
import cn.shuangbofu.nocturne.core.netty.handler.ClientHandler;
import cn.shuangbofu.nocturne.core.netty.handler.FutureListener;
import cn.shuangbofu.nocturne.core.netty.handler.ProtobufChannelInitializer;
import cn.shuangbofu.nocturne.core.netty.listener.MessageListener;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Created by shuangbofu on 2020/7/23 12:51
 */
public class NettyClient extends ServerInitializer<NettyClient> {

    private static final Logger LOGGER = LoggerFactory.getLogger(NettyClient.class);

    Bootstrap bootstrap;
    EventLoopGroup eventLoopGroup;

    @Getter
    RequestChannel serverChannel;
    @Getter
    ClientHandler clientHandler;

    public NettyClient() {
        bootstrap = new Bootstrap();
        eventLoopGroup = new NioEventLoopGroup(1);

        bootstrap.group(eventLoopGroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE, true);
        clientHandler = new ClientHandler();
        bootstrap.handler(new ProtobufChannelInitializer(clientHandler));
    }

    public void connect(String ip, int port) throws InterruptedException {
        LOGGER.info("开始连接{}:{}……", ip, port);
        CountDownLatch latch = new CountDownLatch(1);
        ChannelFuture future = bootstrap.connect(ip, port);
        future.addListener(new FutureListener(this, latch));
        boolean await = latch.await(10, TimeUnit.SECONDS);
        if (!await) {
            throw new RuntimeException("连接服务超时……");
        }
        if (!future.isSuccess()) {
            throw new RuntimeException("连接服务失败！");
        }
    }

    public void setServerChannel(Channel serverChannel) {
        this.serverChannel = new RequestChannel(serverChannel, clientHandler);
    }

    @Override
    public MessageListener getListener() {
        return clientHandler;
    }
}
