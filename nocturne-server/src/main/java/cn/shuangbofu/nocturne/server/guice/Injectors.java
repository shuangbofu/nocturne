package cn.shuangbofu.nocturne.server.guice;

import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * Created by shuangbofu on 2020/7/22 13:46
 */
public class Injectors {
    private static final Injector INJECTOR;

    static {
        INJECTOR = Guice.createInjector(new GuiceModule());
    }

    public static <T> T getInstance(Class<T> clazz) {
        return INJECTOR.getInstance(clazz);
    }
}
