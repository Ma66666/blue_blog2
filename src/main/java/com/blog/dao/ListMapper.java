package com.blog.dao;

import com.blog.entity.Dto.PageDto;
import com.blog.entity.Vo.BlogListVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ListMapper {
    int queryHotCount(PageDto pageDto);
    List<BlogListVo>queryBlogListByHot1(PageDto pageDto); //按热度排序分页
    List<BlogListVo> queryBlogListByHot(PageDto pageDto);//按热度排序不分页

    int queryMonthCount(PageDto pageDto);
    List<BlogListVo> queryBlogListByMonthAndHot(PageDto pageDto); //按月热度不分页
    List<BlogListVo> queryBlogListByMonthAndHot1(PageDto pageDto); //按月热度分页

    int queryWeekCount(PageDto pageDto);
    List<BlogListVo> queryBlogListByWeekAndHot(PageDto pageDto);  //按周热度不分页
    List<BlogListVo> queryBlogListByWeekAndHot1(PageDto pageDto);  //按周热度分页

    int queryDayCount(PageDto pageDto);
    List<BlogListVo> queryBlogListByDayAndHot(PageDto pageDto);  //按日热度不分页
    List<BlogListVo> queryBlogListByDayAndHot1(PageDto pageDto);//按日热度分页

    int queryLatestCount(PageDto pageDto);
    List<BlogListVo> queryBlogListByLatest(PageDto pageDto);  //按日热度不分页
    List<BlogListVo> queryBlogListByLatest1(PageDto pageDto);//按日热度分页

}
