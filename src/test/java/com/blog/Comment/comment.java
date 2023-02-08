package com.blog.Comment;

import com.blog.BlueBlogApplication;
import com.blog.entity.Comment;
import com.blog.entity.Vo.CommentVo;
import com.blog.service.CommentService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = BlueBlogApplication.class)
public class comment {

    @Resource
    private CommentService commentService;
    @Test
    public void insertComment(){
        Comment comment = new Comment();
        comment.setParentId(5);
        comment.setBlogId(1);
        comment.setAccountId("7b5hiIEQ");
        comment.setContent("哈喽你也好");
        comment.setUserType(0);
        comment.setCreateTime(new Date());
        comment.setReplyUserId("nNEjUJzW");
        comment.setReplyUserName("8848");
        commentService.insertComment(comment);
    }
    @Test
    public void queryComment(){
        List<CommentVo> commentVoList = commentService.queryComment(1,0);
//        System.out.println(commentVoList.get(0));

        for(CommentVo commentVo : commentVoList){
            List<CommentVo> commentVoList2= commentService.queryComment(1,commentVo.getId());
            for ( CommentVo commentVo1 : commentVoList2){
                commentVo.addCommentVo(commentVo1);
            }
        }
       System.out.println(commentVoList.get(1));
    }
}
