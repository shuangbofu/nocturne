package cn.shuangbofu.nocturne.core.netty.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;

/**
 * Created by shuangbofu on 2020/7/23 11:18
 */
@ChannelHandler.Sharable
public class ServerHandler extends AbstractMessageHandler {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        LOGGER.info("客户端连接成功！{}", ctx.channel().remoteAddress());
        super.channelActive(ctx);
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        LOGGER.info("客户端断开连接！{}", ctx.channel().remoteAddress());
        super.channelUnregistered(ctx);
    }
}
