package com.blog.service.Impl;

import com.blog.dao.CommentMapper;
import com.blog.entity.Comment;
import com.blog.entity.Vo.CommentVo;
import com.blog.service.CommentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Resource
    private CommentMapper commentMapper;

    @Override
    public int insertComment(Comment comment) {

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


}
