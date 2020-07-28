package cn.shuangbofu.nocturne.server;

import cn.shuangbofu.nocturne.core.constant.ConfigKeys;
import cn.shuangbofu.nocturne.core.netty.channel.RequestChannel;
import cn.shuangbofu.nocturne.core.netty.event.Event;
import cn.shuangbofu.nocturne.core.netty.server.NettyServer;
import cn.shuangbofu.nocturne.core.utils.ConfigUtil;
import cn.shuangbofu.nocturne.protobuf.WebServerProto;
import cn.shuangbofu.nocturne.server.alarm.AlarmEmitter;
import cn.shuangbofu.nocturne.server.handler.FromExecutorHandlerSet;
import cn.shuangbofu.nocturne.server.scheduler.GlobalController;
import cn.shuangbofu.nocturne.server.scheduler.event.ServerStartEvent;
import cn.shuangbofu.nocturne.server.scheduler.task.TaskRetryScheduler;
import cn.shuangbofu.nocturne.server.scheduler.task.TaskScheduler;
import cn.shuangbofu.nocturne.server.service.ExecutorStore;
import com.google.common.base.Throwables;
import org.apache.commons.configuration2.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static cn.shuangbofu.nocturne.core.constant.Constants.*;

/**
 * Created by shuangbofu on 2020/7/22 10:51
 */
public class NocturneServer {

    public static final Configuration CONFIG = ConfigUtil.getConfig("server.yaml");
    private static final Logger LOGGER = LoggerFactory.getLogger(NocturneServer.class);

    public static void main(String[] args) {
        try {
            int port = CONFIG.getInt(ConfigKeys.NOCTURNE_SERVER_PORT, SERVER_DEFAULT_PORT);
            // 开启服务
            new NettyServer()
                    .listen(Event.UNREGISTER, NocturneServer::disConnect)
                    // 处理来自executor的消息
                    .onReceive(new FromExecutorHandlerSet())
                    // web初始化相关
                    .onReceive(WebServerProto.WebServerRegistry.class, (m, c) -> c.success(true))
                    .onReceive(HeartBeatThread.INSTANCE)
                    .start(port);
//            TaskDisptacher.INSTANCE.start(DISPATCHER_THREAD_NUM);
            TaskRetryScheduler.INSTANCE.start(REJECT_RETRY_INTERVAL, AUTO_RETRY_INTERVAL);
            // 指标metric

            // 注册所有观察者
            initAllObserver();
            LOGGER.info("服务启动完毕！");
        } catch (Exception e) {
            Throwables.propagate(e);
        }
    }

    public static void initAllObserver() {
        GlobalController controller = GlobalController.getInstance();
        controller.register(TaskScheduler.getInstance());
        controller.register(AlarmEmitter.getInstance());
        controller.notify(new ServerStartEvent());
    }

    public static void disConnect(RequestChannel channel) {
        ExecutorStore.INSTANCE.unregister(channel);
    }
}
