package com.blog.service.Impl;

import com.blog.dao.BlogMapper;
import com.blog.dao.CommentMapper;
import com.blog.service.LikeService;
import com.blog.util.GetSetRedis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.stereotype.Service;

@Service
public class LikeServiceImpl implements LikeService {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Autowired
    private BlogMapper blogMapper;

    @Autowired
    private CommentMapper commentMapper;
    @Override
    public void Bloglike(int blogId, String accountId) {
        String entityLikeKey = GetSetRedis.getBlogLikeKey(""+blogId);
        boolean islike  =  redisTemplate.opsForSet().isMember(entityLikeKey,accountId);
        if (islike) {
           redisTemplate.opsForSet().remove(entityLikeKey,accountId);
           blogMapper.cutLike(blogId);
        }else {
            redisTemplate.opsForSet().add(entityLikeKey,accountId);
            blogMapper.updataLike(blogId);
        }
    }

    @Override
    public void CommentLike(int id, String accountId) {
        String entityLikeKey = GetSetRedis.getCommentLikeKey(""+id);
        boolean islike  =  redisTemplate.opsForSet().isMember(entityLikeKey,accountId);
        if (islike) {
            redisTemplate.opsForSet().remove(entityLikeKey,accountId);
            commentMapper.cutLike(id);
        }else {
            redisTemplate.opsForSet().add(entityLikeKey,accountId);
            commentMapper.updataLike(id);
        }
    }

    @Override
    public void BlogCollect(int blogId, String accountId) {
        String entityLikeKey = GetSetRedis.getBlogCollectKey(""+blogId);
        boolean islike  =  redisTemplate.opsForSet().isMember(entityLikeKey,accountId);
        if (islike) {
            redisTemplate.opsForSet().remove(entityLikeKey,accountId);
            blogMapper.cutCollectLike(blogId);
        }else {
            redisTemplate.opsForSet().add(entityLikeKey,accountId);
            blogMapper.updataCollectLike(blogId);

        }
    }

    @Override
    public void follow(String accountId, String BeLikeAccountId) {
        redisTemplate.execute(new SessionCallback(){
            @Override
            public Object execute(RedisOperations operations) throws DataAccessException {
                String userLike = GetSetRedis.getUserLike(accountId);
                String likeUser = GetSetRedis.getLikeUser(BeLikeAccountId);

                operations.multi();
                operations.opsForZSet().add(userLike, BeLikeAccountId, System.currentTimeMillis());
                operations.opsForZSet().add(likeUser, accountId, System.currentTimeMillis());

                return operations.exec();
            }
        });

    }

    @Override
    public void unfollow(String accountId, String BeLikeAccountId) {
        redisTemplate.execute(new SessionCallback(){
            @Override
            public Object execute(RedisOperations operations) throws DataAccessException {
                String userLike = GetSetRedis.getUserLike(accountId);
                String likeUser = GetSetRedis.getLikeUser(BeLikeAccountId);

                operations.multi();
                operations.opsForZSet().remove(userLike, BeLikeAccountId);
                operations.opsForZSet().remove(likeUser, accountId);

                return operations.exec();
            }
        });
    }

    @Override
    public int hasLike(String accountId, String BeLikeAccountId) {
        String userLike = GetSetRedis.getUserLike(accountId);
        if(redisTemplate.opsForZSet().score(userLike,BeLikeAccountId)!=null){
            return 1;
        }
        return 0;
    }


//    @Override
//    public void like(int userId, int entityType, int entityId) {
//        String entityLikeKey = GetSetRedis.getEntityLikeKey(entityType,entityId);
//        boolean islike  =  redisTemplate.opsForSet().isMember(entityLikeKey,userId);
//        if (islike) {
//           redisTemplate.opsForSet().remove(entityLikeKey,userId);
//        }else {
//            redisTemplate.opsForSet().add(entityLikeKey,userId);
//        }
//
//    }
//
//    @Override
//    public long findEntityLikeCount(int entityType, int entityId) {
//        String entityLikeKey = GetSetRedis.getEntityLikeKey(entityType,entityId);
//        return redisTemplate.opsForSet().size(entityLikeKey);
//    }
//
//    @Override
//    public int findEntityLikeStatus(int userId, int entityType, int entityId) {
//        String entityLikeKey = GetSetRedis.getEntityLikeKey(entityType,entityId);
//        return redisTemplate.opsForSet().isMember(entityLikeKey,userId)?1:0;
//    }


}
