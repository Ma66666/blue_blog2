package com.blog.service.Impl;

import com.blog.entity.dao.BlogLikeMapper;
import com.blog.entity.dao.CommentMapper;
import com.blog.entity.dao.es.BlogRepository;
import com.blog.entity.dao.es.EsMapper;
import com.blog.entity.dao.es.EsVo;
import com.blog.service.EsService;
import com.blog.util.GetSetRedis;
import com.blog.util.GetTokenAccountId;
import com.blog.util.HtmlFilter;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
public class EsServiceImpl implements EsService {

    @Autowired
    private BlogRepository blogRepository;


    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private GetTokenAccountId getTokenAccountId;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Autowired
    private BlogLikeMapper blogLikeMapper;

    @Autowired
    private EsMapper esMapper;

    @Autowired
    private ElasticsearchTemplate elasticTemplate;


    @Override
    public Map<String,Object> queryEsBlogList(int pagenum, int pagesize, HttpServletRequest httpServletRequest) {
        String accountId = getTokenAccountId.getTokenAccountId(httpServletRequest);
        Map<String,Object> map = new HashMap<>();
                SearchQuery searchQuery = new NativeSearchQueryBuilder()
                //查询条件
                .withSort(SortBuilders.fieldSort("hot").order(SortOrder.DESC))
                        .withSort(SortBuilders.fieldSort("createTime").order(SortOrder.DESC))
                .withPageable(PageRequest.of(pagenum,pagesize))
                .withHighlightFields(
                        new HighlightBuilder.Field("title").preTags("<em>").postTags("</em>"),
                   new HighlightBuilder.Field("content").preTags("<em>").postTags("</em>")
                ).build();
        Page<EsVo> page = blogRepository.search(searchQuery);

        for (EsVo esVo : page){
            esVo.setContent(HtmlFilter.delHtmlTags(esVo.getContent()));
            String entityLikeKey = GetSetRedis.getBlogLikeKey(""+esVo.getId());
            String entityLikeKey1 =GetSetRedis.getBlogCollectKey(""+esVo.getId());
            if (accountId=="空的"){
                System.out.println(false);
                esVo.setLikeType(0);
            }else {
                //判断用户是否点赞
                boolean islike = redisTemplate.opsForSet().isMember(entityLikeKey, accountId);
                if (islike) {
                    esVo.setLikeType(1);

                } else {
                    esVo.setLikeType(0);
                }
                //判断用户是否收藏
                boolean islike2 = redisTemplate.opsForSet().isMember(entityLikeKey1,accountId);
                if (islike2){
                    esVo.setCollectType(1);
                }else {
                    esVo.setCollectType(0);
                }
            }
            esVo.setReplyCount(commentMapper.queryCommentCount(esVo.getId()));

        }

        map.put("total",page.getContent().size());
        map.put("all",page.getTotalElements());
        map.put("EsBlogListVo",page.getContent());
        return map;
    }

    @Override
    public Map<String, Object> quertEsBlogListByDate(int pagenum, int pagesize, HttpServletRequest httpServletRequest) {
        String accountId = getTokenAccountId.getTokenAccountId(httpServletRequest);
        Map<String,Object> map = new HashMap<>();
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                //查询条件
//                .withQuery(QueryBuilders.multiMatchQuery("高颜值6767677","title","content"))
                //排序
                .withSort(SortBuilders.fieldSort("createTime").order(SortOrder.DESC))
                .withPageable(PageRequest.of(pagenum,pagesize))
                .withHighlightFields(
                        new HighlightBuilder.Field("title").preTags("<em>").postTags("</em>"),
                        new HighlightBuilder.Field("content").preTags("<em>").postTags("</em>")
                ).build();
        Page<EsVo> page = blogRepository.search(searchQuery);

        for (EsVo esVo : page){
            esVo.setContent(HtmlFilter.delHtmlTags(esVo.getContent()));
            String entityLikeKey = GetSetRedis.getBlogLikeKey(""+esVo.getId());
            String entityLikeKey1 =GetSetRedis.getBlogCollectKey(""+esVo.getId());
            if (accountId=="空的"){
                System.out.println(false);
                esVo.setLikeType(0);
            }else {
                //判断用户是否点赞
                boolean islike = redisTemplate.opsForSet().isMember(entityLikeKey, accountId);
                if (islike) {
                    esVo.setLikeType(1);

                } else {
                    esVo.setLikeType(0);
                }
                //判断用户是否收藏
                boolean islike2 = redisTemplate.opsForSet().isMember(entityLikeKey1,accountId);
                if (islike2){
                    esVo.setCollectType(1);
                }else {
                    esVo.setCollectType(0);
                }
            }

            esVo.setReplyCount(commentMapper.queryCommentCount(esVo.getId()));



        }

        map.put("total",page.getContent().size());
        map.put("all",page.getTotalElements());
        map.put("EsBlogListVo",page.getContent());
        return map;
    }

    @Override
    public Map<String, Object> queryEsBlogListByMyLikeBlog(int pagenum, int pagesize, HttpServletRequest httpServletRequest, int selectType) {
//          String accountId = getTokenAccountId.getTokenAccountId(httpServletRequest);
//          Map<String,Object> map = new HashMap<>();
//          if (accountId.equals("")){
//              throw new BlogException(ResultCodeEnum.SIGN_ERROR);
//          }
//        List<Integer> list = blogLikeMapper.queryUserLikeBlog("Hot12345");
//        List<EsVo> list1 = esMapper.queryEsBlogListByMyLikeBlog(list);
//        map.put("EsVo",)
        //返回参数
        Map<String,Object> map = new HashMap<>();
        //获取发起请求的用户ID
        String accountId = getTokenAccountId.getTokenAccountId(httpServletRequest);
        //获得用户点赞的博客
        List<Integer> list1 = blogLikeMapper.queryUserLikeBlog(accountId);
        //建立ES查询条件
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                //查询条件 将点赞的博客ID作为查询条件
                .withQuery(QueryBuilders.termsQuery("_id",list1))
                //排序博客创建时间排序
                .withSort(SortBuilders.fieldSort("createTime").order(SortOrder.ASC))
                //分页，前端传的默认是（0,5）每页显示五条数据
                .withPageable(PageRequest.of(pagenum,pagesize))
                .withHighlightFields(
                        new HighlightBuilder.Field("title").preTags("<em>").postTags("</em>"),
                        new HighlightBuilder.Field("content").preTags("<em>").postTags("</em>")
                ).build();
        Page<EsVo> page = blogRepository.search(searchQuery);

        for (EsVo esVo : page){
            esVo.setContent(HtmlFilter.delHtmlTags(esVo.getContent()));
            esVo.setLikeType(1);
            esVo.setReplyCount(commentMapper.queryCommentCount(esVo.getId()));

        }

        map.put("total",page.getContent().size());
        map.put("all",page.getTotalElements());
        map.put("EsBlogListVo",page.getContent());
        return map;

    }

    @Override
    public Map<String, Object> queryEsBlogListByUserLike(int pagenum, int pagesize, HttpServletRequest httpServletRequest, int selectType) {
       String accountId = getTokenAccountId.getTokenAccountId(httpServletRequest);
        //获得redis的key
        String user = GetSetRedis.getUserLike(accountId);
        //获得redis里用户的关注集合
        Set<String> set =redisTemplate.opsForSet().members(user);
        String str = "";
        for (String str1:set){
            str = str +str1+",";
        }

        Map<String,Object> map = new HashMap<>();
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                //查询条件
//                .withQuery(QueryBuilders.multiMatchQuery("nNEjUJzW","accountId","content"))

//               .withQuery(QueryBuilders.matchQuery("accountId",set))
                .withQuery(QueryBuilders.matchQuery("accountId",str))
                //排序
                .withSort(SortBuilders.fieldSort("createTime").order(SortOrder.DESC))
                .withPageable(PageRequest.of(pagenum,pagesize))
                .withHighlightFields(
                        new HighlightBuilder.Field("title").preTags("<em>").postTags("</em>"),
                        new HighlightBuilder.Field("content").preTags("<em>").postTags("</em>")
                ).build();
        Page<EsVo> page = blogRepository.search(searchQuery);

        for (EsVo esVo : page){
            esVo.setContent(HtmlFilter.delHtmlTags(esVo.getContent()));
            String entityLikeKey = GetSetRedis.getBlogLikeKey(""+esVo.getId());
            String entityLikeKey1 =GetSetRedis.getBlogCollectKey(""+esVo.getId());
            if (accountId=="空的"){
                System.out.println(false);
                esVo.setLikeType(0);
            }else {
                //判断用户是否点赞
                boolean islike = redisTemplate.opsForSet().isMember(entityLikeKey, accountId);
                if (islike) {
                    esVo.setLikeType(1);

                } else {
                    esVo.setLikeType(0);
                }
                //判断用户是否收藏
                boolean islike2 = redisTemplate.opsForSet().isMember(entityLikeKey1,accountId);
                if (islike2){
                    esVo.setCollectType(1);
                }else {
                    esVo.setCollectType(0);
                }
            }

            esVo.setReplyCount(commentMapper.queryCommentCount(esVo.getId()));


        }

        map.put("total",page.getContent().size());
        map.put("all",page.getTotalElements());
        map.put("EsBlogListVo",page.getContent());
        return map;
    }

    @Override
    public Map<String, Object> queryEsBlogListByCondition(int pagenum, int pagesize, HttpServletRequest httpServletRequest, String searchData) {
        //获取发起请求用户ID
        String accountId = getTokenAccountId.getTokenAccountId(httpServletRequest);
        //创建查询条件构造器
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.multiMatchQuery(searchData, "title", "content"))
                .withSort(SortBuilders.fieldSort("createTime").order(SortOrder.DESC))
                .withPageable(PageRequest.of(pagenum, pagesize))
                .withHighlightFields(
                        new HighlightBuilder.Field("title").preTags("<mark>").postTags("</mark>"),
                        new HighlightBuilder.Field("content").preTags("<mark>").postTags("</mark>")
                ).build();
        //用Template查询并分页
        Page<EsVo> page = elasticTemplate.queryForPage(searchQuery,EsVo.class, new SearchResultMapper() {
            @Override
            //对查询结果重写
            public <T> AggregatedPage<T> mapResults(SearchResponse response, Class<T> aClass, Pageable pageable) {
                SearchHits hits = response.getHits();
                //总条数，如果为0返回null；
                if (hits.getTotalHits() <= 0) {
                    return null;
                }

                //创建集合
                List<EsVo> list = new ArrayList<>();
                //遍历查询结果为 EsVo赋值
                for (SearchHit hit : hits) {
                    EsVo esVo = new EsVo();


                    esVo.setId(Integer.valueOf(hit.getSourceAsMap().get("id").toString()));

                    esVo.setAccountId(hit.getSourceAsMap().get("accountId").toString());

                    esVo.setUsername(hit.getSourceAsMap().get("username").toString());

                    esVo.setHeaderUrl(hit.getSourceAsMap().get("headerUrl").toString());

                    esVo.setTitle(hit.getSourceAsMap().get("title").toString());

                    esVo.setContent(hit.getSourceAsMap().get("content").toString());

                    System.out.println(esVo.getContent());

                    esVo.setHot(Integer.valueOf(hit.getSourceAsMap().get("hot").toString()));

                    esVo.setCreateTime(new Date(Long.valueOf(hit.getSourceAsMap().get("createTime").toString())));

                    esVo.setCover(hit.getSourceAsMap().get("cover").toString());

                    esVo.setLikeCount(Integer.valueOf(hit.getSourceAsMap().get("likeCount").toString()));

                    esVo.setLikeType(Integer.valueOf(hit.getSourceAsMap().get("likeType").toString()));

                    esVo.setCollectCount(Integer.valueOf(hit.getSourceAsMap().get("collectCount").toString()));

                    esVo.setCollectType(Integer.valueOf(hit.getSourceAsMap().get("collectType").toString()));


                    esVo.setList((List)hit.getSourceAsMap().get("list"));

                    // 处理高亮显示的结果
                    HighlightField titleField = hit.getHighlightFields().get("title");
                    if (titleField != null) {
                        esVo.setTitle(titleField.getFragments()[0].toString());
                    }

                    HighlightField contentField = hit.getHighlightFields().get("content");
                    if (contentField != null) {
                        esVo.setContent(contentField.getFragments()[0].toString());

                    }

                    list.add(esVo);
                }
                //返回总查询结果，list包含查询结果
                return new AggregatedPageImpl(list, pageable,
                        hits.getTotalHits(), response.getAggregations(), response.getScrollId(), hits.getMaxScore());
            }
        });

        for (EsVo esVo : page) {
            //获取redis的key
            String entityLikeKey = GetSetRedis.getBlogLikeKey(""+esVo.getId());
            String entityLikeKey1 =GetSetRedis.getBlogCollectKey(""+esVo.getId());
            if (accountId=="空的"){
                esVo.setContent(HtmlFilter.delHtmlTags(esVo.getContent()));
                System.out.println(false);
                esVo.setLikeType(0);
            }else {
                //判断用户是否点赞
                boolean islike = redisTemplate.opsForSet().isMember(entityLikeKey, accountId);
                if (islike) {
                    esVo.setLikeType(1);

                } else {
                    esVo.setLikeType(0);
                }
                //判断用户是否收藏
                boolean islike2 = redisTemplate.opsForSet().isMember(entityLikeKey1,accountId);
                if (islike2){
                    esVo.setCollectType(1);
                }else {
                    esVo.setCollectType(0);
                }
            }
            esVo.setReplyCount(commentMapper.queryCommentCount(esVo.getId()));
        }
        Map<String,Object>  map = new HashMap<>();
        map.put("EsBlogListVo",page.getContent());
        map.put("total",page.getContent().size());
        map.put("all",page.getTotalElements());
        return map;
    }
}
