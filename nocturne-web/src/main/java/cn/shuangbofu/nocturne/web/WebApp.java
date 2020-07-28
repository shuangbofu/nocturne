package cn.shuangbofu.nocturne.web;

import cn.shuangbofu.nocturne.core.constant.ConfigKeys;
import cn.shuangbofu.nocturne.core.constant.Constants;
import cn.shuangbofu.nocturne.core.netty.event.Event;
import cn.shuangbofu.nocturne.core.netty.server.NettyClient;
import com.blade.Blade;
import com.blade.Environment;
import com.blade.event.EventType;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * Created by shuangbofu on 2020/7/28 12:45
 */
public class WebApp {

    public static void main(String[] args) {
        Blade.of().on(EventType.SERVER_STARTED, eve -> {
            Environment env = eve.environment();
            String ip = env.get(ConfigKeys.NOCTURNE_SERVER_IP).orElseThrow(() -> new RuntimeException("not found server.ip"));
            Optional<String> portStringOptional = env.get(ConfigKeys.NOCTURNE_SERVER_PORT);
            String serverKey = env.get(ConfigKeys.NOCTURNE_SERVER_KEY).orElseThrow(() -> new RuntimeException("not found server key."));
            int port = portStringOptional.map(Integer::parseInt).orElse(Constants.SERVER_DEFAULT_PORT);
            try {
                new NettyClient()
                        .setReconnect(1, TimeUnit.MINUTES)
                        .onReceive(WebHandlerSet.getInstance())
                        .listen(Event.CONNECTED, channel -> ServerClient.get().register(channel, serverKey))
                        .connect(ip, port)
                ;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }).start()
                .get("/", ctx -> ctx.text("Hello Blade"))
                .start();
    }
}
