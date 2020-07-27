package cn.shuangbofu.nocturne.executor;

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
    private static final int RECONNECT_DELAY = 10;

    public static void main(String[] args) throws InterruptedException {
        String ip = args[0];
        int port = Integer.parseInt(args[1]);

        NettyClient client = new NettyClient();
        client
                .listen(Event.CONNECTED, ScheduleHeartBeatThread.INSTANCE::start)
                .listen(Event.UNREGISTER, channel -> {
                    // 关闭心跳
                    ScheduleHeartBeatThread.INSTANCE.shutdown();
                    LOGGER.error("server服务中断，{}秒后重连……", RECONNECT_DELAY);
                    // TODO 一系列恢复操作

                    channel.getChannel().eventLoop().schedule(() -> {
                        try {
                            client.connect(ip, port);
                        } catch (InterruptedException e) {
                            LOGGER.error("重连失败!");
                            channel.getListener().fire(Event.UNREGISTER, channel);
                        }
                    }, RECONNECT_DELAY, TimeUnit.SECONDS);
                })
                .onReceive(new HandlerSet());
        // 连接
        client.connect(ip, port);
    }
}
