package cn.shuangbofu.nocturne.core.utils;

import java.net.*;
import java.util.Enumeration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by shuangbofu on 2020/7/22 下午11:18
 */
public class IPUtils {
    private static final Pattern IP_PATTERN = Pattern
            .compile("(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)");
    private static final String LOCALHOST = "127.0.0.1";


    public static String getIpV4Address() throws UnknownHostException, SocketException {
        String ip = Inet4Address.getLocalHost().getHostName();
        Matcher m = IP_PATTERN.matcher(ip);
        if (m.matches() && !LOCALHOST.equals(ip)) {
            return ip;
        }

        Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
        while (networkInterfaces.hasMoreElements()) {
            NetworkInterface networkInterface = networkInterfaces.nextElement();
            Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();
            while (inetAddresses.hasMoreElements()) {
                InetAddress inetAddress = inetAddresses.nextElement();
                if (inetAddress instanceof Inet4Address && !inetAddress.isLoopbackAddress() && !inetAddress.isLinkLocalAddress()) {
                    return inetAddress.getHostAddress();
                }
            }
        }
        return null;
    }
}

