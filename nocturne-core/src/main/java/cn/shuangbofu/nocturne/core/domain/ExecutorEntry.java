package cn.shuangbofu.nocturne.core.domain;

import cn.shuangbofu.nocturne.core.netty.channel.RequestChannel;
import lombok.Data;

import java.util.Objects;

/**
 * Created by shuangbofu on 2020/7/24 下午10:23
 */
@Data
public class ExecutorEntry {
    private String ip;
    private int port;
    private int groupId;
    private RequestChannel channel;

    public ExecutorEntry(RequestChannel channel, int groupId) {
        Pair<String, Integer> remoteInfo = channel.getRemoteInfo();
        ip = remoteInfo.getFirst();
        port = remoteInfo.getSecond();
        this.channel = channel;
        this.groupId = groupId;
    }

    @Override
    public String toString() {
        return String.format("%s:%s\t[%s]", ip, port, groupId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ExecutorEntry that = (ExecutorEntry) o;
        return port == that.port &&
                ip.equals(that.ip);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ip, port);
    }
}
