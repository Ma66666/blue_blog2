package com.blog.entity.Dto;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class PageDto {

    @ApiModelProperty(value = "查询条件")
    private String condition;
    @ApiModelProperty(value = "查询起始页")
    private int start;
    @ApiModelProperty(value = "查询条数")
    private int total ;
    @ApiModelProperty(value = "查询日期条件")
    private Date dateTime;


}