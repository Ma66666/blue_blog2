package com.blog.dao;

import com.blog.entity.Blog;
import com.blog.entity.Vo.BlogListVo;
import com.blog.entity.Vo.BlogVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BlogMapper {
    BlogVo queryById(int id); //根据id查询帖子信息

    int insertBlog(Blog blog); //添加博客

    List<BlogListVo> queryByList();//查询所有博客封面

    int updataLike(int blogId); //like字段加1

    int cutLike(int blogId);//like字段减1

    int updataCollectLike(int blogId);//收藏数加1

    int cutCollectLike(int blogId);//收藏数减1

    List<BlogListVo> queryByListCg(String accountId);//查询所有草稿文件

    int deleteBlog(int blogId); //逻辑删除博客

    int saveCg(Blog blog); //保存发送草稿

    List<BlogListVo> queryUserBlogList(String accountId); //查找目标用户博客信息

    //查询发布这个博客的人的Id
    String queryUserAccountId(int blogId);






}
