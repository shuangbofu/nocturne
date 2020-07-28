package cn.shuangbofu.nocturne.server;

import cn.shuangbofu.nocturne.core.NamedThreadFactory;
import cn.shuangbofu.nocturne.core.constant.ConfigKeys;
import cn.shuangbofu.nocturne.core.constant.Constants;
import cn.shuangbofu.nocturne.core.netty.annotation.OnReceive;
import cn.shuangbofu.nocturne.core.netty.channel.RequestChannel;
import cn.shuangbofu.nocturne.core.netty.message.ResponseMessage;
import cn.shuangbofu.nocturne.core.netty.protobuf.RequestFactory;
import cn.shuangbofu.nocturne.protobuf.WebServerProto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by shuangbofu on 2020/7/28 下午8:32
 */
@OnReceive
public enum HeartBeatThread {

    /**
     *
     */
    INSTANCE;
    private static final Logger LOGGER = LoggerFactory.getLogger(HeartBeatThread.class);
    ScheduledThreadPoolExecutor executor;

    @OnReceive
    public void start(WebServerProto.WebServerHeartbeatRequest request, RequestChannel channel) {
        executor = new ScheduledThreadPoolExecutor(1, new NamedThreadFactory("heart-beat"));
        LOGGER.info("定时心跳开启！");
        executor.scheduleAtFixedRate(() -> {
            try {
                ResponseMessage response = channel.request(RequestFactory.SERVER2WEB_PING);
                if (response.isSuccess()) {
                    String data = response.getData(String.class);
                    if (Constants.PONG.equals(data)) {
                        LOGGER.info("收到web 服务PONG！");
                    }
                }
            } catch (Exception e) {
                LOGGER.error("心跳异常", e);
            }
        }, 0, 5, TimeUnit.SECONDS);
    }

    public void shutdown() {
        LOGGER.info("关闭心跳！");
        if (executor != null) {
            executor.shutdown();
            executor = null;
        }
    }

    @OnReceive
    public boolean handleWebRegistry(WebServerProto.WebServerRegistry registry, RequestChannel channel) {
        return registry.getServerKey().equals(NocturneServer.CONFIG.getString(ConfigKeys.NOCTURNE_SERVER_KEY));
    }
}