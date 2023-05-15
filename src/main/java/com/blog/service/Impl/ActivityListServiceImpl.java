package com.blog.service.Impl;

import com.blog.config.FanoutConfig;
import com.blog.dao.ActivityListMapper;
import com.blog.dao.ActivityMapper;
import com.blog.entity.Activity;
import com.blog.entity.Activitylist;
import com.blog.entity.Vo.ActivityListVo;
import com.blog.service.ActivityListService;
import com.blog.util.GetTokenAccountId;
import com.tencentcloudapi.trtc.v20190722.models.EventMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
public class ActivityListServiceImpl implements ActivityListService {
    @Autowired
    private ActivityListMapper activityListMapper;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private FanoutConfig fanoutConfig;

    @Autowired
    private ActivityMapper activityMapper;

    @Override
    public int insertActivityList(String accountId, int activityId, String phone, String name) {
        Activitylist activitylist = new Activitylist();
        activitylist.setAccountId(accountId);
        activitylist.setActivityId(activityId);
        activitylist.setCreateTime(new Date());
        activitylist.setPhone(phone);
        activitylist.setStatus(0);
        activitylist.setName(name);
        String orderId = UUID.randomUUID().toString();
        activitylist.setOrderId(orderId);
        rabbitTemplate.convertAndSend(fanoutConfig.getOrderEventExchange(),fanoutConfig.getOrderCloseDelayRoutingKey(),orderId);
        return activityListMapper.insertActivityList(activitylist);
    }

    @Override
    public List<ActivityListVo> queryUnPayOrder(HttpServletRequest httpServletRequest) {
        String accountId = GetTokenAccountId.getTokenAccountId(httpServletRequest);
        return activityListMapper.queryActivityOrderList(accountId);
    }

    @Override
    public List<ActivityListVo> queryEndPayOrder(HttpServletRequest httpServletRequest) {
        String accountId = GetTokenAccountId.getTokenAccountId(httpServletRequest);
        return activityListMapper.queryEndActivityOrderList(accountId);
    }

    @Override
    public List<ActivityListVo> querySuccessPayOrder(HttpServletRequest httpServletRequest) {
        String accountId = GetTokenAccountId.getTokenAccountId(httpServletRequest);
        return activityListMapper.querySuccessOrderList(accountId);
    }

    @Override
    public ActivityListVo queryOrderIndo(String orderId) {
        return activityListMapper.queryOrderIndex(orderId);
    }

    @Transactional
    @Override
    public Map<String, Object> payOrder(String orderId) {
        activityListMapper.updatePayOrder(orderId);
        Activitylist activityList =activityListMapper.getOrderInfo(orderId);
        Activity activity = activityMapper.queryActivityById(activityList.getActivityId());
        activityMapper.updatePeopleNum(activity.getId(),activity.getActivityPeople()-1);
        Map<String, Object> map = new HashMap<>();
        map.put("code",200);
        return map;
    }

    @Transactional
    @Override
    public Map<String, Object> drawBack(String orderId) {
        Activitylist activityList =activityListMapper.getOrderInfo(orderId);
        Activity activity = activityMapper.queryActivityById(activityList.getActivityId());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(activity.getActivityendTime());
        calendar.add(Calendar.DATE, 1);
        Date newDate = calendar.getTime();
        System.out.println(newDate);
        Date date = new Date();
        Map<String, Object> map = new HashMap<>();
        if (!date.before(newDate)){
            map.put("code",201);
            return map;
        }
        activityListMapper.updateDrawBackOrder(orderId);
        activityMapper.updatePeopleNum(activity.getId(),activity.getActivityPeople()+1);
        map.put("code",200);
        return map;
    }

    @Override
    public List<ActivityListVo> queryJoinSuccess(HttpServletRequest httpServletRequest) {
        String accountId = GetTokenAccountId.getTokenAccountId(httpServletRequest);
        return activityListMapper.queryJoinSuccess(accountId);
    }
}
