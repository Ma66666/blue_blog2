package com.blog.service.Impl;

import com.blog.dao.BlogMapper;
import com.blog.dao.ChatMessageMapper;
import com.blog.dao.CommentMapper;
import com.blog.entity.ChatMessage;
import com.blog.entity.Comment;
import com.blog.entity.Dto.CommentDto;
import com.blog.entity.Vo.CommentVo;
import com.blog.service.CommentService;
import com.blog.util.GetSetRedis;
import com.blog.util.SensitiveFilter;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Resource
    private CommentMapper commentMapper;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Autowired
    private BlogMapper blogMapper;

    @Autowired
    private ChatMessageMapper chatMessageMapper;

    @Autowired
    private SensitiveFilter sensitiveFilter;

    /**
     * 用户发送评论
     * @param commentDto 评论内容实体
     * @param accountId 用户ID
     * @return
     */
    @Override
    public int insertComment(CommentDto commentDto,String accountId) {
        Comment comment = new Comment();
        BeanUtils.copyProperties(commentDto,comment);
        System.out.println(accountId);
        comment.setAccountId(accountId);
        comment.setCreateTime(new Date());
        comment.setUserType(0);
        comment.setLikeCount(0);
      int i =  Integer.parseInt(String.valueOf(sensitiveFilter.filter(commentDto.getContent()).get("num")));
      comment.setContent(sensitiveFilter.filter(commentDto.getContent()).get("String")+"");
        if (i>5){
        comment.setStatus(1);
        }

        if (commentDto.getParentId()!=0) {
            CommentVo commentVo = commentMapper.queryComment(commentDto.getParentId());
            System.out.println("我执行了");
            ChatMessage chatMessage = new ChatMessage();
            chatMessage.setMessage("评论了您的评论:" + comment.getContent());
            chatMessage.setSendAccountId(accountId);
            chatMessage.setToAccountId(commentVo.getAccountId());
            chatMessage.setUser1User2("1");
            chatMessage.setCreateTime(new Date());
            chatMessage.setType(2);
            chatMessage.setBlogId(comment.getBlogId());
            chatMessage.setCommentId(comment.getParentId());
            chatMessageMapper.insertNotice(chatMessage);
        }else {
            ChatMessage chatMessage = new ChatMessage();
            chatMessage.setMessage("评论了您的博客:" + comment.getContent());
            chatMessage.setSendAccountId(accountId);
            chatMessage.setToAccountId(blogMapper.queryById(commentDto.getBlogId()).getAccountId());
            chatMessage.setUser1User2("1");
            chatMessage.setCreateTime(new Date());
            chatMessage.setType(2);
            chatMessage.setBlogId(comment.getBlogId());
            chatMessageMapper.insertNotice(chatMessage);
        }
            return commentMapper.insertComment(comment);

    }

    /**
     *
     * @param blogId 博客ID
     * @param parentId 父ID (0:一级评论；其它为一级评论的ID)
     * @param accountId 用户ID
     * @return 用户集合
     */
    @Override
    public List<CommentVo> queryComment(int blogId,int parentId,String accountId) {

        //先获得父评论ID集合
        List<CommentVo> commentVoList = commentMapper.queryCommentList(blogId,0);
        for (CommentVo commentVo : commentVoList){
            String entityLikeKey = GetSetRedis.getCommentLikeKey(""+commentVo.getId());
            //判断用户是否点赞了评论
            boolean islike  =  redisTemplate.opsForSet().isMember(entityLikeKey,accountId);
            if (islike){
                commentVo.setLikeType(1);

            }else {
                commentVo.setLikeType(0);

            }
            //获得子评论ID
            List<CommentVo> commentVoList2= commentMapper.queryCommentList(blogId,commentVo.getId());
            for ( CommentVo commentVo1 : commentVoList2){
                String entityLikeKey1 = GetSetRedis.getCommentLikeKey(""+commentVo1.getId());
                //判断用户是否点赞
                boolean islike1  =  redisTemplate.opsForSet().isMember(entityLikeKey1,accountId);
                if (islike1){
                    commentVo1.setLikeType(1);
                }else {
                    commentVo1.setLikeType(0);
                }
                //往评论实体添加子评论
                commentVo.addCommentVo(commentVo1);
            }
        }
        return commentVoList;
    }

    /**
     * 查询评论总数
     * @param blogId 博客ID
     * @return
     */
    @Override
    public int queryCommentCount(int blogId) {
        return commentMapper.queryCommentCount(blogId);
    }


}
