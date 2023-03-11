package com.blog.entity.dao;

import com.blog.entity.Sort;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SortMapper {
    List<Sort> getSortList(); //获得排序方式集合
}
