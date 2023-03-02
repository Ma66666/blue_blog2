package com.blog.service;

import com.blog.entity.Dto.PageDto;
import com.blog.entity.Vo.BlogListVo;


import java.util.List;
import java.util.Map;

public interface ListService {

    /**
     *
     * @param pageDto 包含是否分页数据
     * @return 返回博客封面信息集合
     */
    Map<String,Object> getBlogListVo(PageDto pageDto);
}
