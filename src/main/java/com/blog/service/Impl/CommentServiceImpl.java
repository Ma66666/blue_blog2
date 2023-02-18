package com.blog.service.Impl;

import com.blog.dao.CommentMapper;
import com.blog.entity.Comment;
import com.blog.entity.Dto.CommentDto;
import com.blog.entity.Vo.CommentVo;
import com.blog.service.CommentService;
import com.blog.util.GetSetRedis;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Resource
    private CommentMapper commentMapper;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Override
    public int insertComment(CommentDto commentDto,String accountId) {
        Comment comment = new Comment();
        BeanUtils.copyProperties(commentDto,comment);
        System.out.println(accountId);
        comment.setAccountId(accountId);
        comment.setCreateTime(new Date());
        comment.setUserType(0);
        comment.setLikeCount(0);
        return commentMapper.insertComment(comment);
    }

    @Override
    public List<CommentVo> queryComment(int blogId,int parentId,String accountId) {

        List<CommentVo> commentVoList = commentMapper.queryComment(blogId,0);
        for (CommentVo commentVo : commentVoList){
            String entityLikeKey = GetSetRedis.getCommentLikeKey(""+commentVo.getId());
            boolean islike  =  redisTemplate.opsForSet().isMember(entityLikeKey,accountId);
            if (islike){
                commentVo.setLikeType(1);

            }else {
                commentVo.setLikeType(0);

            }
            List<CommentVo> commentVoList2= commentMapper.queryComment(blogId,commentVo.getId());
            for ( CommentVo commentVo1 : commentVoList2){
                String entityLikeKey1 = GetSetRedis.getCommentLikeKey(""+commentVo1.getId());
                boolean islike1  =  redisTemplate.opsForSet().isMember(entityLikeKey1,accountId);
                if (islike1){
                    commentVo1.setLikeType(1);
                }else {
                    commentVo1.setLikeType(0);
                }
                commentVo.addCommentVo(commentVo1);
            }
        }
        return commentVoList;
    }

    @Override
    public int queryCommentCount(int blogId) {
        return commentMapper.queryCommentCount(blogId);
    }


}
