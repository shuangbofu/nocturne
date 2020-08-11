package cn.shuangbofu.nocturne.dao;

import cn.shuangbofu.nocturne.dao.enums.ExecutorStatus;
import io.github.biezhi.anima.annotation.Table;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Created by shuangbofu on 2020/7/29 下午2:25
 */
@Accessors(chain = true)
@Table(name = "executor")
@Data
public class Executor extends Model<Executor> {
    private Long id;
    private Long gmtCreate;
    private Long gmtModified;

    private String ip;
    private Integer port;

    private Long groupId;

    private ExecutorStatus status;
}
