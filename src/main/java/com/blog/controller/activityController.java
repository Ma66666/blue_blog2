package com.blog.controller;


import com.blog.entity.Activity;
import com.blog.service.ActivityService;
import com.blog.util.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Api(tags="活动页面")
@RestController
@RequestMapping("api/activity")
@CrossOrigin
public class activityController {

     @Autowired
    private ActivityService activityService;

    @ApiOperation(value = "获得活动详细页面")
    @GetMapping( "GetActivity")
    public Result GetActivity(@RequestParam(value = "id") int id){
        return Result.ok(activityService.quertActivity(id));
    }


    @ApiOperation(value = "获得官方进行中的活动集合")
    @GetMapping( "OfficialActivityList")
    public Result GetOfficialActivityList(){
        return Result.ok(activityService.queryOfficialActivityList());
    }

    @ApiOperation(value = "获得官方过期获得详细页面")
    @GetMapping( "EndOfficialActivityList")
    public Result GetEndOfficialActivityList(){
        return Result.ok(activityService.queryEndOfficialActivityList());
    }

    @ApiOperation(value = "获得个人进行中的活动集合")
    @GetMapping( "SelfActivityList")
    public Result GetSelfActivityList(){
        return Result.ok(activityService.querySelfActivityList());
    }

    @ApiOperation(value = "获得个人过期获得详细页面")
    @GetMapping( "EndSelfActivityList")
    public Result GetEndSelfActivityList(){
        return Result.ok(activityService.queryEndSelfActivityList());
    }

    @GetMapping("getActivityListByCondition")
    public Result getActivityListByCondition(@RequestParam(value = "page")int pagenum,
                                        @RequestParam("size") int size,
                                        HttpServletRequest httpServletRequest,
                                        @RequestParam("condition")  String condition
    ){
        return Result.ok(activityService.queryLikeActivity(condition));
    }


    @PostMapping("/addactivity")
    public Result add(@RequestBody Activity bActivity)
    {
        return Result.ok(activityService.insertBActivity(bActivity));
    }




}
