package cn.shuangbofu.nocturne.server.alarm;

import cn.shuangbofu.nocturne.server.scheduler.event.ServerStopEvent;
import cn.shuangbofu.nocturne.server.scheduler.ServerObserver;
import cn.shuangbofu.nocturne.server.scheduler.event.ServerStartEvent;

/**
 * Created by shuangbofu on 2020/7/22 13:00
 */
public class AlarmEmitter extends ServerObserver {

    private static final AlarmEmitter INSTANCCE = new AlarmEmitter();

    public static AlarmEmitter getInstance() {
        return INSTANCCE;
    }

    @Override
    public void handleStartEvent(ServerStartEvent event) {
        System.out.println("alarmEmitter open!");
    }

    @Override
    public void handleStopEvent(ServerStopEvent event) {
        System.out.println("alarmEmitter close!");
    }
}
