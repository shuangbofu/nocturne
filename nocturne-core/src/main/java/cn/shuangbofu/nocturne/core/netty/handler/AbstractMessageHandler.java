package cn.shuangbofu.nocturne.core.netty.handler;

import cn.shuangbofu.nocturne.core.NamedThreadFactory;
import cn.shuangbofu.nocturne.core.netty.channel.RequestChannel;
import cn.shuangbofu.nocturne.core.netty.channel.ResponseChannel;
import cn.shuangbofu.nocturne.core.netty.event.Event;
import cn.shuangbofu.nocturne.core.netty.event.EventHandler;
import cn.shuangbofu.nocturne.core.netty.listener.MessageHandler;
import cn.shuangbofu.nocturne.core.netty.listener.MessageListener;
import cn.shuangbofu.nocturne.core.netty.listener.ResponseHandler;
import cn.shuangbofu.nocturne.core.netty.message.ReceivedMessage;
import cn.shuangbofu.nocturne.core.netty.message.ResponseMessage;
import cn.shuangbofu.nocturne.core.netty.protobuf.ProtocolEntityUtil;
import cn.shuangbofu.nocturne.protobuf.MessageProto.Message;
import cn.shuangbofu.nocturne.protobuf.MessageTypeProto.MessageType;
import cn.shuangbofu.nocturne.protobuf.ResponseProto;
import com.google.common.collect.Lists;
import com.google.common.collect.Queues;
import com.google.protobuf.MessageLite;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by shuangbofu on 2020/7/23 16:00
 */
public abstract class AbstractMessageHandler extends SimpleChannelInboundHandler<Message> implements MessageListener {

    // TODO 线程池具体参数、队列类型等可能要修改下
    // 0 MAX_VALUE 可能会OOM
    // TODO 学习线程池和队列

    private static final ThreadPoolExecutor HANDLE_RECEIVE_THREAD_POOL = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 5, TimeUnit.MINUTES, new SynchronousQueue<>(), new NamedThreadFactory("messageHandler"));
    private static final ThreadPoolExecutor EVENT_FIRE_THREAD_POOL = new ThreadPoolExecutor(2, 30, 5, TimeUnit.MINUTES, Queues.newArrayBlockingQueue(5), new NamedThreadFactory("event-fire"));
    protected final Logger LOGGER = LoggerFactory.getLogger(getClass().getSimpleName());
    private final Map<Class<? extends MessageLite>, List<MessageHandler<? extends MessageLite>>> messageHandlersMap = new ConcurrentHashMap<>();
    private final Map<Integer, ResponseHandler> responseHandlerMap = new ConcurrentHashMap<>();
    private final Map<Event, List<EventHandler>> eventEventHandlersMap = new ConcurrentHashMap<>();

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        fire(Event.UNREGISTER, new RequestChannel(ctx.channel(), this));
        super.channelUnregistered(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        LOGGER.error("发生异常", cause);
        super.exceptionCaught(ctx, cause);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Message msg) throws Exception {
        LOGGER.debug("msg:{}", msg);
        MessageType type = msg.getType();
        ReceivedMessage receivedMessage = new ReceivedMessage(msg);
        LOGGER.debug("request reqId ===> {}", receivedMessage.getReqId());
        ResponseChannel responseChannel = new ResponseChannel(ctx.channel(), receivedMessage.getReqId(), this);
        if (type.equals(MessageType.response)) {
            ResponseMessage responseMessage = new ResponseMessage((ReceivedMessage<ResponseProto.Response>) receivedMessage);
            LOGGER.debug("response reqId ===> {}", responseMessage.getReqId());
            ResponseHandler responseHandler = responseHandlerMap.get(responseMessage.getReqId());
            if (responseHandler != null) {
                responseHandler.onReceiveResponse(responseMessage, responseChannel);
            }
        } else {
            Class<? extends MessageLite> aClass = ProtocolEntityUtil.getClassByType(type);
            if (aClass != null) {
                List<MessageHandler<? extends MessageLite>> messageHandlers = messageHandlersMap.get(aClass);
                if (messageHandlers != null && messageHandlers.size() > 0) {
                    messageHandlers.forEach(handler -> {
                        try {
                            HANDLE_RECEIVE_THREAD_POOL.submit(() -> handler.onReceive(receivedMessage, responseChannel));
                        } catch (Exception e) {

                        }
                    });
                }
            }
        }
    }

    @SafeVarargs
    @Override
    public final <T extends MessageLite> void addMessageHandlers(Class<T> clazz, MessageHandler<T>... handlers) {
        messageHandlersMap.computeIfAbsent(clazz, k -> Lists.newArrayList()).addAll(Arrays.asList(handlers));
    }

    @Override
    public void addResponseHandler(Integer reqId, ResponseHandler responseHandler) {
        responseHandlerMap.put(reqId, responseHandler);
    }

    @Override
    public void removeResponseHandler(Integer reqId) {
        responseHandlerMap.remove(reqId);
    }

    @Override
    public void removeMessageHandler(MessageHandler<? extends MessageLite> messageHandler) {
        messageHandlersMap.values().forEach(j -> j.removeIf(i -> i.equals(messageHandler)));
    }

    @Override
    public void addEventListener(Event event, EventHandler handler) {
        eventEventHandlersMap.computeIfAbsent(event, k -> Lists.newArrayList()).add(handler);
    }

    @Override
    public void removeEventListener(EventHandler handler) {
        eventEventHandlersMap.values().forEach(i -> i.removeIf(j -> j.equals(handler)));
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        fire(Event.ACTIVE, new RequestChannel(ctx.channel(), this));
        ctx.fireChannelActive();
    }

    @Override
    public void fire(Event event, RequestChannel channel) {
        List<EventHandler> eventHandlers = eventEventHandlersMap.get(event);
        if (eventHandlers != null && eventHandlers.size() > 0) {
            for (EventHandler eventHandler : eventHandlers) {
                EVENT_FIRE_THREAD_POOL.submit(() -> eventHandler.handle(channel));
            }
        }
    }
}
