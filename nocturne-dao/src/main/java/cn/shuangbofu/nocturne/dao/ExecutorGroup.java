package cn.shuangbofu.nocturne.dao;

import cn.shuangbofu.nocturne.dao.enums.ExecutorGroupStatus;
import io.github.biezhi.anima.annotation.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * Created by shuangbofu on 2020/7/29 下午2:24
 */
@Accessors(chain = true)
@Table(name = "executor_group")
@Data
@EqualsAndHashCode(callSuper = false)
public class ExecutorGroup extends Model<ExecutorGroup> {
    private Long id;
    private Long gmtCreate;
    private Long gmtModified;

    private String name;
    private String authKey;

    private ExecutorGroupStatus status;
}
