package com.blog.dao.es;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


//博客实体
//indexName只能小写，type只能_doc
@Document(indexName = "blogvo",type = "_doc",shards = 6,replicas = 3)
@Data
public class EsVo {


    @Id
    @ApiModelProperty(value = "博客ID")
    private int id;

    @Field(type = FieldType.Text)
    @ApiModelProperty(value = "账户编号ID")
    private String accountId;

    @Field(type = FieldType.Text)
    @ApiModelProperty(value = "用户昵称")
    private String username;

    @Field(type = FieldType.Text)
    @ApiModelProperty(value = "用户头像")
    private String headerUrl;

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
    private Integer hot;

    @Field(type = FieldType.Date)
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @Field(type = FieldType.Text)
    @ApiModelProperty(value = "封面")
    private String cover;

    @Field(type = FieldType.Text)
    @ApiModelProperty(value = "话题")
    private String topic;

    @Field(type = FieldType.Integer)
    @ApiModelProperty(value = "点赞总数")
    private int likeCount;

    @Field(type = FieldType.Integer)
    @ApiModelProperty(value = "用户点赞状态 0：未点赞,1已点赞")
    private int likeType;

    @Field(type = FieldType.Integer)
    @ApiModelProperty(value = "收藏总数")
    private int collectCount;

    @Field(type = FieldType.Integer)
    @ApiModelProperty(value = "回复总数")
    private int replyCount;

    @Field(type = FieldType.Integer)
    @ApiModelProperty(value = "用户收藏状态 0：未点赞,1已点赞")
    private int collectType;

    @Field(type = FieldType.Text)
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @ApiModelProperty(value = "图片1")
    private String image1;

    @Field(type = FieldType.Text)
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @ApiModelProperty(value = "图片2")
    private String image2;

    @Field(type = FieldType.Text)
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @ApiModelProperty(value = "图片3")
    private String image3;

    @Field(type = FieldType.Text)
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @ApiModelProperty(value = "图片4")
    private String image4;

    @Field(type = FieldType.Text)
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @ApiModelProperty(value = "图片5")
    private String image5;

    @Field(type = FieldType.Text)
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @ApiModelProperty(value = "图片6")
    private String image6;

    @Field(type = FieldType.Text)
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @ApiModelProperty(value = "图片7")
    private String image7;

    @Field(type = FieldType.Text)
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @ApiModelProperty(value = "图片8")
    private String image8;

    @Field(type = FieldType.Text)
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @ApiModelProperty(value = "图片9")
    private String image9;

    public List<String > list = new ArrayList<>();
}
