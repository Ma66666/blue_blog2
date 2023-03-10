package com.blog.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

//排序实体
@Data
public class Sort {
    @ApiModelProperty(value = "ID主键")
    private int id;
    @ApiModelProperty(value = "排序方式")
    private String sortname;
}
