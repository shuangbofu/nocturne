package cn.shuangbofu.nocturne.web.controller;

import cn.shuangbofu.nocturne.dao.Executor;
import cn.shuangbofu.nocturne.dao.ExecutorGroup;
import cn.shuangbofu.nocturne.dao.loader.ExecutorLoader;
import cn.shuangbofu.nocturne.web.vo.ExecutorGroupVO;
import cn.shuangbofu.nocturne.web.vo.Result;
import com.blade.mvc.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by shuangbofu on 2020/7/29 下午4:41
 */
@Path
public class ExecutorController {

    @PostRoute("/executor/group")
    @JSON
    public Result<Boolean> createGroup(@BodyParam ExecutorGroup group) {
        ExecutorLoader.createGroup(group.getName());
        return Result.success(true);
    }

    @GetRoute("/executors")
    @JSON
    public Result<List<ExecutorGroupVO>> executorList() {
        List<ExecutorGroup> groups = ExecutorLoader.listAllGroups();
        List<Executor> executors = ExecutorLoader.listAllExecutors();
        return Result.success(groups.stream().map(g -> ExecutorGroupVO.from(g, executors.stream()
                .filter(e -> e.getGroupId().equals(g.getId()))
                .collect(Collectors.toList()))).collect(Collectors.toList()));
    }
}
