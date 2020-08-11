package cn.shuangbofu.nocturne.web.vo;

import cn.shuangbofu.nocturne.dao.Executor;
import cn.shuangbofu.nocturne.dao.ExecutorGroup;
import cn.shuangbofu.nocturne.dao.enums.ExecutorGroupStatus;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * Created by shuangbofu on 2020/7/29 下午4:47
 */
@Data
@Accessors(chain = true)
public class ExecutorGroupVO {
    private Long id;
    private String name;
    private ExecutorGroupStatus status;
    private List<Executor> executors;

    public static ExecutorGroupVO from(ExecutorGroup group, List<Executor> executors) {
        return new ExecutorGroupVO()
                .setId(group.getId())
                .setExecutors(executors)
                .setName(group.getName())
                .setStatus(group.getStatus());
    }
}
