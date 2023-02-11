package com.blog.service.Impl;

import com.blog.service.LikeService;
import com.blog.util.GetSetRedis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class LikeServiceImpl implements LikeService {

    @Autowired
    private RedisTemplate redisTemplate;


    @Override
    public void like(int userId, int entityType, int entityId) {
        String entityLikeKey = GetSetRedis.getEntityLikeKey(entityType,entityId);
        boolean islike  =  redisTemplate.opsForSet().isMember(entityLikeKey,userId);
        if (islike) {
           redisTemplate.opsForSet().remove(entityLikeKey,userId);
        }else {
            redisTemplate.opsForSet().add(entityLikeKey,userId);
        }

    }
}
