package com.blog.service;

import com.blog.entity.Activity;

import java.util.List;
import java.util.Map;

public interface ActivityService {
    Map<String, Object> quertActivity(int id );

    List<Activity> queryOfficialActivityList();

    List<Activity> queryEndOfficialActivityList();

    List<Activity> querySelfActivityList();

    List<Activity> queryEndSelfActivityList();

    List<Activity> queryLikeActivity(String condition);

    /**
     * 新增活动
     *
     * @param bActivity 活动
     * @return 结果
     */
     int insertBActivity(Activity bActivity);

}
