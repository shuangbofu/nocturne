package cn.shuangbofu.nocturne.server.scheduler.task;

import cn.shuangbofu.nocturne.core.observer.Event;
import cn.shuangbofu.nocturne.server.dispatcher.TaskQueues;
import cn.shuangbofu.nocturne.server.guice.Injectors;
import cn.shuangbofu.nocturne.server.scheduler.ServerObserver;
import cn.shuangbofu.nocturne.server.scheduler.event.ServerStartEvent;
import cn.shuangbofu.nocturne.server.scheduler.event.ServerStopEvent;
import com.google.common.eventbus.Subscribe;

/**
 * Created by shuangbofu on 2020/7/22 10:59
 */
public class TaskScheduler extends ServerObserver {

    private static final TaskScheduler INSTANCE = new TaskScheduler();
    private TaskQueues queues = Injectors.getInstance(TaskQueues.class);

    public static TaskScheduler getInstance() {
        return INSTANCE;
    }

    @Override
    public void handleStartEvent(ServerStartEvent event) {
        controller.notify(new TestEvent("hello test"));
    }

    @Override
    public void handleStopEvent(ServerStopEvent event) {

    }

    @Subscribe
    public void handleTestEvent(TestEvent event) {
        System.out.println("handle testEvent, msg = " + event.msg);
    }

    static class TestEvent implements Event {
        String msg;

        public TestEvent(String msg) {
            this.msg = msg;
        }
    }
}
