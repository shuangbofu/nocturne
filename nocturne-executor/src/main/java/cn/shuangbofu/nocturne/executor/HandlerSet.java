package cn.shuangbofu.nocturne.executor;

import cn.shuangbofu.nocturne.core.netty.annotation.OnReceive;
import cn.shuangbofu.nocturne.core.netty.channel.ResponseChannel;
import cn.shuangbofu.nocturne.core.netty.protobuf.RequestFactory;
import cn.shuangbofu.nocturne.protobuf.TaskProto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.SecureRandom;

/**
 * Created by shuangbofu on 2020/7/27 下午9:45
 */
@OnReceive
public class HandlerSet {

    private static final Logger LOGGER = LoggerFactory.getLogger(HandlerSet.class);

    @OnReceive
    public void handle(TaskProto.SubmitTaskRequest taskRequest, ResponseChannel channel) {
        long taskId = taskRequest.getTaskId();
        // 根据自定义执行策略检查任务执行前是否符合执行标准，比如内存、cpu、yarn来判断
        // 随机是否接受
        boolean accept = new SecureRandom().nextBoolean();
        LOGGER.info("{}任务「{}」", accept ? "接受" : "拒绝", taskId);
        // 返回接受
        channel.success(RequestFactory.submitTaskResponse(accept));
        if (!accept) {
            return;
        }
        LOGGER.info("准备执行任务「{}」！", taskId);
        long start = System.currentTimeMillis();
        TaskPool.INSTANCE.put(taskId);
        try {
            Thread.sleep((new SecureRandom().nextInt(10) + 10) * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        LOGGER.info("任务「{}」执行完成！耗时{}s", taskId, ((System.currentTimeMillis() - start) / 1000));
        TaskPool.INSTANCE.remove(taskId);
        // TODO 返回任务执行后的状态
    }
}
