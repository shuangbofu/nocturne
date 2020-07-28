package cn.shuangbofu.nocturne.core.utils;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.YAMLConfiguration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by shuangbofu on 2020/7/22 下午11:15
 */
public class ConfigUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigUtil.class);
    private static final Map<String, Configuration> CONFIGURATION_CACHE = new ConcurrentHashMap<>();
    private static final Configurations CONFIGS = new Configurations();

    private static Configuration getConfig0(String filename) {
        if (filename.endsWith(".properties")) {
            return getProperties(filename);
        } else if (filename.endsWith(".yaml")) {
            return getYamlConfig(filename);
        }
        return null;
    }

    public static Configuration getConfig(String filename) {
        Configuration config = CONFIGURATION_CACHE.get(filename);
        if (config == null) {
            config = getConfig0(filename);
            CONFIGURATION_CACHE.put(filename, config);
        }
        if (config == null) {
            throw new RuntimeException("not found config");
        }
        return config;

    }

    private static Configuration getYamlConfig(String filename) {
        try {
            YAMLConfiguration config = new YAMLConfiguration();
            FileInputStream fileInputStream = new FileInputStream(Thread.currentThread().getContextClassLoader().getResource(filename).getFile());
            config.read(fileInputStream);
            return config;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Configuration getProperties(String filename) {
        try {
            return CONFIGS.properties(filename);

        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
        return null;
    }
}
