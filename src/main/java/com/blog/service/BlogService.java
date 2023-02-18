package com.blog.service;


import com.blog.entity.Vo.BlogListVo;
import com.blog.entity.Vo.BlogVo;


import java.util.List;

public interface BlogService {

    int insertBlog(String title, String content, String cover, List<Object> ImgList, int type,String accountId,String topic); //插入博客

    BlogVo QueryBlog(int blogId,String accountId);//查询博客

    List<BlogListVo> QueryBlogList(); //查询获取博客集合

    List<BlogListVo> QueryUserBlogList(String accountId); //查询获取博客集合

    List<BlogListVo> queryByListCg(String accountId); //查询用户所有草稿

    int deleteBlog(int blogId); //删除博客

    int saveCg(String title, String content, String cover, List<Object> ImgList, int type,String accountId,String topic,int id); //保存的草稿看是否继续保存还是直接发送

}
