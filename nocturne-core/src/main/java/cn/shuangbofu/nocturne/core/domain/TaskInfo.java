package cn.shuangbofu.nocturne.core.domain;

import lombok.Data;

/**
 * Created by shuangbofu on 2020/7/22 14:37
 */
@Data
public class TaskInfo {
    private String id;
    private String name;
    private TaskType taskType;
    // 优先级
    private int priority;
    // 执行组
    private long executorGroupId;
    // 用户组
    private long userGroupId;
    // 项目组
    private long projectId;
    // 重试次数
    private long retryCount;
    // 重试间隔
    private long retryInterval;
}
