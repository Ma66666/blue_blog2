package com.blog.dao;

import com.blog.entity.Topic;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TopicMapper {
    List<Topic> getTopicList() ; //获得话题集合

}
