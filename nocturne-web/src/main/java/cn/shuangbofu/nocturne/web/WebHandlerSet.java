package cn.shuangbofu.nocturne.web;

import cn.shuangbofu.nocturne.core.netty.annotation.OnReceive;

/**
 * Created by shuangbofu on 2020/7/28 17:07
 */
@OnReceive
public class WebHandlerSet {
    private static final WebHandlerSet INSTANCE = new WebHandlerSet();

    public static WebHandlerSet getInstance() {
        return INSTANCE;
    }

}
