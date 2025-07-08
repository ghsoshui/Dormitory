package com.example.springboot.service.impl;

import com.example.springboot.entity.Repair;
import com.example.springboot.entity.PageResult;
import com.example.springboot.mapper.RepairMapper;
import com.example.springboot.service.RepairService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class RepairServiceImpl implements RepairService {

    @Resource
    private RepairMapper repairMapper;

    @Override
    public Long getRepairCount() {
        return repairMapper.countAll();
    }

    @Override
    public int addRepair(Repair repair) {
        // 可考虑自动设置报修时间，并将状态默认设为“未完成”
        // 示例：repair.setOrderBuildTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        // repair.setState("未完成");
        return repairMapper.insert(repair);
    }

    @Override
    public PageInfo<Repair> findRepairs(Integer pageNum, Integer pageSize, String search) {
        PageHelper.startPage(pageNum, pageSize);
        List<Repair> repairList;
        if (StringUtils.hasText(search)) {
            repairList = repairMapper.findByCriteria(search);
        } else {
            repairList = repairMapper.findAll();
        }
        return new PageInfo<>(repairList);
    }

    @Override
    public PageInfo<Repair> findRepairsByRepairer(Integer pageNum, Integer pageSize, String search, String repairerName) {
        PageHelper.startPage(pageNum, pageSize);
        // 此特定查询中 repairerName 不应为空
        List<Repair> repairList = repairMapper.findByRepairerAndCriteria(repairerName, search);
        return new PageInfo<>(repairList);
    }

    @Override
    public int updateRepair(Repair repair) {
        if (repair.getId() == null) {
            return 0;  // 如果 ID 为空，返回 0或抛出异常
        }

        return repairMapper.update(repair);
    }

    @Override
    public int deleteRepair(Integer id) {
        return repairMapper.deleteById(id);
    }

    @Override
    public Repair getRepairById(Integer id) {
        return repairMapper.findById(id);
    }
}
