package cn.shuangbofu.nocturne.server.handler;

import cn.shuangbofu.nocturne.core.constant.Constants;
import cn.shuangbofu.nocturne.core.netty.annotation.OnReceive;
import cn.shuangbofu.nocturne.core.netty.channel.ResponseChannel;
import cn.shuangbofu.nocturne.protobuf.ExecutorProto;
import cn.shuangbofu.nocturne.protobuf.HeartBeatProto;
import cn.shuangbofu.nocturne.server.service.ExecutorStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by shuangbofu on 2020/7/27 11:57
 */
@OnReceive
public class FromExecutorHandlerSet {

    private static final Logger LOGGER = LoggerFactory.getLogger(FromExecutorHandlerSet.class);

    @OnReceive
    public String handleHeartBeat(HeartBeatProto.HeartBeatRequest heartBeatRequest, ResponseChannel channel) {
        if (Constants.PING.equals(heartBeatRequest.getMsg())) {
            // TODO server操作
            List<Long> tasksList = heartBeatRequest.getTasksList();
            ExecutorStore.INSTANCE.heartbeat(channel, tasksList);
            LOGGER.info("收到心跳 ❤");
            LOGGER.info("{} ==> {}", channel.getRemoteAddress(), tasksList);
            return Constants.PONG;
        }
        return null;
    }

    @OnReceive
    public boolean handleExecutorRegistry(ExecutorProto.ExecutorRegistry registry, ResponseChannel channel) {
        ExecutorStore.INSTANCE.register(channel, registry.getServerKey(), registry.getExecutorKey());
        return true;
    }
}
