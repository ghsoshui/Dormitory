package com.example.springboot.mapper;

import com.example.springboot.entity.Notice;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface NoticeMapper {

    Notice findById(@Param("id") Integer id);

    List<Notice> findAll();

    /**
     * 根据标题查找通知（不区分大小写，支持模糊匹配）。
     * @param title 标题搜索条件。
     * @return 匹配的通知列表。
     */
    List<Notice> findByTitle(@Param("title") String title);

    /**
     * 查找所有通知，并按发布时间降序排序。
     * @return 按发布时间排序的通知列表。
     */
    List<Notice> findAllOrderByReleaseTimeDesc();

    int insert(Notice notice);

    int update(Notice notice);

    int deleteById(@Param("id") Integer id);
}
