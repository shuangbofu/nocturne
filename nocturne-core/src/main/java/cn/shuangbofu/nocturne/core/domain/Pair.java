package cn.shuangbofu.nocturne.core.domain;

import java.util.Objects;

/**
 * Created by shuangbofu on 2020/7/25 上午9:42
 */
public class Pair<A, B> {

    private final A first;
    private final B second;

    public Pair(A first, B second) {
        this.first = first;
        this.second = second;
    }

    public A getFirst() {
        return first;
    }

    public B getSecond() {
        return second;
    }

    @Override
    public int hashCode() {
        return Objects.hash(first, second);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (obj instanceof cn.shuangbofu.nocturne.core.domain.Pair<?, ?>) {
            cn.shuangbofu.nocturne.core.domain.Pair<?, ?> other = (cn.shuangbofu.nocturne.core.domain.Pair<?, ?>) obj;
            return Objects.equals(first, other.first) && Objects.equals(second, other.second);
        }

        return false;
    }

    @Override
    public String toString() {
        return "Pair {first=" + Objects.toString(first) + ", second=" + Objects.toString(second) + "}";
    }

}
