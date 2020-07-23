package cn.shuangbofu.nocturne.core.utils;

import com.google.common.base.Throwables;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * Created by shuangbofu on 2020/7/22 下午11:15
 */
public class ConfigUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigUtil.class);

    public static Config getAkkaConfig(String fileName) {
        String key = "akka.remote.netty.tcp.hostname";
        Config akkaConfig = ConfigFactory.load(fileName);
        if (akkaConfig.hasPath(key) && !akkaConfig.getString(key).isEmpty()) {
            LOGGER.info("akka akkaConfig=="+akkaConfig);
            return akkaConfig;
        } else {
            try {
                String ip = IPUtils.getIpV4Address();
                LOGGER.info("akka ip=="+ip);
                return ConfigFactory.parseString(key + "=" + ip).withFallback(akkaConfig);
            } catch (UnknownHostException | SocketException ex) {
                Throwables.propagate(ex);
                return null;
            }
        }
    }
}
