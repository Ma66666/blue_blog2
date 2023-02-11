package com.blog.service;

import com.blog.entity.Comment;
import com.blog.entity.Dto.CommentDto;
import com.blog.entity.Vo.CommentVo;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface CommentService {
    int insertComment(CommentDto commentDto, String accountId); //插入评论

    List<CommentVo> queryComment(int blogId,int parentId); //查评论

    int queryCommentCount(int blogId);
}
