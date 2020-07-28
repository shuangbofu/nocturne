package cn.shuangbofu.nocturne.protobuf;

import com.google.common.collect.Lists;
import com.google.common.io.Files;
import com.google.protobuf.MessageLite;

import java.io.*;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Created by shuangbofu on 2020/7/28 下午11:11
 */
public class MappingCacheGenerator {
    private static final MessageTypeProto.MessageType[] TYPES = MessageTypeProto.MessageType.values();
    private static final Map<Class<? extends MessageLite>, MessageTypeProto.MessageType> map = new HashMap<>();

    public static void init() throws Exception {
        // TODO 可以直接读取类
        URL[] urls = ((URLClassLoader) ClassLoader.getSystemClassLoader()).getURLs();
        String resourcePath = null;
        for (URL url : urls) {
            String path = url.getPath();
            if (path.contains("/resources/")) {
                resourcePath = path;
                break;
            }
        }
        if (resourcePath != null) {
            String decodedPath = URLDecoder.decode(resourcePath, "utf-8");
            File file = new File(decodedPath + "/proto/");
            readFromFiles(file);
        }
    }

    public static void readFromFiles(File file) throws Exception {
        if (file.isDirectory()) {
            for (File f : Objects.requireNonNull(file.listFiles())) {
                readFromFiles(f);
            }
        } else {
            if (file.getName().contains("message_type")) {
                return;
            }
            List<String> lines = Files.readLines(file, StandardCharsets.UTF_8);
            String all = String.join("\n", lines);
            List<String> classNames = getMatches(all, "message ([a-zA-Z]*) \\{");

            String javaPackage = getMatch(all, "option java_package = \"(.*)\";");
            String outClassName = getMatch(all, "option java_outer_classname = \"(.*)\";");

            for (String className : classNames) {
                if (className != null && javaPackage != null && outClassName != null) {
                    Class<? extends MessageLite> aClass = (Class<? extends MessageLite>) Class.forName(javaPackage + "." + outClassName + "$" + className);
                    String s = className.substring(0, 1).toLowerCase() + className.substring(1);
                    map.putAll(Arrays.stream(TYPES).filter(i -> s.contains(i.name())).collect(Collectors.toMap(i -> aClass, i -> i)));
                }
            }
        }
    }

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
        init();
        Class<MappingCache> clazz = MappingCache.class;
        String path = clazz.getClassLoader().getResource(".").getPath();
        String className = "/src/main/java/" + clazz.getPackage().getName().replace(".", "/") +
                "/" + clazz.getSimpleName() + ".java";
        File file = new File(path.replace("/build/classes/java/main/", className));

        StringBuilder replaceContent = new StringBuilder();
        for (Map.Entry<Class<? extends MessageLite>, MessageTypeProto.MessageType> entry : map.entrySet()) {
            String cName = entry.getKey().getName().replace("$", ".");
            replaceContent.append(String.format("        put(%s.class, MessageTypeProto.MessageType.%s);\n", cName, entry.getValue()));
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        StringBuilder content = new StringBuilder();
        boolean read = true;
        while (true) {
            String line = reader.readLine();
            if (line == null) {
                break;
            }
            if (line.contains("// END")) {
                read = true;
            }

            if (read) {
                content.append(line).append("\n");
            }

            if (line.contains("// START")) {
                read = false;
                content.append("%s");
            }
        }
        // /Users/shuangbofu/Documents/project/study/nocturne/nocturne-protocol/src/main/java/cn/shuangbofu/nocturne/protobuf/MappingCache.java
        String fileString = String.format(content.toString(), replaceContent.toString());
        FileWriter writer = new FileWriter(file);
        writer.write(fileString);
        writer.flush();
        writer.close();
        reader.close();
    }
}
