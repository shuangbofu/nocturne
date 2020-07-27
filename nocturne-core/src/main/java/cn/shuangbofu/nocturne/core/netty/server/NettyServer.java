package cn.shuangbofu.nocturne.core.netty.server;

import cn.shuangbofu.nocturne.core.netty.handler.ProtobufChannelInitializer;
import cn.shuangbofu.nocturne.core.netty.handler.ServerHandler;
import cn.shuangbofu.nocturne.core.netty.listener.MessageListener;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * Created by shuangbofu on 2020/7/23 10:38
 */
public class NettyServer extends ServerInitializer<NettyServer> {
    ServerBootstrap serverBootstrap;
    EventLoopGroup bossGroup;
    NioEventLoopGroup workerGroup;
    ServerHandler serverHandler;

    public NettyServer() {
        serverBootstrap = new ServerBootstrap();
        bossGroup = new NioEventLoopGroup(1);
        workerGroup = new NioEventLoopGroup(1);
        serverBootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class);
        serverHandler = new ServerHandler();
        serverBootstrap.childHandler(new ProtobufChannelInitializer(serverHandler));
    }


    public void start(int port) throws InterruptedException {
        serverBootstrap.bind(port).sync();
    }

    public void shutdown() {

    }

    @Override
    public MessageListener getListener() {
        return serverHandler;
    }
}
