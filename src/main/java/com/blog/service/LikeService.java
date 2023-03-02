package com.blog.service;


import com.blog.entity.Vo.LikeVo;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface LikeService {

    void Bloglike(int blogId,String accountId);

    void CommentLike(int id,String accountId);

    void BlogCollect(int blogId,String accountId);

    /**
     *  执行关注操作
     * @param accountId 用户ID
     * @param BeLikeAccountId 被用户关注ID
     */
    void follow(String accountId,String BeLikeAccountId);

    /**
     *  执行取关操作
     * @param accountId 用户ID
     * @param BeLikeAccountId 被用户取关的ID
     */
    void unfollow(String accountId,String BeLikeAccountId);

    /**
     *  判断用户是关注
     * @param accountId 用户ID
     * @param BeLikeAccountId
     * @return 返回关注状态 0：没关注 1：已关注 2：被拉黑
     */
    int hasLike(String accountId,String BeLikeAccountId);

    /**
     * 获得用户关注列表
     * @param request 请求
     * @param accountId 目标用户ID
     * @return
     */

    Map<String,Object> UserLikeList(HttpServletRequest request,String accountId);

    Map<String,Integer> getCount(String accountId);

    /**
     * 获得用户粉丝列表
     * @param request 用来获得token中的accountId
     * @param accountId 目标用户ID
     * @return
     */
    Map<String,Object> LikeUserList(HttpServletRequest request,String accountId);


//    //点赞方法
//    void like(int userId,int entityType,int entityId);
//
//    //查询实体点赞数量
//    public long findEntityLikeCount(int entityType,int entityId);
//
//    //查询用户都某个实体的点赞状态
//    public int findEntityLikeStatus(int userId,int entityType,int entityId);


}
