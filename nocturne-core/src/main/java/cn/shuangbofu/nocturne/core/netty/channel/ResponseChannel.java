package cn.shuangbofu.nocturne.core.netty.channel;

import cn.shuangbofu.nocturne.core.netty.listener.MessageListener;
import cn.shuangbofu.nocturne.protobuf.MessageTypeProto;
import cn.shuangbofu.nocturne.protobuf.ResponseProto;
import com.google.protobuf.ByteString;
import com.google.protobuf.MessageLite;
import io.netty.channel.ChannelFuture;

/**
 * Created by shuangbofu on 2020/7/26 下午9:55
 */
public class ResponseChannel extends RequestChannel {
    private final int reqId;

    public ResponseChannel(io.netty.channel.Channel channel, int reqId, MessageListener listener) {
        super(channel, listener);
        this.reqId = reqId;
    }

    public void error(String message) {
        response(false, message, null);
    }

    public void success(Object res) {
        if (res == null) {
            return;
        }
        if (res instanceof ByteString) {
            success((ByteString) res);
        } else if (res instanceof Boolean) {
            success((Boolean) res);
        } else if (res instanceof String) {
            success((String) res);
        } else if (res instanceof MessageLite) {
            success((MessageLite) res);
        } else {
            success(res.toString());
        }
    }

    public void success(ByteString data) {
        response(true, "", data);
    }

    public void success(MessageLite messageLite) {
        success(messageLite.toByteString());
    }

    public void success(Boolean data) {
        success(data.toString());
    }

    public void success(String str) {
        success(ByteString.copyFromUtf8(str));
    }

    public void response(boolean success, String message, ByteString data) {
        ChannelFuture future = writeAndFlush(buildMessage(0, MessageTypeProto.MessageType.response,
                ResponseProto.Response.newBuilder()
                        .setSuccess(success)
                        .setMessage(message)
                        .setData(data)
                        .setReqId(reqId)
                        .build().toByteString()));
        future.addListener(f -> {
            if (f.isSuccess()) {
                LOGGER.debug("response success");
            } else {
                Throwable cause = f.cause();
                ByteString res = ResponseProto.Response.newBuilder()
                        .setSuccess(false)
                        .setMessage(cause.getMessage())
                        .build().toByteString();
                writeAndFlush(buildMessage(0, MessageTypeProto.MessageType.response, res));
                LOGGER.error("response error", cause);
            }
        });
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
