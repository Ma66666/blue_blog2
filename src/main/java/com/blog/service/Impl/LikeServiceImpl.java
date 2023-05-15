package com.blog.service.Impl;

import com.blog.dao.BlogMapper;
import com.blog.dao.ChatMessageMapper;
import com.blog.dao.CommentMapper;
import com.blog.dao.LikeMapper;
import com.blog.dao.es.EsVo;
import com.blog.entity.ChatMessage;
import com.blog.entity.Vo.LikeVo;
import com.blog.service.LikeService;
import com.blog.util.GetSetRedis;
import com.blog.util.GetTokenAccountId;
import org.elasticsearch.action.update.UpdateRequest;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.UpdateQuery;
import org.springframework.data.elasticsearch.core.query.UpdateQueryBuilder;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
public class LikeServiceImpl implements LikeService {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Autowired
    private BlogMapper blogMapper;

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private GetTokenAccountId getTokenAccountId;

    @Autowired
    private LikeMapper likeMapper;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private ChatMessageMapper chatMessageMapper;


    @Autowired
    private ElasticsearchTemplate elasticTemplate;

    /**
     * 用户点赞博客，不用返回值
     * @param blogId 博客Id
     * @param accountId 用户ID
     */
    @Override
    public void Bloglike(int blogId, String accountId) {
        String entityLikeKey = GetSetRedis.getBlogLikeKey(""+blogId);
        //判断用户之前是否点赞了博客
        boolean islike  =  redisTemplate.opsForSet().isMember(entityLikeKey,accountId);
        //如果是
        if (islike) {
            //删除该记录
           redisTemplate.opsForSet().remove(entityLikeKey,accountId);
           //博客点赞数减去1
           blogMapper.cutLike(blogId);
            rabbitTemplate.convertAndSend("Blue.Topic", "BlogUnLike", blogId);
        }else {
            //添加该记录
            redisTemplate.opsForSet().add(entityLikeKey,accountId);
            //博客点赞数加1
            blogMapper.updataLike(blogId);
            rabbitTemplate.convertAndSend("Blue.Topic", "BlogLike", blogId);
            ChatMessage chatMessage = new ChatMessage();
            chatMessage.setMessage("点赞了您的博客:”"+blogMapper.queryById(blogId).getTitle()+"”");
            chatMessage.setSendAccountId(accountId);
            chatMessage.setToAccountId(blogMapper.queryUserAccountId(blogId));
            chatMessage.setCreateTime(new Date());
            chatMessage.setType(1);
            chatMessage.setBlogId(blogId);
            chatMessage.setUser1User2("1");
            chatMessageMapper.insertNotice(chatMessage);
        }
    }

    /**
     * 用户点赞评论不用返回值
     * @param id  评论ID
     * @param accountId 用户ID
     */
    @Override
    public void CommentLike(int id,int blogId, String accountId) {
        String entityLikeKey = GetSetRedis.getCommentLikeKey(""+id);
        boolean islike  =  redisTemplate.opsForSet().isMember(entityLikeKey,accountId);
        if (islike) {
            redisTemplate.opsForSet().remove(entityLikeKey,accountId);
            commentMapper.cutLike(id);
        }else {
            redisTemplate.opsForSet().add(entityLikeKey,accountId);
            commentMapper.updataLike(id);
            ChatMessage chatMessage = new ChatMessage();
            chatMessage.setMessage("点赞了您的评论:”"+commentMapper.queryComment(id).getContent()+"”");
            chatMessage.setSendAccountId(accountId);
            chatMessage.setToAccountId(commentMapper.queryComment(id).getAccountId());
            chatMessage.setCreateTime(new Date());
            chatMessage.setType(1);
            chatMessage.setBlogId(blogId);
            chatMessage.setCommentId(id);
            chatMessage.setUser1User2("1");
            chatMessageMapper.insertNotice(chatMessage);
        }
    }

    /**
     * 用户收藏博客，逻辑与点赞相识
     * @param blogId 博客ID
     * @param accountId 用户ID
     */
    @Override
    public void BlogCollect(int blogId, String accountId) {
        String entityLikeKey = GetSetRedis.getBlogCollectKey(""+blogId);
        boolean islike  =  redisTemplate.opsForSet().isMember(entityLikeKey,accountId);
        if (islike) {
            redisTemplate.opsForSet().remove(entityLikeKey,accountId);
            blogMapper.cutCollectLike(blogId);
        }else {
            redisTemplate.opsForSet().add(entityLikeKey,accountId);
            blogMapper.updataCollectLike(blogId);
            ChatMessage chatMessage = new ChatMessage();
            chatMessage.setMessage("收藏了您的博客:”"+blogMapper.queryById(blogId).getTitle()+"”");
            chatMessage.setSendAccountId(accountId);
            chatMessage.setToAccountId(blogMapper.queryUserAccountId(blogId));
            chatMessage.setCreateTime(new Date());
            chatMessage.setType(1);
            chatMessage.setBlogId(blogId);
            chatMessage.setUser1User2("1");
            chatMessageMapper.insertNotice(chatMessage);

        }
    }

    /**
     * 用户关注
     * @param accountId 用户ID
     * @param BeLikeAccountId 被用户关注ID
     */
    @Override
    public void follow(String accountId, String BeLikeAccountId) {
        redisTemplate.execute(new SessionCallback(){
            @Override
            public Object execute(RedisOperations operations) throws DataAccessException {
                String userLike = GetSetRedis.getUserLike(accountId);
                String likeUser = GetSetRedis.getLikeUser(BeLikeAccountId);

                operations.multi();
                operations.opsForSet().add(userLike, BeLikeAccountId);
                operations.opsForSet().add(likeUser, accountId);

                return operations.exec();
            }
        });
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setMessage("关注了您");
        chatMessage.setSendAccountId(accountId);
        chatMessage.setToAccountId(BeLikeAccountId);
        chatMessage.setCreateTime(new Date());
        chatMessage.setUser1User2("1");
        chatMessage.setType(3);
        chatMessage.setUser1User2("1");
        chatMessageMapper.insertNotice(chatMessage);

    }

    /**
     * 用户取关
     * @param accountId 用户ID
     * @param BeLikeAccountId 被用户取关的ID
     */
    @Override
    public void unfollow(String accountId, String BeLikeAccountId) {
        redisTemplate.execute(new SessionCallback(){
            @Override
            public Object execute(RedisOperations operations) throws DataAccessException {
                String userLike = GetSetRedis.getUserLike(accountId);
                String likeUser = GetSetRedis.getLikeUser(BeLikeAccountId);

                operations.multi();
                operations.opsForSet().remove(userLike, BeLikeAccountId);
                operations.opsForSet().remove(likeUser, accountId);

                return operations.exec();
            }
        });
    }

    /**
     * 判断用户是否关注了另一个用户
     * @param accountId 用户ID
     * @param BeLikeAccountId 被关注ID
     * @return 1为是 0为否
     */
    @Override
    public int hasLike(String accountId, String BeLikeAccountId) {
        String userLike = GetSetRedis.getUserLike(accountId);
        if(redisTemplate.opsForSet().isMember(userLike,BeLikeAccountId)){
            return 1;
        }
        return 0;
    }

    /**
     * 查看用户关注列表
     * @param request 请求，用来解析发起请求的用户ID
     * @param accountId 目标用户ID
     * @return
     */
    @Override
    public Map<String,Object> UserLikeList(HttpServletRequest request,String accountId) {
        //获得发起请求的yonghuID
        String meaccountId = getTokenAccountId.getTokenAccountId(request);
        List<LikeVo> list  = new ArrayList<>();
        //获得redis的key
        String user = GetSetRedis.getUserLike(accountId);
        //获得redis里用户的关注集合
        Set<String> set =redisTemplate.opsForSet().members(user);
        //实例化迭代器
        Iterator<String> iterator  = set.iterator();
        //判断发起请求的用户是否和被查看的用户是同一个，如果是
        if (meaccountId == accountId){
            while (iterator.hasNext()){
                //获得目标用户的用户信息
                LikeVo likeVo = likeMapper.getUserLikeVo(iterator.next());
                //判断目标信息的ID是否和发起请求的ID相同
                if (likeVo.getAccountId().equals(meaccountId)){
                    //如果又相同，判断值为3，前端不会显示按钮
                    likeVo.setLiketype(3);
                }else {
                    //如果不是，那就是自己关注的ID，值全为1，前端显示已关注
                    likeVo.setLiketype(1);
                }
                list.add(likeVo);
            }
        }else { //如果发起请求的用户和被查询的用户不是同一个人
            //求两个集合的交集交集
            String user1 = GetSetRedis.getUserLike(meaccountId);
            Set<String> set1 = redisTemplate.opsForSet().intersect(user1,user);

            Iterator<String> iterator1  = set1.iterator();
            while (iterator1.hasNext()){
                LikeVo likeVo = likeMapper.getUserLikeVo(iterator1.next());
                //判断被查询的用户里边有没有发起请求的人
                if (likeVo.getAccountId().equals(meaccountId)){
                    //如果有，判断值为3
                    likeVo.setLiketype(3);
                }else {
                    //如果没有，再判断值为1
                    likeVo.setLiketype(1);
                }
                list.add(likeVo);
            }
            //求差集
            //两个集合的差集即是发起请求用户没关注的用户，值设为0
            Set<String> set2 = redisTemplate.opsForSet().difference(user,user1);
            Iterator<String> iterator2  = set2.iterator();
            while (iterator2.hasNext()){
                LikeVo likeVo = likeMapper.getUserLikeVo(iterator2.next());
                if (likeVo.getAccountId().equals(meaccountId)){
                    likeVo.setLiketype(3);
                }else {
                    likeVo.setLiketype(0);
                }
                list.add(likeVo);
            }
        }
        Map<String,Object> map =new HashMap<>();
        map.put("total",set.size());
        map.put("ListVo",list);
        return map;
    }

    /**
     * 获得关注、粉丝数
     * @param accountId 被查询用户ID
     * @return
     */
    @Override
    public Map<String, Integer> getCount(String accountId) {
        String userLike = GetSetRedis.getUserLike(accountId);
        String likeUser = GetSetRedis.getLikeUser(accountId);
        Map<String,Integer> map = new HashMap<>();
        map.put("userLike",redisTemplate.opsForSet().size(userLike).intValue()); //转Integer
        map.put("likeUser",redisTemplate.opsForSet().size(likeUser).intValue());
        return map;
    }

    /**
     * 查询用户粉丝列表
     * @param request 用来获得token中的accountId
     * @param accountId 目标用户ID
     * @return
     */
    @Override
    public Map<String, Object> LikeUserList(HttpServletRequest request, String accountId) {
        String meaccountId = getTokenAccountId.getTokenAccountId(request);
        List<LikeVo> list  = new ArrayList<>();
        String user = GetSetRedis.getLikeUser(accountId);
            String user1 = GetSetRedis.getUserLike(meaccountId);
            //求我关注的和他的粉丝的交集
            Set<String> set = redisTemplate.opsForSet().intersect(user1,user);
            Iterator<String> iterator  = set.iterator();
            while (iterator.hasNext()){
                LikeVo likeVo = likeMapper.getUserLikeVo(iterator.next());
                //判断对方的粉丝列表里边有没有自己，如果有设置为3
                if (likeVo.getAccountId().equals(meaccountId)){
                    likeVo.setLiketype(3);
                }else {
                    //如果不是自己，因为是我的关注和他的粉丝的交集，那么剩下的必定都是我的关注，状态值设置为1
                    likeVo.setLiketype(1);
                }

                list.add(likeVo);
            }
            //求差集，他的粉丝和我的关注的差集
            Set<String> set2 = redisTemplate.opsForSet().difference(user,user1);
            Iterator<String> iterator2  = set2.iterator();
            while (iterator2.hasNext()){
                LikeVo likeVo = likeMapper.getUserLikeVo(iterator2.next());
                //先获得差集的用户ID，判断该集合有没有我，如果有，设置为3
                if (likeVo.getAccountId().equals(meaccountId)){
                    likeVo.setLiketype(3);
                }else {
                    //如果没有， 那必定都是我没关注的用户
                    likeVo.setLiketype(0);
                }
                list.add(likeVo);


//            //求交集
//            String user1 = GetSetRedis.getLikeUser(meaccountId);
//            Set<String> set1 = redisTemplate.opsForSet().intersect(user1,user);
//            Iterator<String> iterator1  = set1.iterator();
//            while (iterator1.hasNext()){
//                LikeVo likeVo = likeMapper.getUserLikeVo(iterator1.next());
//                likeVo.setLiketype(1);
//                list.add(likeVo);
//            }
//            //求差集
//            Set<String> set2 = redisTemplate.opsForSet().difference(user,user1);
//            Iterator<String> iterator2  = set2.iterator();
//            while (iterator2.hasNext()){
//                LikeVo likeVo = likeMapper.getUserLikeVo(iterator2.next());
//                likeVo.setLiketype(0);
//                list.add(likeVo);
//            }
        }
        Map<String,Object> map =new HashMap<>();

        map.put("ListVo",list);
        return map;
    }


//    @Override
//    public void like(int userId, int entityType, int entityId) {
//        String entityLikeKey = GetSetRedis.getEntityLikeKey(entityType,entityId);
//        boolean islike  =  redisTemplate.opsForSet().isMember(entityLikeKey,userId);
//        if (islike) {
//           redisTemplate.opsForSet().remove(entityLikeKey,userId);
//        }else {
//            redisTemplate.opsForSet().add(entityLikeKey,userId);
//        }
//
//    }
//
//    @Override
//    public long findEntityLikeCount(int entityType, int entityId) {
//        String entityLikeKey = GetSetRedis.getEntityLikeKey(entityType,entityId);
//        return redisTemplate.opsForSet().size(entityLikeKey);
//    }
//
//    @Override
//    public int findEntityLikeStatus(int userId, int entityType, int entityId) {
//        String entityLikeKey = GetSetRedis.getEntityLikeKey(entityType,entityId);
//        return redisTemplate.opsForSet().isMember(entityLikeKey,userId)?1:0;
//    }


}
