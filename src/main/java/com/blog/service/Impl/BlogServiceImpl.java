package com.blog.service.Impl;

import com.blog.dao.BlogMapper;
import com.blog.entity.Blog;
import com.blog.entity.Vo.BlogListVo;
import com.blog.entity.Vo.BlogVo;
import com.blog.service.BlogService;
import com.blog.util.GetSetRedis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class BlogServiceImpl implements BlogService {
    @Autowired
    private BlogMapper blogMapper;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;


    /**
     *
     * @param title 博客标题
     * @param content 博客内容
     * @param cover 博客封面
     * @param ImgList 博客图片礼盒
     * @param type 博客状态
     * @param accountId 博主ID
     * @param topic 博客话题
     * @return 1代表发送成功
     */
    @Override
    public int insertBlog(String title, String content, String cover, List<Object> ImgList, int type, String accountId,String topic) {
        Blog blog = new Blog();
        blog.setImageall(ImgList);
        blog.setAccountId(accountId);
        blog.setContent(content);
        blog.setCover(cover);
        blog.setHot(0);
        blog.setCreateTime(new Date());
        blog.setTitle(title);
        blog.setLikeCount(0);
        blog.setCollectCount(0);
        blog.setType(type);
        blog.setTopic(topic);
        return blogMapper.insertBlog(blog);
    }

    /**
     *  查询博客详细，并判断该用户是否点赞收藏
     * @param blogId 博客ID
     * @param accountId 用户ID
     * @return 返回博客实体
     */
    @Override
    public BlogVo QueryBlog(int blogId,String accountId) {
        BlogVo blogVo = blogMapper.queryById(blogId);
        //查看用户是否关注或点赞
        String entityLikeKey = GetSetRedis.getBlogLikeKey(""+blogId);
        String entityLikeKey1 =GetSetRedis.getBlogCollectKey(""+blogId);
        if (accountId=="空的"){
            System.out.println(false);
            blogVo.setLikeType(0);
        }else {
            //判断用户是否点赞
            boolean islike = redisTemplate.opsForSet().isMember(entityLikeKey, accountId);
            if (islike) {
                blogVo.setLikeType(1);

            } else {
                blogVo.setLikeType(0);
            }
            //判断用户是否收藏
            boolean islike2 = redisTemplate.opsForSet().isMember(entityLikeKey1,accountId);
            if (islike2){
                blogVo.setCollectType(1);
            }else {
                blogVo.setCollectType(0);
            }
        }
            blogVo.list.add(blogVo.getImage1());
        blogVo.list.add(blogVo.getImage2());
        blogVo.list.add(blogVo.getImage3());
        blogVo.list.add(blogVo.getImage4());
        blogVo.list.add(blogVo.getImage5());
        blogVo.list.add(blogVo.getImage6());
        blogVo.list.add(blogVo.getImage7());
        blogVo.list.add(blogVo.getImage8());
        blogVo.list.add(blogVo.getImage9());
        blogVo.list.removeAll(Collections.singleton(null));
        return blogVo;
    }

    //查询博客集合
    @Override
    public List<BlogListVo> QueryBlogList() {

        return blogMapper.queryByList();
    }

    /**
     * 查询用户博客集合
     * @param accountId 博主ID
     * @return 用户博客集合
     */
    @Override
    public List<BlogListVo> QueryUserBlogList(String accountId) {
        return blogMapper.queryUserBlogList(accountId);
    }

    /**
     * 查询 用户保存的草稿
     * @param accountId 用户ID
     * @return 返回草稿集合
     */
    @Override
    public List<BlogListVo> queryByListCg(String accountId) {
        return blogMapper.queryByListCg(accountId);
    }

    /**
     * 删除博客ID
     * @param blogId 博客ID
     * @return
     */
    @Override
    public int deleteBlog(int blogId) {
        return blogMapper.deleteBlog(blogId);
    }

    /**
     * 保存博客或者发送博客，主要用type判断
     * @param title
     * @param content
     * @param cover
     * @param ImgList
     * @param type
     * @param accountId
     * @param topic
     * @param id
     * @return
     */
    @Override
    public int saveCg(String title, String content, String cover, List<Object> ImgList, int type, String accountId, String topic, int id) {
        Blog blog = new Blog();
        blog.setImageall(ImgList);
        blog.setAccountId(accountId);
        blog.setContent(content);
        blog.setCover(cover);
        blog.setHot(0);
        blog.setCreateTime(new Date());
        blog.setTitle(title);
        blog.setLikeCount(0);
        blog.setCollectCount(0);
        blog.setType(type);
        blog.setTopic(topic);
        blog.setId(id);
        return blogMapper.saveCg(blog);

    }
}
