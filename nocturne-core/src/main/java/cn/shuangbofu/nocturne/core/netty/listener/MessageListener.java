package cn.shuangbofu.nocturne.core.netty.listener;

import cn.shuangbofu.nocturne.core.netty.event.Event;
import cn.shuangbofu.nocturne.core.netty.event.EventHandler;
import com.google.protobuf.MessageLite;

/**
 * Created by shuangbofu on 2020/7/24 13:52
 */
public interface MessageListener extends EventListener {
    <T extends MessageLite> void addMessageHandlers(Class<T> clazz, MessageHandler<T>... handlers);

    void addResponseHandler(Integer reqId, ResponseHandler responseHandler);

    void removeResponseHandler(Integer reqId);

    void removeMessageHandler(MessageHandler<? extends MessageLite> messageHandler);

    void addEventListener(Event event, EventHandler handler);

    void removeEventListener(EventHandler handler);
}
