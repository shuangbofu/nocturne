package cn.shuangbofu.nocturne.core.netty.event;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by shuangbofu on 2020/7/26 下午11:53
 */
public interface Event {

    Event REGISTER = new ChannelEvent();
    Event UNREGISTER = new ChannelEvent();
    Event ACTIVE = new ChannelEvent();
    Event CONNECTED = new ChannelEvent();

    class ChannelEvent implements Event {
        private static final AtomicInteger IDS = new AtomicInteger(0);
        private final int id;

        public ChannelEvent() {
            id = IDS.incrementAndGet();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            ChannelEvent that = (ChannelEvent) o;
            return id == that.id;
        }

        @Override
        public int hashCode() {
            return Objects.hash(id);
        }
    }
}
