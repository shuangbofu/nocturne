package cn.shuangbofu.nocturne.core.netty.message;

import cn.shuangbofu.nocturne.core.netty.protobuf.ProtocolEntityUtil;
import cn.shuangbofu.nocturne.protobuf.MessageProto.Message;
import cn.shuangbofu.nocturne.protobuf.MessageTypeProto.MessageType;
import com.google.protobuf.ByteString;
import com.google.protobuf.MessageLite;

/**
 * Created by shuangbofu on 2020/7/24 13:16
 */
public class ReceivedMessage<B extends MessageLite> implements cn.shuangbofu.nocturne.core.netty.message.Message {
    private final Message message;
    private final Class<B> innerClass;

    public ReceivedMessage(Message message) {
        this.message = message;
        MessageType type = getType();
        innerClass = (Class<B>) ProtocolEntityUtil.getClassByType(type);
    }

    public B getInnerEntity() {
        Object data = getData0();
        if (!innerClass.isInstance(data)) {
            throw new ClassCastException(String.format("cast to (%s) not supported", innerClass));
        }
        return (B) data;
    }

    @Override
    public Object getData() {
        return getData0();
    }

    protected Object getData0() {
        return getInnerData(message.getType(), message.getData());
    }

    protected Object getInnerData(MessageType messageType, ByteString byteString) {
        return ProtocolEntityUtil.getInnerData(messageType, byteString);
    }

    @Override
    public MessageType getType() {
        return message.getType();
    }

    @Override
    public int getReqId() {
        return message.getReqId();
    }
}
