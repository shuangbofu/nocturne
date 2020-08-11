package cn.shuangbofu.nocturne.dao.enums;

import io.github.biezhi.anima.annotation.EnumMapping;
import lombok.AllArgsConstructor;

/**
 * Created by shuangbofu on 2020/7/29 下午2:26
 */
@EnumMapping("executorStatusValue")
@AllArgsConstructor
public enum ExecutorStatus {

    /**
     *
     */
    ONLINE(1, "上线"),
    OFFLINE(2, "下线"),

    ;

    int executorStatusValue;
    private String description;
}
