package com.blog.controller;

import com.blog.service.TopicService;
import com.blog.util.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api(tags="话题相关")
@RestController
@RequestMapping("api/Topic")
@CrossOrigin
public class TopicController {
    @Resource
    private TopicService topicService;

    @ApiOperation("获得话题集合")
    @GetMapping("getTopicList")
    private Result getTopicList(){
        return Result.ok(topicService.getTopicList());
    }
}
