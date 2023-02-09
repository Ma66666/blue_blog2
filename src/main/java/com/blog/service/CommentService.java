package com.blog.service;

import com.blog.entity.Comment;
import com.blog.entity.Vo.CommentVo;

import java.util.List;

public interface CommentService {
    int insertComment(Comment comment); //插入评论

    List<CommentVo> queryComment(int blogId,int parentId); //查评论

    int queryCommentCount(int blogId);
}
