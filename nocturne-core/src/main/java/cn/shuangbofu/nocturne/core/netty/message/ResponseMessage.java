package cn.shuangbofu.nocturne.core.netty.message;

import cn.shuangbofu.nocturne.core.netty.protobuf.ProtocolEntityUtil;
import cn.shuangbofu.nocturne.protobuf.MessageTypeProto.MessageType;
import cn.shuangbofu.nocturne.protobuf.ResponseProto;
import cn.shuangbofu.nocturne.protobuf.ResponseProto.Response;

/**
 * Created by shuangbofu on 2020/7/24 13:17
 */
public class ResponseMessage implements Message {

    private final Response response;

    public ResponseMessage(ReceivedMessage<Response> receivedMessage) {
        response = receivedMessage.getInnerEntity();
    }

    private ResponseMessage(int reqId, String msg) {
        response = ResponseProto.Response.newBuilder().setMessage(msg).setSuccess(false).setReqId(reqId).build();
    }

    public static ResponseMessage error(int reqId, String msg) {
        return new ResponseMessage(reqId, msg);
    }

    public <T> T getData(Class<T> tClass) {
        return (T) ProtocolEntityUtil.getInnerData(tClass, response.getData());
    }

    @Override
    public Object getData() {
        return response.getData();
    }

    @Override
    public MessageType getType() {
        return MessageType.response;
    }

    /**
     * 这里的reqId是返回时携带的
     *
     * @return
     */
    @Override
    public int getReqId() {
        return response.getReqId();
    }

    public boolean isSuccess() {
        return response.getSuccess();
    }

    public String getMessage() {
        return response.getMessage();
    }
}
