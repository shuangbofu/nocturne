package cn.shuangbofu.nocturne.executor;

import cn.shuangbofu.nocturne.core.constant.ConfigKeys;
import cn.shuangbofu.nocturne.core.constant.Constants;
import cn.shuangbofu.nocturne.core.netty.event.Event;
import cn.shuangbofu.nocturne.core.netty.server.NettyClient;
import cn.shuangbofu.nocturne.core.utils.ConfigUtil;
import org.apache.commons.configuration2.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * Created by shuangbofu on 2020/7/23 13:27
 */
public class ExecutorServer {
    public static final Configuration CONFIG = ConfigUtil.getConfig("executor.yaml");
    private static final Logger LOGGER = LoggerFactory.getLogger(ExecutorServer.class);

    public static void main(String[] args) throws InterruptedException {
        String ip = CONFIG.getString(ConfigKeys.NOCTURNE_SERVER_IP);
        int port = CONFIG.getInt(ConfigKeys.NOCTURNE_SERVER_PORT, Constants.SERVER_DEFAULT_PORT);

        new NettyClient()
                .setReconnect(15, TimeUnit.SECONDS)
                .listen(Event.CONNECTED, ScheduleHeartBeatThread.INSTANCE::start)
                .listen(Event.UNREGISTER, channel -> ScheduleHeartBeatThread.INSTANCE.shutdown())
                .onReceive(new HandlerSet())
                .connect(ip, port);
    }
}
