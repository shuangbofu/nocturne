package cn.shuangbofu.nocturne.core.utils;

import java.util.List;
import java.util.Optional;

/**
 * Created by shuangbofu on 2020/7/22 15:45
 */
public class DTUtil {

    /**
     * 轮询算法
     *
     * @param lists
     * @param <T>
     * @return
     */
    public static <T> T roundRobinGet(List<T> lists) {
        for (T list : lists) {

        }
        return null;
    }

    public static <T> T ofElse(T tOf, T tElse) {
        return Optional.of(tOf).orElse(tElse);
    }

//    public static <T> List<T> combineList(List<T>... lists) {
//        List<T> res = Lists.newArrayList();
//        for (List<T> list : lists) {
//            res.addAll(list);
//        }
//        return res;
//    }
}
