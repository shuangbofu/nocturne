package cn.shuangbofu.nocturne.core.netty.protobuf;

import cn.shuangbofu.nocturne.protobuf.MappingCache;
import cn.shuangbofu.nocturne.protobuf.MessageTypeProto.MessageType;
import com.google.protobuf.ByteString;
import com.google.protobuf.MessageLite;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * Created by shuangbofu on 2020/7/23 下午10:38
 */
public class ProtocolEntityUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProtocolEntityUtil.class);

    public static MessageType getTypeByEntityClass(Class<? extends MessageLite> clazz) {
        return MappingCache.CLASS_ENUM_MAPPING_CACHE.get(clazz);
    }

    public static Class<? extends MessageLite> getClassByType(MessageType type) {
        return MappingCache.MAPPING_CACHE_REVERSE.get(type);
    }

    public static Object getInnerData(MessageType messageType, ByteString data) {
        try {
            Method method = MappingCache.PARSE_FROM_METHOD_CACHE.get(messageType);
            if (method != null) {
                return method.invoke(null, data);
            }
        } catch (Exception e) {
            LOGGER.warn("转换异常", e);
        }
        return data;
    }

    public static Object getInnerData(Class<?> tClass, ByteString data) {
        String s = data.toStringUtf8();
        if (MessageLite.class.isAssignableFrom(tClass)) {
            return getInnerData(getTypeByEntityClass((Class<? extends MessageLite>) tClass), data);
        } else if (tClass.isAssignableFrom(Integer.class)) {
            return Integer.parseInt(s);
        } else if (tClass.isAssignableFrom(String.class)) {
            return s;
        } else if (tClass.isAssignableFrom(Long.class)) {
            return Long.parseLong(s);
        } else if (tClass.isAssignableFrom(Boolean.class)) {
            return Boolean.valueOf(s);
        } else {
            throw new RuntimeException("not supported");
        }
    }
}
