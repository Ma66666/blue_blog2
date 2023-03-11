package com.blog.service;


import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface EsService {
   /**
    *
    * @param pagenum 页
    * @param pagesize 每页条数
    * @param httpServletRequest 请求
    * @return
    */
   Map<String,Object> queryEsBlogList(int pagenum, int pagesize, HttpServletRequest httpServletRequest);

   /**
    *
    * @param pagenum 页
    * @param pagesize 每页条数
    * @param httpServletRequest 请求
    * @return
    */
   Map<String,Object> quertEsBlogListByDate(int pagenum,int pagesize,HttpServletRequest httpServletRequest);

   /**
    * 查询我的/关注/收藏/喜欢的博客
    * @param pagenum 页
    * @param pagesize 每页条数
    * @param httpServletRequest 请求
    * @param selectType 查询条件 1：我的关注 2：我的收藏  3：我的喜欢
    * @return
    */
   Map<String,Object> queryEsBlogListByMyLikeBlog(int pagenum,int pagesize,HttpServletRequest httpServletRequest,int selectType);


   /**
    *查询我关注博主的博客
    * @param pagenum 页
    * @param pagesize 每页条数
    * @param httpServletRequest 请求
    * @param selectType 查询条件 1：我的关注 2：我的收藏  3：我的喜欢
    * @return
    */
   Map<String,Object> queryEsBlogListByUserLike(int pagenum,int pagesize,HttpServletRequest httpServletRequest,int selectType);


   /**
    * 根据词条查询博客
    * @param pagenum 页
    * @param pagesize 每页条数
    * @param httpServletRequest 请求
    * @param searchData 查询条件
    * @return
    */
   Map<String,Object> queryEsBlogListByCondition(int pagenum,int pagesize,HttpServletRequest httpServletRequest,String searchData);

}
