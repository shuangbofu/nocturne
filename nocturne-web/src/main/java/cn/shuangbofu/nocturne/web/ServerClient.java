package cn.shuangbofu.nocturne.web;

import cn.shuangbofu.nocturne.core.constant.Constants;
import cn.shuangbofu.nocturne.core.netty.annotation.OnReceive;
import cn.shuangbofu.nocturne.core.netty.channel.RequestChannel;
import cn.shuangbofu.nocturne.core.netty.channel.ResponseChannel;
import cn.shuangbofu.nocturne.core.netty.message.ResponseMessage;
import cn.shuangbofu.nocturne.core.netty.protobuf.RequestFactory;
import cn.shuangbofu.nocturne.protobuf.HeartBeatProto;
import com.google.protobuf.MessageLite;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by shuangbofu on 2020/7/28 17:12
 */
@OnReceive
public class ServerClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(ServerClient.class);
    private static final ServerClient CLIENT = new ServerClient();
    private static final AtomicLong LAST_HEART = new AtomicLong();
    private RequestChannel channel;

    public static ServerClient getInstance() {
        return CLIENT;
    }

    public static ResponseMessage req(MessageLite messageLite) {
        return getInstance().request(messageLite);
    }

    public static void requestNoRes(MessageLite messageLite) {
        getInstance().getServerChannel().req(messageLite);
    }

    // TODO 失败要怎么样？
    public void register(RequestChannel channel, String serverKey) {
        this.channel = channel;
        ResponseMessage response = channel.request(RequestFactory.webRegistry(serverKey));
        if (response.isSuccess()) {
            // web请求server开启心跳
            LOGGER.info("成功连接到server，请求heartbeat");
            channel.request(RequestFactory.HEARTBEAT_REQUEST2_SERVER);
        } else {
            LOGGER.error("连接server失败");
        }
    }

    public ResponseMessage request(MessageLite messageLite) {
        return getServerChannel().request(messageLite);
    }

    private RequestChannel getServerChannel() {
        RequestChannel channel = Optional.ofNullable(this.channel).orElseThrow(() -> new RuntimeException("connection interrupted."));
        if (LAST_HEART.get() - System.currentTimeMillis() > 12 * 1000) {
            throw new RuntimeException("connection lost");
        }
        return channel;
    }

    @OnReceive
    public String handleServerHeartbeat(HeartBeatProto.ServerHeartBeat serverHeartBeat, ResponseChannel channel) {
        if (serverHeartBeat.getMsg().equals(Constants.PING)) {
            LAST_HEART.set(System.currentTimeMillis());
            return Constants.PONG;
        }
        return null;
    }
}
