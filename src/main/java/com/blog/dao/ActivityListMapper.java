package com.blog.dao;

import com.blog.entity.Activitylist;
import com.blog.entity.Vo.ActivityListVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ActivityListMapper {
    int insertActivityList(Activitylist activitylist); //用户报名

    List<ActivityListVo> queryActivityOrderList(String accountId); //查询某用户未过期的订单列表

    List<ActivityListVo> queryEndActivityOrderList(String accountId);//查询某用户过期的订单列表

    List<ActivityListVo> querySuccessOrderList(String accountId);//查询用户完成的订单列表

    ActivityListVo queryOrderIndex(String orderId); //查询用户订单

    int updateOrder(String orderId);  //过期订单修改订单状态，在消息队列中使用

    int updatePayOrder(String orderId); //修改订单状态

    int updateDrawBackOrder(String orderId);//退款

    Activitylist getOrderInfo(String orderId); //获得订单信息

    List<ActivityListVo> queryJoinSuccess(String accountId);

    int queryCountOrder(int id);

    int setOrderStatus(int id);



}
