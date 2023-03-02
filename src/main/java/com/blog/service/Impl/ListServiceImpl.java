package com.blog.service.Impl;

import com.blog.dao.ListMapper;
import com.blog.entity.Dto.PageDto;
import com.blog.entity.Vo.BlogListVo;
import com.blog.service.ListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ListServiceImpl implements ListService {
    @Autowired
    private ListMapper listMapper;

    /**
     * 查询排行榜并分页，分页没做，以后完善
     * @param pageDto 包含是否分页数据
     * @return
     */
    @Override
    public  Map<String,Object> getBlogListVo(PageDto pageDto) {
        Map<String,Object> map = new HashMap<>();
        if (pageDto.getTotal()==0){ //如果total为0，说明不分页
            if (pageDto.getDateCondition().equals("最热")){ //判断前端的查询条件是否为最热，如果是，查询最热的SQL语句
                map.put("total",listMapper.queryHotCount(pageDto));
                map.put("BlogListVo",listMapper.queryBlogListByHot(pageDto));
                return map;
            }else if (pageDto.getDateCondition().equals("月榜")){
                map.put("total",listMapper.queryMonthCount(pageDto));
                map.put("BlogListVo",listMapper.queryBlogListByMonthAndHot(pageDto));
            }else if (pageDto.getDateCondition().equals("周榜")){
                map.put("total",listMapper.queryWeekCount(pageDto));
                map.put("BlogListVo",listMapper.queryBlogListByWeekAndHot(pageDto));
            }else if (pageDto.getDateCondition().equals("日榜")){
                map.put("total",listMapper.queryDayCount(pageDto));
                map.put("BlogListVo",listMapper.queryBlogListByDayAndHot(pageDto));
            }else {
                map.put("total",listMapper.queryLatestCount(pageDto));
                map.put("BlogListVo",listMapper.queryBlogListByLatest(pageDto));
            }

        return map;
        }   else{
            map.put("sorry","没找到");
            return map;
        }

    }
}
