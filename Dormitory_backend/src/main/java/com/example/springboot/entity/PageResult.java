package com.example.springboot.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

// 这是分页结果
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageResult <T>{
    //页码
    private Long total;
    //当前页数据
    private List<T> rows;


}
