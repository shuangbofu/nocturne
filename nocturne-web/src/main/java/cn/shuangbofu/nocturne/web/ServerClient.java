package cn.shuangbofu.nocturne.web;

import cn.shuangbofu.nocturne.core.netty.channel.RequestChannel;
import cn.shuangbofu.nocturne.core.netty.message.ResponseMessage;
import com.google.protobuf.MessageLite;

import java.util.Optional;

/**
 * Created by shuangbofu on 2020/7/28 17:12
 */
public class ServerClient {

    private static final ServerClient CLIENT = new ServerClient();

    private RequestChannel channel;

    public static ServerClient get() {
        return CLIENT;
    }

    public void register(RequestChannel channel, String serverKey) {
        this.channel = channel;
//        channel.request();
    }

    public ResponseMessage request(MessageLite messageLite) {
        return getServerChannel().request(messageLite);
    }

    private RequestChannel getServerChannel() {
        return Optional.ofNullable(channel).orElseThrow(() -> new RuntimeException("Connection interrupted."));
    }
}
