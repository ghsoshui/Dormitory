package com.example.springboot.mapper;

import com.example.springboot.entity.DormRoom;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface DormRoomMapper {

    DormRoom findByRoomId(@Param("dormRoomId") Integer dormRoomId);

    List<DormRoom> findAll();

    /**
     * 根据搜索字符串查找宿舍房间。
     * 搜索可以基于 dormRoomId（例如 "101"），或 dormBuildId。
     * @param search 搜索条件。
     * @return 匹配的宿舍房间列表。
     */
    List<DormRoom> findByCriteria(@Param("search") String search);

    int insert(DormRoom dormRoom);

    int update(DormRoom dormRoom); // 假设对象中包含 dormRoomId 用于 WHERE 条件

    int deleteByRoomId(@Param("dormRoomId") Integer dormRoomId);

    // 根据学生姓名查找有床位被占用的宿舍房间
    DormRoom findByOccupant(@Param("studentName") String studentName);

    // 统计未满员的宿舍房间数量
    int countNonFullRooms();

    // 删除床位信息：将指定床铺字段置空并减少当前人数。
    // 此操作更适合先查询房间数据，在服务层处理后再调用更新。
    // 或者使用特定的映射方法：
    int clearBedAndUpdateCapacity(@Param("dormRoomId") Integer dormRoomId, @Param("bedNameColumn") String bedNameColumn);

    // 统计所有宿舍房间的已住人数总和
    Long sumCurrentCapacity();

    // 统计指定宿舍楼的已住人数总和
    Long sumCurrentCapacityByBuilding(@Param("dormBuildId") Integer dormBuildId);

    // 检查指定房间的特定床位是否被占用。
    // 可以通过 findByRoomId 获取房间后在服务层检查，或编写特定查询。
    // 这里暂定使用 findByRoomId 和服务层逻辑处理。

}
