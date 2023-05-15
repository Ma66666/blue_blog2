package com.blog.controller;


import com.blog.service.ActivityListService;
import com.blog.util.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Api(tags="活动页面")
@RestController
@RequestMapping("api/activityList")
@CrossOrigin
public class ActivityListController {
    @Autowired
    private ActivityListService activityListService;

    @ApiOperation(value = "活动报名插入人员信息")
    @PostMapping( "insertActivityList")
    public Result insertActivityList(@RequestParam(value = "accountId") String accountId,
                                     @RequestParam(value = "activityId") int  activityId,
                                     @RequestParam(value = "phone") String phone,
                                     @RequestParam(value = "name") String name
    ){
        return Result.ok(activityListService.insertActivityList(accountId,activityId,phone,name));
    }

    @ApiOperation(value = "查询我未支付订单")
    @GetMapping("getMyUnpayOrder")
    public Result getMyUnpayOrder(HttpServletRequest httpServletRequest){
        return Result.ok(activityListService.queryUnPayOrder(httpServletRequest));
    }

    @ApiOperation(value = "查询我但未支付但已经过期订单")
    @GetMapping("getMyEndpayOrder")
    public Result getMyEndpayOrder(HttpServletRequest httpServletRequest){
        return Result.ok(activityListService.queryEndPayOrder(httpServletRequest));
    }

    @ApiOperation(value = "查询我的订单详细")
    @GetMapping("getOrderInfo")
    public Result getOrderInfo(@RequestParam(value = "orderId") String orderId){
        return Result.ok(activityListService.queryOrderIndo(orderId));
    }

    @ApiOperation(value = "支付订单")
    @PostMapping("payOrder")
    public Result payOrder(@RequestParam(value = "orderId") String orderId){
        return Result.ok(activityListService.payOrder(orderId));
    }

    @ApiOperation(value = "支付订单")
    @PostMapping("drawBack")
    public Result drawBack(@RequestParam(value = "orderId") String orderId){
        return Result.ok(activityListService.drawBack(orderId));
    }
    @ApiOperation(value = "查询我支付成功的订单")
    @GetMapping("getMySuccessOrder")
    public Result getMySuccessOrder(HttpServletRequest httpServletRequest){
        return Result.ok(activityListService.querySuccessPayOrder(httpServletRequest));
    }

    @ApiOperation(value = "查询我参加的成功的订单")
    @GetMapping("getJoinSuccess")
    public Result getJoinSuccess(HttpServletRequest httpServletRequest){
        return Result.ok(activityListService.queryJoinSuccess(httpServletRequest));
    }
}
