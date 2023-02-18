package com.blog.service.Impl;

import com.blog.dao.ListMapper;
import com.blog.service.ListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ListServiceImpl implements ListService {
    @Autowired
    private ListMapper listMapper;
}
