package cn.shuangbofu.nocturne.executor;

import cn.shuangbofu.nocturne.core.NamedThreadFactory;
import cn.shuangbofu.nocturne.core.constant.ConfigKeys;
import cn.shuangbofu.nocturne.core.constant.Constants;
import cn.shuangbofu.nocturne.core.netty.channel.RequestChannel;
import cn.shuangbofu.nocturne.core.netty.message.ResponseMessage;
import cn.shuangbofu.nocturne.core.netty.protobuf.RequestFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by shuangbofu on 2020/7/23 18:02
 */
public enum ScheduleHeartBeatThread {
    /**
     *
     */
    INSTANCE;

    // TODO 从配置或者其他地方获取
    private static final Logger LOGGER = LoggerFactory.getLogger(ScheduleHeartBeatThread.class);
    RequestChannel channel;
    ScheduledExecutorService scheduledExecutor;

    public void start(RequestChannel channel) {
        this.channel = channel;
        register();
    }

    private void schedule() {
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1, new NamedThreadFactory("heart-beat"));
        LOGGER.info("定时心跳开启！");
        executor.scheduleAtFixedRate(() -> {
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
        }, 0, 5, TimeUnit.SECONDS);
    }

    public void shutdown() {
        LOGGER.info("关闭心跳！");
        if (scheduledExecutor != null) {
            scheduledExecutor.shutdown();
            scheduledExecutor = null;
        }
    }

    private void register() {
        LOGGER.info("发送注册信息……");
        String serverKey = ExecutorServer.CONFIG.getString(ConfigKeys.NOCTURNE_SERVER_KEY);
        String executorKey = ExecutorServer.CONFIG.getString(ConfigKeys.NOCTURNE_EXECUTOR_KEY);
        boolean register = channel.request(RequestFactory.executorRegistry(serverKey, executorKey))
                .getData(Boolean.class);
        if (register) {
            LOGGER.info("收到服务端返回，注册成功！");
            schedule();
        }
    }
}
