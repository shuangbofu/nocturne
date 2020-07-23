package cn.shuangbofu.nocturne.server.scheduler;

import cn.shuangbofu.nocturne.core.observer.Event;
import cn.shuangbofu.nocturne.core.observer.Observable;
import cn.shuangbofu.nocturne.core.observer.Observer;
import com.google.common.eventbus.EventBus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by shuangbofu on 2020/7/22 10:43
 */
public class GlobalController implements Observable {
    protected static final Logger LOGGER = LoggerFactory.getLogger(GlobalController.class);
    private static final GlobalController INSTANCE = new GlobalController();
    protected EventBus eventBus;

    public GlobalController() {
        eventBus = new EventBus();
    }

    public static GlobalController getInstance() {
        return INSTANCE;
    }

    @Override
    public void register(Observer o) {
        if (o instanceof ServerObserver) {
            eventBus.register(o);
        }
        LOGGER.info("{} register {}", getClass().getSimpleName(), o.getClass().getSimpleName());
    }

    @Override
    public void unregister(Observer o) {
        if (o instanceof ServerObserver) {
            eventBus.unregister(o);
            LOGGER.info("{} unregister {}", getClass().getSimpleName(), o.getClass().getSimpleName());
        }
    }

    @Override
    public void notify(Event event) {
        eventBus.post(event);
    }
}
