package com.blog.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;
import java.util.List;



//博客实体
@Document(indexName = "blog",type = "_blog",shards = 6,replicas = 3)
@Data
public class Blog {


    @Id
    @ApiModelProperty(value = "博客ID")
    private int id;

    @Field(type = FieldType.Text)
    @ApiModelProperty(value = "账户编号ID")
    private String accountId;


    //分词器解析器
    //分析一串字符串由多少词语组成 analyzer searchAnalyzer
    @Field(type = FieldType.Text,analyzer = "ik_max_word",searchAnalyzer = "ik_smart")
    @ApiModelProperty(value = "博客标题")
    private String title;

    //分词器解析器
    //分析一串字符串由多少词语组成 analyzer searchAnalyzer
    @Field(type = FieldType.Text,analyzer = "ik_max_word",searchAnalyzer = "ik_smart")
    @ApiModelProperty(value = "博客内容")
    private String content;

    @Field(type = FieldType.Integer)
    @ApiModelProperty(value = "博客热度")
    private int hot;

    @Field(type = FieldType.Integer)
    @ApiModelProperty(value = "博客状态 0：草稿 1：正常 2：置顶 3删除")
    private int type;

    @Field(type = FieldType.Date)
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @Field(type = FieldType.Date)
    @ApiModelProperty(value = "修改时间")
    private Date updateTime;

    @Field(type = FieldType.Text)
    @ApiModelProperty(value = "话题")
    private String topic;

    @Field(type = FieldType.Text)
    @ApiModelProperty(value = "标签")
    private String tag;

    @Field(type = FieldType.Text)
    @ApiModelProperty(value = "封面")
    private String cover;

    @Field(type = FieldType.Integer)
    @ApiModelProperty(value = "点赞总数")
    private int likeCount;

    @Field(type = FieldType.Integer)
    @ApiModelProperty(value = "收藏总数")
    private int collectCount;

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
