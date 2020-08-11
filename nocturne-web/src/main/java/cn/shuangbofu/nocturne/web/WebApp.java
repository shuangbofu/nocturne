package cn.shuangbofu.nocturne.web;

import cn.shuangbofu.nocturne.core.constant.ConfigKeys;
import cn.shuangbofu.nocturne.core.constant.Constants;
import cn.shuangbofu.nocturne.dao.LoadDb;
import cn.shuangbofu.nocturne.dao.loader.ExecutorLoader;
import cn.shuangbofu.nocturne.web.vo.Result;
import com.blade.Blade;
import com.blade.Environment;
import com.blade.event.EventType;

import java.util.Optional;

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
            loadDb(env);
            try {
//                new NettyClient()
//                        .setReconnect(20, TimeUnit.SECONDS)
//                        .onReceive(WebHandlerSet.getInstance())
//                        .onReceive(ServerClient.getInstance())
//                        .listen(Event.CONNECTED, channel -> ServerClient.getInstance().register(channel, serverKey))
//                        .connect(ip, port);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        })
                .get("/", ctx -> ctx.text("Hello Blade"))
                .get("/executorGroup/list", ctx -> ctx.json(Result.success(ExecutorLoader.listAllGroups())))
                .get("/executor/list", ctx -> ctx.json(Result.success(ExecutorLoader.listAllExecutors())))
                .start();
    }

    private static void loadDb(Environment env) {
        String jdbcUrl = env.get(ConfigKeys.JDBC_URL).get();
        String username = env.get(ConfigKeys.JDBC_USERNAME).get();
        String password = env.get(ConfigKeys.JDBC_PASSWORD).get();
        LoadDb.load(jdbcUrl, username, password);
    }
}
