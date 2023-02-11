package com.blog.service;


public interface LikeService {

    //点赞方法
    void like(int userId,int entityType,int entityId);
}
