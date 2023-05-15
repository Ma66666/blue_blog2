package com.blog.service;

import com.blog.entity.Activitylist;
import com.blog.entity.Vo.ActivityListVo;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface ActivityListService {

   int insertActivityList( String accountId,
                          int  activityId,
                           String phone,
                          String name);

   List<ActivityListVo> queryUnPayOrder(HttpServletRequest httpServletRequest); //查询我未支付的但还在时间之内的订单

   List<ActivityListVo> queryEndPayOrder(HttpServletRequest httpServletRequest); //查询我未支付的但已经过期的订单

   List<ActivityListVo> querySuccessPayOrder(HttpServletRequest httpServletRequest);//查询我已经支付的订单

   ActivityListVo queryOrderIndo(String orderId); //查询订单信息

   Map<String,Object> payOrder(String orderId); //支付

   Map<String,Object> drawBack(String orderId); //退款

   List<ActivityListVo> queryJoinSuccess(HttpServletRequest httpServletRequest);//查询我已经支付的订单
}
