package com.blog.service.Impl;

import com.blog.dao.BlogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BlogServiceImpl {
    @Autowired
    private BlogMapper blogMapper;
}
