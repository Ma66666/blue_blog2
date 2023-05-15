package com.blog.dao.es;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;
@Document(indexName = "uservo",type = "_doc",shards = 6,replicas = 3)
@Data
public class UserEsVo {

    @Id
    @ApiModelProperty(value = "ID")
    private int id;

    @Field(type = FieldType.Text,analyzer = "ik_max_word",searchAnalyzer = "ik_smart")
    @ApiModelProperty(value = "用户名称")
    private String username;

    @Field(type = FieldType.Text,analyzer = "ik_max_word",searchAnalyzer = "ik_smart")
    @ApiModelProperty(value = "账户编号ID")
    private String accountId;

    @Field(type = FieldType.Text)
    @ApiModelProperty(value = "用户头像")
    private String headerUrl;

    @Field(type = FieldType.Text)
    @ApiModelProperty(value = "用户性别")
    private String sex;

    @Field(type = FieldType.Text)
    @ApiModelProperty(value = "用户个性签名")
    private String pSignature;

    @Field(type = FieldType.Date)
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @Field(type = FieldType.Text)
    @ApiModelProperty(value = "0：正常账号 1：Vip 2：封号 3：管理员")
    private String status;
}
