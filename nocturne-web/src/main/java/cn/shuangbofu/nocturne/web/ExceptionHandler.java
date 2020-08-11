package cn.shuangbofu.nocturne.web;

import cn.shuangbofu.nocturne.web.vo.Result;
import com.blade.ioc.annotation.Bean;
import com.blade.mvc.WebContext;
import com.blade.mvc.handler.DefaultExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by shuangbofu on 2020/7/29 上午10:56
 */
@Bean
public class ExceptionHandler extends DefaultExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionHandler.class);

    @Override
    public void handle(Exception e) {
        LOGGER.warn("异常", e);
        WebContext.response().json(Result.error(e.getMessage()));
    }
}
