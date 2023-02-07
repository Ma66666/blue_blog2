package com.blog.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class Blog {


    @ApiModelProperty(value = "博客ID")
    private int id;

    @ApiModelProperty(value = "账户编号ID")
    private String accountId;

    @ApiModelProperty(value = "博客标题")
    private String title;

    @ApiModelProperty(value = "博客内容")
    private String content;

    @ApiModelProperty(value = "博客热度")
    private int hot;

    @ApiModelProperty(value = "博客状态 0：草稿 1：正常 2：置顶 3删除")
    private int type;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    private Date updateTime;

    @ApiModelProperty(value = "话题")
    private String topic;

    @ApiModelProperty(value = "标签")
    private String tag;

    @ApiModelProperty(value = "封面")
    private String cover;

    @ApiModelProperty(value = "图片1")
    private String image1;

    @ApiModelProperty(value = "图片2")
    private String image2;

    @ApiModelProperty(value = "图片3")
    private String image3;

    @ApiModelProperty(value = "图片4")
    private String image4;

    @ApiModelProperty(value = "图片5")
    private String image5;

    @ApiModelProperty(value = "图片6")
    private String image6;

    @ApiModelProperty(value = "图片7")
    private String image7;

    @ApiModelProperty(value = "图片8")
    private String image8;

    @ApiModelProperty(value = "图片9")
    private String image9;



   //遍历集合给图片赋值
    public void setImageall(List<Object> list){
        int i = list.size();
        int j = 0;
        switch (0){
            case 0:this.image1 =(String)list.get(j);
            j++;
            if (i<=j) break;
            case 1:this.image2 =(String)list.get(j);
                j++;
                if (i<=j) break;
            case 2:this.image3 = (String)list.get(j);
                j++;
                if (i<=j) break;
            case 3:this.image4 = (String)list.get(j);
                j++;
                if (i<=j) break;
            case 4:this.image5 = (String)list.get(j);
                j++;
                if (i<=j) break;
            case 5:this.image6 = (String)list.get(j);
                j++;
                if (i<=j) break;
            case 6:this.image7= (String)list.get(j);
                j++;
                if (i<=j) break;
            case 7:this.image8 = (String)list.get(j);
                j++;
                if (i<=j) break;
            case 8:this.image9 = (String)list.get(j);
                j++;
                if (i<=j) break;
        }

    }






}
