package com.blog.service.Impl;

import com.blog.dao.CommentMapper;
import com.blog.entity.Comment;
import com.blog.entity.Dto.CommentDto;
import com.blog.entity.Vo.CommentVo;
import com.blog.service.CommentService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Resource
    private CommentMapper commentMapper;

    @Override
    public int insertComment(CommentDto commentDto,String accountId) {
        Comment comment = new Comment();
        BeanUtils.copyProperties(commentDto,comment);
        comment.setAccountId(accountId);
        comment.setCreateTime(new Date());
        comment.setUserType(0);
        return commentMapper.insertComment(comment);
    }

    @Override
    public List<CommentVo> queryComment(int blogId,int parentId) {
        List<CommentVo> commentVoList = commentMapper.queryComment(blogId,0);
        for (CommentVo commentVo : commentVoList){
            List<CommentVo> commentVoList2= commentMapper.queryComment(blogId,commentVo.getId());
            for ( CommentVo commentVo1 : commentVoList2){
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
