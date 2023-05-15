package com.blog.service.Impl;

import com.blog.config.FanoutConfig;
import com.blog.config.FanoutConfig2;
import com.blog.dao.ActivityMapper;
import com.blog.entity.Activity;
import com.blog.service.ActivityService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ActivityServiceImpl implements ActivityService {
    @Autowired
    private ActivityMapper activityMapper;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private FanoutConfig2 fanoutConfig;
    @Override
    public Map<String, Object> quertActivity(int id) {
        Activity activity = activityMapper.queryActivityById(id);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(activity.getActivityendTime());
        calendar.add(Calendar.DATE, 1);
        Date newDate = calendar.getTime();
        System.out.println(newDate);
        Date date = new Date();
        Map<String,Object> map = new HashMap<>();
        if (date.before(newDate)){
           map.put("data",activity);
           map.put("type",1);
        }else {
            map.put("data",activity);
            map.put("type",0);
        }



        return map;
    }

    @Override
    public List<Activity> queryOfficialActivityList() {
        return activityMapper.queryOfficialActivityList();
    }

    @Override
    public List<Activity> queryEndOfficialActivityList() {
        return activityMapper.queryEndOfficialActivityList();
    }

    @Override
    public List<Activity> querySelfActivityList() {
        return activityMapper.querySelfActivityList();
    }

    @Override
    public List<Activity> queryEndSelfActivityList() {
        return activityMapper.queryEndSelfActivityList();
    }

    @Override
    public List<Activity> queryLikeActivity(String condition) {
        return activityMapper.queryLikeActivityList(condition);

    }

    @Override
    public int insertBActivity(Activity activity) {
    activity.setCreateTime(new Date());
        activityMapper.insertBActivity(activity);
        int id = activity.getId();
        rabbitTemplate.convertAndSend(fanoutConfig.getOrderEventExchange(),fanoutConfig.getOrderCloseDelayRoutingKey(),id);
        return 1;

    }
}
