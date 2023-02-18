package com.blog.service;


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



//    //点赞方法
//    void like(int userId,int entityType,int entityId);
//
//    //查询实体点赞数量
//    public long findEntityLikeCount(int entityType,int entityId);
//
//    //查询用户都某个实体的点赞状态
//    public int findEntityLikeStatus(int userId,int entityType,int entityId);


}
