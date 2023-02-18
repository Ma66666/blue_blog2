package com.blog.service.Impl;

import com.blog.dao.TopicMapper;
import com.blog.entity.Topic;
import com.blog.service.TopicService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TopicServiceImpl implements TopicService {

    @Resource
    private TopicMapper topicMapper;
    @Override
    public List<Topic> getTopicList() {
        return topicMapper.getTopicList();
    }
}
