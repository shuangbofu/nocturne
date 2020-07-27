package cn.shuangbofu.nocturne.core.netty.server;

import cn.shuangbofu.nocturne.core.netty.annotation.OnReceive;
import cn.shuangbofu.nocturne.core.netty.event.Event;
import cn.shuangbofu.nocturne.core.netty.event.EventHandler;
import cn.shuangbofu.nocturne.core.netty.listener.MessageHandler;
import cn.shuangbofu.nocturne.core.netty.listener.MessageListener;
import com.google.protobuf.MessageLite;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Optional;

/**
 * Created by shuangbofu on 2020/7/27 下午5:01
 */
public abstract class ServerInitializer<S> {

    public S onReceive(Object initializer) {
        if (initializer.getClass().getAnnotation(OnReceive.class) != null) {
            for (Method method : initializer.getClass().getDeclaredMethods()) {
                if (method.getAnnotation(OnReceive.class) != null) {
                    Class<? extends MessageLite> receivedMessageClass = null;
                    Optional<Class<?>> any = Arrays.stream(method.getParameterTypes()).filter(MessageLite.class::isAssignableFrom).findAny();
                    if (any.isPresent()) {
                        receivedMessageClass = (Class<? extends MessageLite>) any.get();
                    }
                    if (receivedMessageClass != null) {
                        method.setAccessible(true);
                        onReceive(receivedMessageClass, ((message, responseChannel) -> {
                            try {
                                Object invoke = method.invoke(initializer, message.getInnerEntity(), responseChannel);
                                responseChannel.success(invoke);
                            } catch (IllegalAccessException | InvocationTargetException e) {
                                e.printStackTrace();
                            }
                        }));
                    }
                }
            }
        }
        return (S) this;
    }

    @SafeVarargs
    public final <T extends MessageLite> S onReceive(Class<T> clazz, MessageHandler<T>... handlers) {
        getListener().addMessageHandlers(clazz, handlers);
        return (S) this;
    }


    public S listen(Event event, EventHandler handler) {
        getListener().addEventListener(event, handler);
        return (S) this;
    }

    protected abstract MessageListener getListener();
}
