package cn.shuangbofu.nocturne.dao.enums;

import io.github.biezhi.anima.annotation.EnumMapping;
import lombok.AllArgsConstructor;

/**
 * Created by shuangbofu on 2020/8/1 下午8:17
 */
@AllArgsConstructor
@EnumMapping("executorGroupStatusValue")
public enum ExecutorGroupStatus {

    /**
     *
     */
    ENABLED(2),
    DISABLED(1),
    DELETED(0);


    private int executorGroupStatusValue;
}
