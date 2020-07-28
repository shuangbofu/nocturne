package cn.shuangbofu.nocturne.executor;

import cn.shuangbofu.nocturne.core.constant.Constants;
import cn.shuangbofu.nocturne.core.netty.event.Event;
import cn.shuangbofu.nocturne.core.netty.server.NettyClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * Created by shuangbofu on 2020/7/23 13:27
 */
public class ExecutorServer {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExecutorServer.class);

    public static void main(String[] args) throws InterruptedException {
        String ip = getIp(args);
        int port = getPort(args);
        new NettyClient()
                .setReconnect(15, TimeUnit.SECONDS)
                .listen(Event.CONNECTED, ScheduleHeartBeatThread.INSTANCE::start)
                .listen(Event.UNREGISTER, channel -> ScheduleHeartBeatThread.INSTANCE.shutdown())
                .onReceive(new HandlerSet())
                .connect(ip, port);
    }

    private static int getPort(String[] args) {
        int port = Constants.SERVER_DEFAULT_PORT;
        if (args.length > 1) {
            port = Integer.parseInt(args[1]);
        }
        return port;
    }

    private static String getIp(String[] args) {
        String iP = "127.0.0.1";
        if (args.length > 0) {
            iP = args[0];
        }
        return iP;
    }
}
