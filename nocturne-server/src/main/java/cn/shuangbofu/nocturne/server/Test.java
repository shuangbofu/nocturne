package cn.shuangbofu.nocturne.server;

import cn.shuangbofu.nocturne.core.constant.ConfigKeys;
import cn.shuangbofu.nocturne.core.utils.ConfigUtil;
import org.apache.commons.configuration2.Configuration;

/**
 * Created by shuangbofu on 2020/7/28 下午8:53
 */
public class Test {
    public static void main(String[] args) {
        Configuration config = ConfigUtil.getConfig("server.yaml");
        String key = config.getString(ConfigKeys.NOCTURNE_SERVER_KEY);
        System.out.println(key);
    }
}
