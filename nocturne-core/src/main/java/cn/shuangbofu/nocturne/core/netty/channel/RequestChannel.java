package cn.shuangbofu.nocturne.core.netty.channel;

import cn.shuangbofu.nocturne.core.netty.listener.MessageListener;
import cn.shuangbofu.nocturne.core.netty.listener.ResponseHandler;
import cn.shuangbofu.nocturne.core.netty.message.ResponseMessage;
import cn.shuangbofu.nocturne.core.netty.protobuf.ProtocolEntityUtil;
import cn.shuangbofu.nocturne.protobuf.MessageProto;
import cn.shuangbofu.nocturne.protobuf.MessageTypeProto;
import com.google.protobuf.MessageLite;
import io.netty.channel.ChannelFuture;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by shuangbofu on 2020/7/26 下午9:56
 */
public class RequestChannel extends AbstractChannel {

    private static final AtomicInteger REQ_ID_COUNTER = new AtomicInteger(0);

    public RequestChannel(io.netty.channel.Channel channel, MessageListener listener) {
        super(channel, listener);
    }

    private ChannelFuture request0(int reqId, MessageLite messageLite) {
        MessageTypeProto.MessageType messageType = ProtocolEntityUtil.getTypeByEntityClass(messageLite.getClass());
        MessageProto.Message message = buildMessage(reqId, messageType, messageLite.toByteString());
        return writeAndFlush(message);
    }

    public void asyncRequest(MessageLite messageLite, ResponseHandler handler) {
        int reqId = REQ_ID_COUNTER.getAndIncrement();
        request0(reqId, messageLite).addListener(future -> {
            if (future.isSuccess()) {
                getListener().addResponseHandler(reqId, handler);
            } else {
                Throwable cause = future.cause();
                LOGGER.error("请求异常");
                handler.onReceiveResponse(ResponseMessage.error(reqId, cause.getMessage()), new ResponseChannel(channel, reqId, listener));
            }
        });
    }

    public ResponseMessage request(MessageLite messageLite) {
        int reqId = REQ_ID_COUNTER.getAndIncrement();
        LOGGER.debug("request " + messageLite.getClass() + " reqId: " + reqId);
        RequestResponseHandler handler = new RequestResponseHandler(reqId, messageLite);
        getListener().addResponseHandler(reqId, handler);
        ChannelFuture future = request0(reqId, messageLite);
        try {
            handler.timeout(10);
            boolean success = future.await(5, TimeUnit.SECONDS);
            if (success) {
                LOGGER.debug("{} 发送成功！", messageLite.getClass());
                return handler.getResponse();
            } else {
                throw new RuntimeException("发送失败", future.cause());
            }
        } catch (Exception e) {
            LOGGER.error("请求异常！", e);
            return ResponseMessage.error(reqId, e.getMessage());
        } finally {
            getListener().removeResponseHandler(reqId);
        }
    }

    public void req(MessageLite messageLite) {
        int reqId = REQ_ID_COUNTER.getAndIncrement();
        ChannelFuture future = request0(reqId, messageLite);
        try {
            if (!future.await(5, TimeUnit.SECONDS)) {
                throw new RuntimeException("请求超时");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    private static class RequestResponseHandler implements ResponseHandler {
        private final CountDownLatch latch;
        private final String requestEntityName;
        private final int reqId;
        private ResponseMessage response;

        public RequestResponseHandler(int reqId, MessageLite messageLite) {
            this.reqId = reqId;
            requestEntityName = messageLite.getClass().getSimpleName();
            LOGGER.debug("request create");
            latch = new CountDownLatch(1);
        }

        @Override
        public void onReceiveResponse(ResponseMessage response, ResponseChannel channel) {
            LOGGER.debug("get response1, type:{}", response.getType());
            this.response = response;
            latch.countDown();
        }

        public void timeout(int seconds) throws InterruptedException {
            boolean await = latch.await(seconds, TimeUnit.SECONDS);
            if (!await) {
                throw new RuntimeException(String.format("%s「%s」 响应超时！", requestEntityName, reqId));
            }
        }

        public ResponseMessage getResponse() {
            LOGGER.debug("get response0");
            return response;
        }
    }
}
