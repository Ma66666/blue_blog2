package com.blog.service;

import com.blog.entity.Url;

import java.util.List;

public interface UrlService {
    List<Url> QueryUrl();

    List<Url> querySearchList();

    List<Url> queryMessageList();

    List<Url> queryActivityList();
}
