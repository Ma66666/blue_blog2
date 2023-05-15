package com.blog.dao;

import com.blog.entity.Activity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ActivityMapper {

    Activity queryActivityById(int id);

    List<Activity> queryOfficialActivityList(); //查询当前在进行中的官方活动

    List<Activity> queryEndOfficialActivityList(); //查询过期的官方活动

    List<Activity> querySelfActivityList(); //查询当前在进行中的官方活动

    List<Activity> queryEndSelfActivityList(); //查询过期的官方活动

    int updatePeopleNum(@Param("id") int id, @Param("Num") int num);

    List<Activity> queryLikeActivityList(String condition);

    int insertBActivity(Activity bActivity);

    int updateActivityStatus(@Param("id") int id, @Param("status") int num);

}
