package com.blog.dao;

import com.blog.entity.Comment;
import com.blog.entity.Vo.CommentVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CommentMapper {
    int insertComment(Comment comment);//怎加评论

    int deleteComment(int id); //删除评论

    List<CommentVo> queryComment(@Param("blogId") int bolgId, @Param("parentId") int parentId);//查评论

}
