package com.blog.dao;


import com.blog.entity.Url;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UrlMapper {
    //获得首页的URL
    List<Url> getUrl();


    //获得搜索页的URL
    List<Url> getSearchUrl();

    //获得消息页面的URL
    List<Url> getMessageUrl();

    //获得活动页面的URL
    List<Url> getActivityUrl();


}
