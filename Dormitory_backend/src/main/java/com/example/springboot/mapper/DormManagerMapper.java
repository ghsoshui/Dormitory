package com.example.springboot.mapper;

import com.example.springboot.entity.DormManager;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface DormManagerMapper {

    DormManager findByUsername(@Param("username") String username);

    List<DormManager> findAll();

    /**
     * 根据搜索条件查找宿舍管理员，通常匹配用户名或姓名。
     * @param search 搜索关键字。
     * @return 匹配的宿舍管理员列表。
     */
    List<DormManager> findByCriteria(@Param("search") String search);

    int insert(DormManager dormManager);

    /**
     * 更新一个宿舍管理员对象。假设用户名是更新的关键字段。
     * @param dormManager 包含更新信息的宿舍管理员对象。
     * @return 影响的行数。
     */
    int update(DormManager dormManager);

    int deleteByUsername(@Param("username") String username);

    // 登录方法，类似于管理员
    DormManager findByUsernameAndPassword(@Param("username") String username, @Param("password") String password);
}
