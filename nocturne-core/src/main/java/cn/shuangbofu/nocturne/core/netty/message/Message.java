package cn.shuangbofu.nocturne.core.netty.message;

import cn.shuangbofu.nocturne.protobuf.MessageTypeProto.MessageType;

/**
 * Created by shuangbofu on 2020/7/24 13:11
 */
public interface Message {
    /**
     * 直接返回 ByteString
     *
     * @return
     */
    Object getData();

    MessageType getType();

    int getReqId();
}
