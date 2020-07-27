package cn.shuangbofu.nocturne.core.netty.handler;

import cn.shuangbofu.nocturne.protobuf.MessageProto.Message;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;

/**
 * Created by shuangbofu on 2020/7/23 10:46
 */
@ChannelHandler.Sharable
public class ProtobufChannelInitializer extends ChannelInitializer<SocketChannel> {
    ChannelHandler handler;

    public ProtobufChannelInitializer(AbstractMessageHandler handler) {
        this.handler = handler;
    }

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline ph = ch.pipeline();
        //传输的协议 Protobuf
        ph.addLast(new ProtobufVarint32FrameDecoder());
        ph.addLast(new ProtobufDecoder(Message.getDefaultInstance()));
        ph.addLast(new ProtobufVarint32LengthFieldPrepender());
        ph.addLast(new ProtobufEncoder());

        //业务逻辑实现类
        ph.addLast(handler);
    }
}
