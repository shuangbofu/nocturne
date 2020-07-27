package cn.shuangbofu.nocturne.core.netty.protobuf;

import cn.shuangbofu.nocturne.protobuf.ExecutorProto;
import cn.shuangbofu.nocturne.protobuf.HeartBeatProto;
import cn.shuangbofu.nocturne.protobuf.MessageTypeProto.MessageType;
import cn.shuangbofu.nocturne.protobuf.ResponseProto;
import cn.shuangbofu.nocturne.protobuf.TaskProto;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.protobuf.ByteString;
import com.google.protobuf.MessageLite;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by shuangbofu on 2020/7/23 下午10:38
 */
public class ProtocolEntityUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProtocolEntityUtil.class);
    private static final Map<Class<? extends MessageLite>, MessageType> CLASS_ENUM_MAPPING_CACHE;
    private static final Map<MessageType, Class<? extends MessageLite>> MAPPING_CACHE_REVERSE;
    private static final Map<MessageType, Method> PARSE_FROM_METHOD_CACHE = Maps.newConcurrentMap();

    static {
        CLASS_ENUM_MAPPING_CACHE = new HashMap<>();
        MAPPING_CACHE_REVERSE = new HashMap<>();
        try {
            init2();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static MessageType getTypeByEntityClass(Class<? extends MessageLite> clazz) {
        return CLASS_ENUM_MAPPING_CACHE.get(clazz);
    }

    public static Class<? extends MessageLite> getClassByType(MessageType type) {
        return MAPPING_CACHE_REVERSE.get(type);
    }

    public static Object getInnerData(MessageType messageType, ByteString data) {
        try {
            Method method = PARSE_FROM_METHOD_CACHE.get(messageType);
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

    public static void init2() throws NoSuchMethodException {
        // 手动维护关系
        CLASS_ENUM_MAPPING_CACHE.put(ResponseProto.Response.class, MessageType.response);
        CLASS_ENUM_MAPPING_CACHE.put(HeartBeatProto.HeartBeatRequest.class, MessageType.heartBeat);
        CLASS_ENUM_MAPPING_CACHE.put(ExecutorProto.ExecutorRegistry.class, MessageType.executorRegistry);
        CLASS_ENUM_MAPPING_CACHE.put(TaskProto.SubmitTaskRequest.class, MessageType.submitTaskRequest);
        CLASS_ENUM_MAPPING_CACHE.put(TaskProto.SubmitTaskResponse.class, MessageType.submitTaskResponse);

        for (Map.Entry<Class<? extends MessageLite>, MessageType> entry : CLASS_ENUM_MAPPING_CACHE.entrySet()) {
            MessageType messageType = entry.getValue();
            Class<? extends MessageLite> aClass = entry.getKey();
            MAPPING_CACHE_REVERSE.put(messageType, aClass);

            Method parseFrom = aClass.getMethod("parseFrom", ByteString.class);
            parseFrom.setAccessible(true);
            PARSE_FROM_METHOD_CACHE.put(messageType, parseFrom);

            LOGGER.debug(String.format("%-10s %-10s", messageType, aClass));
        }
    }

//    public static void init() throws Exception {
//        // TODO 可以直接读取类
//
//        MessageType[] types = MessageType.values();
//        URL[] urls = ((URLClassLoader) ClassLoader.getSystemClassLoader()).getURLs();
//        String resourcePath = null;
//        for (URL url : urls) {
//            String path = url.getPath();
//            if (path.contains("/resources/")) {
//                resourcePath = path;
//                break;
//            }
//        }
//        if (resourcePath != null) {
//            String decodedPath = URLDecoder.decode(resourcePath, "utf-8");
//            File file = new File(decodedPath + "/proto/");
//            if (file.isDirectory()) {
//                for (File f : file.listFiles()) {
//                    if (f.getName().contains("message_type")) {
//                        continue;
//                    }
//                    List<String> lines = Files.readLines(f, StandardCharsets.UTF_8);
//                    String all = String.join("\n", lines);
//                    List<String> classNames = getMatches(all, "message ([a-zA-Z]*) \\{");
//
//                    String javaPackage = getMatch(all, "option java_package = \"(.*)\";");
//                    String outClassName = getMatch(all, "option java_outer_classname = \"(.*)\";");
//
//                    for (String className : classNames) {
//                        if (className != null && javaPackage != null && outClassName != null) {
//                            Class<? extends MessageLite> aClass = (Class<? extends MessageLite>) Class.forName(javaPackage + "." + outClassName + "$" + className);
//                            String s = className.substring(0, 1).toLowerCase() + className.substring(1);
//                            CLASS_ENUM_MAPPING_CACHE.putAll(Arrays.stream(types).filter(i -> s.contains(i.name())).collect(Collectors.toMap(i -> aClass, i -> i)));
//                        }
//                    }
//                }
//            }
//        }
//        for (Map.Entry<Class<? extends MessageLite>, MessageType> entry : CLASS_ENUM_MAPPING_CACHE.entrySet()) {
//            MessageType messageType = entry.getValue();
//            Class<? extends MessageLite> aClass = entry.getKey();
//            MAPPING_CACHE_REVERSE.put(messageType, aClass);
//
//            Method parseFrom = aClass.getMethod("parseFrom", ByteString.class);
//            parseFrom.setAccessible(true);
//            PARSE_FROM_METHOD_CACHE.put(messageType, parseFrom);
//
//            LOGGER.debug(String.format("%-10s %-10s", messageType, aClass));
//        }
//    }

    private static List<String> getMatches(String str, String regex) {
        Pattern compile = Pattern.compile(regex);
        Matcher matcher = compile.matcher(str);
        List<String> results = Lists.newArrayList();
        while (matcher.find()) {
            results.add(matcher.group(1));
        }
        return results;
    }

    private static String getMatch(String str, String regex) {
        List<String> matches = getMatches(str, regex);
        if (matches.size() > 0) {
            return matches.get(0);
        }
        return null;
    }

    public static void main(String[] args) throws Exception {
        init2();
//        Enumeration<URL> resources = Thread.currentThread().getContextClassLoader().getResources("cn/shuangbofu/nocturne/protobuf");
//        while (resources.hasMoreElements()) {
//            URL url = resources.nextElement();
//            String path = url.getPath();
//        }
    }
}
