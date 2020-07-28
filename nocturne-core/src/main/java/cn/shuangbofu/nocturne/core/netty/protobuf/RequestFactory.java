package cn.shuangbofu.nocturne.core.netty.protobuf;

import cn.shuangbofu.nocturne.core.constant.Constants;
import cn.shuangbofu.nocturne.core.netty.message.ReceivedMessage;
import cn.shuangbofu.nocturne.core.netty.message.ResponseMessage;
import cn.shuangbofu.nocturne.protobuf.ExecutorProto.ExecutorRegistry;
import cn.shuangbofu.nocturne.protobuf.*;
import cn.shuangbofu.nocturne.protobuf.HeartBeatProto.HeartBeatRequest;

import java.util.List;

/**
 * Created by shuangbofu on 2020/7/24 下午10:37
 */
public class RequestFactory {

    public static HeartBeatProto.ServerHeartBeat SERVER2WEB_PING = HeartBeatProto.ServerHeartBeat.newBuilder().setMsg(Constants.PING).build();
    public static WebServerProto.WebServerHeartbeatRequest HEARTBEAT_REQUEST2_SERVER = WebServerProto.WebServerHeartbeatRequest.newBuilder().build();

    public static WebServerProto.WebServerRegistry webRegistry(String serverKey) {
        return WebServerProto.WebServerRegistry.newBuilder().setServerKey(serverKey).build();
    }

    public static ExecutorRegistry executorRegistry(String serverKey, String executorKey) {
        return ExecutorRegistry.newBuilder()
                .setServerKey(serverKey)
                .setExecutorKey(executorKey)
                .build();
    }

    public static HeartBeatRequest ping(List<Long> taskList) {
        return HeartBeatRequest.newBuilder()
                .addAllTasks(taskList)
                .setMsg(Constants.PING)
                .build();
    }

    public static TaskProto.SubmitTaskRequest submitTaskRequest(String id, String content) {
        return TaskProto.SubmitTaskRequest.newBuilder()
                .setTaskId(Long.parseLong(id))
                .setContent(content)
                .build();
    }

    public static TaskProto.SubmitTaskResponse submitTaskResponse(boolean accept) {
        return TaskProto.SubmitTaskResponse.newBuilder()
                .setAccept(accept)
                .build();
    }

    public static void main(String[] args) {
        ResponseProto.Response response = ResponseProto.Response.newBuilder()
                .setData(submitTaskResponse(true).toByteString()).build();

        MessageProto.Message message = MessageProto.Message.newBuilder()
                .setData(response.toByteString()).setType(MessageTypeProto.MessageType.response).build();

        ResponseMessage responseMessage = new ResponseMessage(new ReceivedMessage(message));

        TaskProto.SubmitTaskResponse data = responseMessage.getData(TaskProto.SubmitTaskResponse.class);

        System.out.println(data.getAccept());
    }
}
