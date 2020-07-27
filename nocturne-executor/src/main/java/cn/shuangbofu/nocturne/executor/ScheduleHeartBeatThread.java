package cn.shuangbofu.nocturne.executor;

import cn.shuangbofu.nocturne.core.Constants;
import cn.shuangbofu.nocturne.core.netty.channel.RequestChannel;
import cn.shuangbofu.nocturne.core.netty.message.ResponseMessage;
import cn.shuangbofu.nocturne.core.netty.protobuf.RequestFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by shuangbofu on 2020/7/23 18:02
 */
public enum ScheduleHeartBeatThread {
    /**
     *
     */
    INSTANCE;
    private static final int HEART_BEAT_PERIOD_SECONDS = 4;

    // TODO 从配置或者其他地方获取
    private static final int GROUP_ID = 1;

    private static final Logger LOGGER = LoggerFactory.getLogger(ScheduleHeartBeatThread.class);
    RequestChannel channel;
    ScheduledExecutorService scheduledExecutor;

    public void start(RequestChannel channel) {
        this.channel = channel;
        register();
    }

    private void schedule() {
        scheduledExecutor = Executors.newSingleThreadScheduledExecutor();
        LOGGER.info("定时心跳开启！");
        scheduledExecutor.scheduleAtFixedRate(() -> {
            try {
                ResponseMessage response = channel.request(RequestFactory.ping(TaskPool.INSTANCE.getTaskList()));
                if (response.isSuccess()) {
                    String data = response.getData(String.class);
                    if (Constants.PONG.equals(data)) {
                        LOGGER.info("收到服务端PONG！");
                    }
                }
            } catch (Exception e) {
                LOGGER.error("心跳异常", e);
            }
        }, 0, HEART_BEAT_PERIOD_SECONDS, TimeUnit.SECONDS);
    }

    public void shutdown() {
        if (scheduledExecutor != null) {
            scheduledExecutor.shutdown();
            scheduledExecutor = null;
        }
    }

    private void register() {
        LOGGER.info("发送注册信息……");
        boolean register = channel.request(RequestFactory.executorRegistry(GROUP_ID))
                .getData(Boolean.class);
        if (register) {
            LOGGER.info("收到服务端返回，注册成功！");
            schedule();
        }
    }
}
