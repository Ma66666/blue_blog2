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

    List<CommentVo> queryCommentList(@Param("blogId") int bolgId, @Param("parentId") int parentId);//查评论集合

    int queryCommentCount(int blogId); //查评论数

    int updataLike(int id); //like字段加1

    int cutLike(int id); //取消关注

    CommentVo queryComment(int commentId);//查询单挑评论

}
