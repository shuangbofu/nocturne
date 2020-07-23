package cn.shuangbofu.nocturne.server;

import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.routing.RoundRobinPool;
import cn.shuangbofu.nocturne.core.NocturneConstants;
import cn.shuangbofu.nocturne.server.actor.ServerActor;
import cn.shuangbofu.nocturne.server.guice.Injectors;
import cn.shuangbofu.nocturne.server.scheduler.GlobalController;
import cn.shuangbofu.nocturne.server.scheduler.task.TaskRetryScheduler;
import cn.shuangbofu.nocturne.server.alarm.AlarmEmitter;
import cn.shuangbofu.nocturne.server.dispatcher.TaskDisptacher;
import cn.shuangbofu.nocturne.server.scheduler.event.ServerStartEvent;
import cn.shuangbofu.nocturne.server.scheduler.task.TaskScheduler;
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
            // TODO 开启服务
            // NETTY OR AKKA
            ActorSystem actorSystem = Injectors.getInstance(ActorSystem.class);
            actorSystem.actorOf(
                    Props.create(ServerActor.class).withRouter(new RoundRobinPool(500)),
                    NocturneConstants.SERVER_AKKA_SYSTEM_NAME);

            TaskDisptacher.INSTANCE.start(DISPATCHER_THREAD_NUM);
            TaskRetryScheduler.INSTANCE.start(REJECT_RETRY_INTERVAL, AUTO_RETRY_INTERVAL);
            // 指标metric

            // 注册所有观察者，
            initAllObserver();
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
}
