package cn.shuangbofu.nocturne.server.scheduler;

import cn.shuangbofu.nocturne.core.observer.Observer;
import cn.shuangbofu.nocturne.server.scheduler.event.ServerStartEvent;
import cn.shuangbofu.nocturne.server.scheduler.event.ServerStopEvent;
import com.google.common.eventbus.DeadEvent;
import com.google.common.eventbus.Subscribe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by shuangbofu on 2020/7/22 10:59
 */
public abstract class ServerObserver implements Observer {
    public static final Logger LOGGER = LoggerFactory.getLogger(ServerObserver.class);
    public static GlobalController controller = GlobalController.getInstance();

    @Subscribe
    public abstract void handleStartEvent(ServerStartEvent event);

    @Subscribe
    public abstract void handleStopEvent(ServerStopEvent event);

    @Subscribe
    public void handleDeadEvent(DeadEvent event) {
        LOGGER.warn("Receive DeadEvent {} from {}", event.getEvent(), event.getSource().getClass());
    }
}
