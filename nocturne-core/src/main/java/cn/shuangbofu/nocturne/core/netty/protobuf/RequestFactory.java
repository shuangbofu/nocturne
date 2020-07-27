package cn.shuangbofu.nocturne.core.netty.protobuf;

import cn.shuangbofu.nocturne.core.Constants;
import cn.shuangbofu.nocturne.core.netty.message.ReceivedMessage;
import cn.shuangbofu.nocturne.core.netty.message.ResponseMessage;
import cn.shuangbofu.nocturne.protobuf.ExecutorProto.ExecutorRegistry;
import cn.shuangbofu.nocturne.protobuf.HeartBeatProto.HeartBeatRequest;
import cn.shuangbofu.nocturne.protobuf.MessageProto;
import cn.shuangbofu.nocturne.protobuf.MessageTypeProto;
import cn.shuangbofu.nocturne.protobuf.ResponseProto;
import cn.shuangbofu.nocturne.protobuf.TaskProto;

import java.util.List;

/**
 * Created by shuangbofu on 2020/7/24 下午10:37
 */
public class RequestFactory {

    public static ExecutorRegistry executorRegistry(int groupId) {
        return ExecutorRegistry.newBuilder()
                .setGroupId(groupId)
                .build();
    }

    public static HeartBeatRequest ping(List<Long> taskList) {
        return HeartBeatRequest.newBuilder()
                .addAllTasks(taskList)
                .setMsg(Constants.PING)
                .build();
    }

    public static TaskProto.SubmitTaskRequest submitTaskRequest(long id, String content) {
        return TaskProto.SubmitTaskRequest.newBuilder()
                .setTaskId(id)
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
