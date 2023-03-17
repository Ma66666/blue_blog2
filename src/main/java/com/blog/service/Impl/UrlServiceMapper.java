package com.blog.service.Impl;

import com.blog.dao.UrlMapper;
import com.blog.entity.Url;
import com.blog.service.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UrlServiceMapper implements UrlService {
    @Autowired
    private UrlMapper urlMapper;
    @Override
    public List<Url> QueryUrl() {
        return urlMapper.getUrl();
    }

    @Override
    public List<Url> querySearchList() {
        return urlMapper.getSearchUrl();
    }

    @Override
    public List<Url> queryMessageList() {
        return urlMapper.getMessageUrl();
    }
}
