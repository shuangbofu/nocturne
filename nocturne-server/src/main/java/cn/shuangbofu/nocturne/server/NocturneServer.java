package cn.shuangbofu.nocturne.server;

import cn.shuangbofu.nocturne.core.netty.channel.RequestChannel;
import cn.shuangbofu.nocturne.core.netty.event.Event;
import cn.shuangbofu.nocturne.core.netty.server.NettyServer;
import cn.shuangbofu.nocturne.server.alarm.AlarmEmitter;
import cn.shuangbofu.nocturne.server.dispatcher.TaskDisptacher;
import cn.shuangbofu.nocturne.server.scheduler.GlobalController;
import cn.shuangbofu.nocturne.server.scheduler.event.ServerStartEvent;
import cn.shuangbofu.nocturne.server.scheduler.task.TaskRetryScheduler;
import cn.shuangbofu.nocturne.server.scheduler.task.TaskScheduler;
import cn.shuangbofu.nocturne.server.service.ExecutorStore;
import com.google.common.base.Throwables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by shuangbofu on 2020/7/22 10:51
 */
public class NocturneServer {
    private static final int DISPATCHER_THREAD_NUM = 2;
    private static final int REJECT_RETRY_INTERVAL = 3;
    private static final int AUTO_RETRY_INTERVAL = 4;

    private static final Logger LOGGER = LoggerFactory.getLogger(NocturneServer.class);

    public static void main(String[] args) {
        try {
            int port = Integer.parseInt(args[0]);
            new NettyServer()
                    .listen(Event.UNREGISTER, NocturneServer::executorDisconnect)
                    .onReceive(new ServerHandlerSet())
                    .start(port);

            // TODO 开启服务

            TaskDisptacher.INSTANCE.start(DISPATCHER_THREAD_NUM);
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

    public static void executorDisconnect(RequestChannel channel) {
        LOGGER.info("executor断开连接，server做一些操作！");
        ExecutorStore.INSTANCE.unregister(channel);
    }
}
