package cn.shuangbofu.nocturne.core.netty.channel;

import cn.shuangbofu.nocturne.core.domain.Pair;
import cn.shuangbofu.nocturne.core.netty.listener.MessageListener;
import cn.shuangbofu.nocturne.protobuf.MessageProto.Message;
import cn.shuangbofu.nocturne.protobuf.MessageTypeProto.MessageType;
import com.google.protobuf.ByteString;
import io.netty.channel.ChannelFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.SocketAddress;
import java.util.Objects;

/**
 * Created by shuangbofu on 2020/7/23 16:48
 */
public abstract class AbstractChannel implements Channel {

    protected static final Logger LOGGER = LoggerFactory.getLogger(AbstractChannel.class);

    protected MessageListener listener;
    protected io.netty.channel.Channel channel;

    public AbstractChannel(io.netty.channel.Channel channel, MessageListener listener) {
        this.channel = channel;
        this.listener = listener;
    }

    @Override
    public ChannelFuture writeAndFlush(Message message) {
        return channel.writeAndFlush(message);
    }

    protected Message buildMessage(int reqId, MessageType type, ByteString data) {
        return Message.newBuilder()
                .setType(type)
                .setReqId(reqId)
                .setData(data)
                .build();
    }

    @Override
    public Pair<String, Integer> getLocalInfo() {
        return address2Info(getLocalAddress());
    }

    @Override
    public Pair<String, Integer> getRemoteInfo() {
        return address2Info(getRemoteAddress());
    }

    @Override
    public String getRemoteAddress() {
        return getAddress(channel.remoteAddress());
    }

    @Override
    public String getLocalAddress() {
        return getAddress(channel.localAddress());
    }

    private Pair<String, Integer> address2Info(String address) {
        String[] token = address.split(":");
        return new Pair<>(token[0], Integer.parseInt(token[1]));
    }

    private String getAddress(SocketAddress socketAddress) {
        return socketAddress.toString().replace("/", "");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }
        AbstractChannel that = (AbstractChannel) o;
        return channel.equals(that.channel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(channel);
    }

    @Override
    public MessageListener getListener() {
        return listener;
    }

    @Override
    public io.netty.channel.Channel getChannel() {
        return channel;
    }
}
