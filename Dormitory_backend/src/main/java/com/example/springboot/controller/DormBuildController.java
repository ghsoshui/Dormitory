package com.example.springboot.controller;

import com.example.springboot.common.Result;
import com.example.springboot.entity.DormBuild;
import com.example.springboot.service.DormBuildService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/building")
public class DormBuildController {

    @Resource
    private DormBuildService dormBuildService;

    /**
     * 楼宇添加
     */
    @PostMapping("/add")
    public Result<?> add(@RequestBody DormBuild dormBuild) {
        int i = dormBuildService.addDormBuild(dormBuild);
        if (i > 0) {
            return Result.success();
        } else {
            return Result.error("-1", "添加失败");
        }
    }

    /**
     * 楼宇信息更新
     */
    @PutMapping("/update")
    public Result<?> update(@RequestBody DormBuild dormBuild) {
        if (dormBuild.getId() == null) {
            return Result.error("-1", "更新失败：ID不能为空");
        }
        int i = dormBuildService.updateDormBuild(dormBuild);
        if (i > 0) {
            return Result.success();
        } else {
            return Result.error("-1", "更新失败");
        }
    }

    /**
     * 楼宇删除
     */
    @DeleteMapping("/delete/{id}") // 将路径变量改为 id 以匹配 service/mapper 的参数
    public Result<?> delete(@PathVariable Integer id) {
        int i = dormBuildService.deleteDormBuildById(id);
        if (i > 0) { // Changed from i == 1
            return Result.success();
        } else {
            return Result.error("-1", "删除失败");
        }
    }

    /**
     * 楼宇查找 (使用 PageHelper 和 PageResult 进行分页)
     */
    @GetMapping("/find")
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "") String search) {
        PageInfo<DormBuild> pageInfo = dormBuildService.findPaginatedDormBuilds(pageNum, pageSize, search);
        return Result.success(pageInfo);
    }

    /**
     * 首页Echarts 获取楼宇信息 (返回楼宇业务ID列表)
     */
    @GetMapping("/getBuildingName")
    public Result<?> getBuildingName() {
        List<DormBuild> allBuilds = dormBuildService.findAllDormBuilds();
        List<Integer> buildingIds = allBuilds.stream()
                .map(DormBuild::getDormBuildId) // 使用方法引用
                .collect(Collectors.toList());
        return !buildingIds.isEmpty() ?
                Result.success(buildingIds) : Result.error("-1", "查询失败"); // Consider returning success with empty list
    }
}
