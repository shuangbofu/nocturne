package cn.shuangbofu.nocturne.core.netty.annotation;

import java.lang.annotation.*;

/**
 * Created by shuangbofu on 2020/7/27 12:00
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OnReceive {
}
