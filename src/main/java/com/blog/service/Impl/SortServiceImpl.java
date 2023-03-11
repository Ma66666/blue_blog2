package com.blog.service.Impl;

import com.blog.entity.dao.SortMapper;
import com.blog.entity.Sort;
import com.blog.service.SortService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SortServiceImpl implements SortService {
    @Resource
    private SortMapper sortMapper;
    @Override
    public List<Sort> getSortList() {
        return sortMapper.getSortList();
    }
}
