package cn.shuangbofu.nocturne.protobuf;

import com.google.common.collect.Maps;
import com.google.protobuf.ByteString;
import com.google.protobuf.MessageLite;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class MappingCache {
    public static final Map<Class<? extends MessageLite>, MessageTypeProto.MessageType> CLASS_ENUM_MAPPING_CACHE = new HashMap<>();
    public static final Map<MessageTypeProto.MessageType, Method> PARSE_FROM_METHOD_CACHE = Maps.newConcurrentMap();
    public static final Map<MessageTypeProto.MessageType, Class<? extends MessageLite>> MAPPING_CACHE_REVERSE;

    static {
        // 不能删掉注释
        // START
        put(cn.shuangbofu.nocturne.protobuf.ExecutorProto.ExecutorRegistry.class, MessageTypeProto.MessageType.executorRegistry);
        put(cn.shuangbofu.nocturne.protobuf.ResponseProto.Response.class, MessageTypeProto.MessageType.response);
        put(cn.shuangbofu.nocturne.protobuf.HeartBeatProto.HeartBeatRequest.class, MessageTypeProto.MessageType.heartBeat);
        put(cn.shuangbofu.nocturne.protobuf.TaskProto.SubmitTaskRequest.class, MessageTypeProto.MessageType.submitTaskRequest);
        put(cn.shuangbofu.nocturne.protobuf.TaskProto.SubmitTaskResponse.class, MessageTypeProto.MessageType.submitTaskResponse);
        put(cn.shuangbofu.nocturne.protobuf.HeartBeatProto.ServerHeartBeat.class, MessageTypeProto.MessageType.serverHeartBeat);
        put(cn.shuangbofu.nocturne.protobuf.WebServerProto.WebServerRegistry.class, MessageTypeProto.MessageType.webServerRegistry);
        put(cn.shuangbofu.nocturne.protobuf.WebServerProto.WebServerHeartbeatRequest.class, MessageTypeProto.MessageType.webServerHeartbeatRequest);
        // END
        MAPPING_CACHE_REVERSE = new HashMap<>(CLASS_ENUM_MAPPING_CACHE.size());
        for (Map.Entry<Class<? extends MessageLite>, MessageTypeProto.MessageType> entry : CLASS_ENUM_MAPPING_CACHE.entrySet()) {
            MessageTypeProto.MessageType messageType = entry.getValue();
            Class<? extends MessageLite> aClass = entry.getKey();
            MAPPING_CACHE_REVERSE.put(messageType, aClass);
            Method parseFrom;
            try {
                parseFrom = aClass.getMethod("parseFrom", ByteString.class);
                parseFrom.setAccessible(true);
                PARSE_FROM_METHOD_CACHE.put(messageType, parseFrom);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
    }

    public static void put(Class<? extends MessageLite> clazz, MessageTypeProto.MessageType messageType) {
        CLASS_ENUM_MAPPING_CACHE.put(clazz, messageType);
    }
}
