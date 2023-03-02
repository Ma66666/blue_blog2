package com.blog.dao;


import com.blog.entity.Url;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UrlMapper {
    List<Url> getUrl();


}
