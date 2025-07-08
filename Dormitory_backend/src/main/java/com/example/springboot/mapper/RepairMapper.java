package com.example.springboot.mapper;

import com.example.springboot.entity.Repair;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface RepairMapper {

    Repair findById(@Param("id") Integer id);

    List<Repair> findAll(); // 供管理员或维修人员查看，按创建时间排序

    List<Repair> findByCriteria(@Param("search") String search); // 按标题或内容搜索

    // 根据维修人和搜索条件查找报修记录
    List<Repair> findByRepairerAndCriteria(@Param("repairerName") String repairerName, @Param("search") String search);

    int insert(Repair repair);

    int update(Repair repair); // 通常用于更新状态和完成时间

    int deleteById(@Param("id") Integer id);

    Long countAll();

}
