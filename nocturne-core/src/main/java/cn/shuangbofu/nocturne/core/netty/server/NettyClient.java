package cn.shuangbofu.nocturne.core.netty.server;

import cn.shuangbofu.nocturne.core.netty.channel.RequestChannel;
import cn.shuangbofu.nocturne.core.netty.event.Event;
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
import io.netty.util.HashedWheelTimer;
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
    @Getter
    private final ClientHandler clientHandler;
    private final Bootstrap bootstrap;
    @Getter
    private RequestChannel serverChannel;
    private int delay = 1;
    private TimeUnit delayUnit = TimeUnit.MINUTES;
    private String ip;
    private int port;

    public NettyClient() {
        bootstrap = new Bootstrap();
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup(1);

        bootstrap.group(eventLoopGroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE, true);
        clientHandler = new ClientHandler();
        bootstrap.handler(new ProtobufChannelInitializer(clientHandler));
    }

    public void connect(String ip, int port) throws InterruptedException {
        this.ip = ip;
        this.port = port;
        // 设置重连
        setUp();
        connect0();
    }

    private void connect0() throws InterruptedException {
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

    public NettyClient setReconnect(int delay, TimeUnit unit) {
        delayUnit = unit;
        this.delay = delay;
        return this;
    }

    private void setUp() {
        listen(Event.UNREGISTER, channel -> {
            LOGGER.error("连接中断/失败，{}秒后重连……", delayUnit.toSeconds(delay));
            new HashedWheelTimer().newTimeout(timeout -> {
                try {
                    connect0();
                } catch (InterruptedException e) {
                    LOGGER.error("重连失败!");
                    channel.getListener().fire(Event.UNREGISTER, channel);
                }
            }, delay, delayUnit);
        });
    }

    public void setServerChannel(Channel serverChannel) {
        this.serverChannel = new RequestChannel(serverChannel, clientHandler);
    }

    @Override
    protected MessageListener getListener() {
        return clientHandler;
    }
}
