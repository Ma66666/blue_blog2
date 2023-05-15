package com.blog.es;

import com.alibaba.fastjson.JSON;
import com.blog.BlueBlogApplication;
import com.blog.dao.BlogLikeMapper;
import com.blog.dao.es.*;
import com.blog.util.ElasticsearchUtil;
import com.blog.util.GetSetRedis;
import com.blog.util.HtmlFilter;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.data.elasticsearch.core.query.UpdateQuery;
import org.springframework.data.elasticsearch.core.query.UpdateQueryBuilder;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = BlueBlogApplication.class)
public class EsTest {


    @Autowired
    private BlogRepository blogRepository;

@Autowired
private UserRepository userRepository;

    @Autowired
    private EsMapper esMapper;
    @Autowired
    private ElasticsearchUtil elasticsearchUtil;

    @Autowired
    private BlogLikeMapper blogLikeMapper;

    @Autowired
    private ElasticsearchTemplate elasticTemplate;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Autowired
    private GetSetRedis getSetRedis;

    @Test
    public void testInsert(){
        List<EsVo> list = esMapper.queryBlog();
        for(EsVo esVo :list){
            esVo.setContent(HtmlFilter.delHtmlTags(esVo.getContent()));
            esVo.setLikeCount(redisTemplate.opsForSet().size(GetSetRedis.getBlogLikeKey(esVo.getId()+"")).intValue());
            esVo.list.add(esVo.getImage1());
            esVo.list.add(esVo.getImage2());
            esVo.list.add(esVo.getImage3());
            esVo.list.add(esVo.getImage4());
            esVo.list.add(esVo.getImage5());
            esVo.list.add(esVo.getImage6());
            esVo.list.add(esVo.getImage7());
            esVo.list.add(esVo.getImage8());
            esVo.list.add(esVo.getImage9());
            esVo.list.removeAll(Collections.singleton(null));
        }
        blogRepository.saveAll(list);
    }
    @Test
    public void testInsert1(){
        List<UserEsVo> list = esMapper.queryUser();

        userRepository.saveAll(list);
    }
    //更新数据
     @Test
     public void update() throws IOException {


//         UpdateRequest updateRequest = new UpdateRequest("blogvo","_doc","27");
//         EsVo esVo = new EsVo();
//         esVo.setUsername("Hot");
//         updateRequest.doc(JSON.toJSONString(esVo), XContentType.JSON);
//         UpdateResponse updateResponse = client.update(updateRequest, RequestOptions.DEFAULT);
//         System.out.println(updateResponse.status());
//         elasticTemplate.update();
         EsVo esVo  = new EsVo();
         esVo.setLikeCount(3);
         esVo.setId(9);
         esVo.setUsername("大黄");
         esVo.setLikeType(1);

         UpdateRequest updateRequest = new UpdateRequest();
         Map<String,Object> map = new HashMap<>();
         map.put("likeCount",10);

         updateRequest.doc(map);
         UpdateQueryBuilder updateQueryBuilder = new UpdateQueryBuilder();
         updateQueryBuilder.withId("9");
         updateQueryBuilder.withUpdateRequest(updateRequest);
         updateQueryBuilder.withClass(EsVo.class);
         UpdateQuery updateQuery = updateQueryBuilder.build();
        elasticTemplate.update(updateQuery);

//         System.out.println(JSON.toJSONString(elasticsearchUtil.update("blogvo","_doc",esVo)));

     }
    @Test
    public void testInsertList(){
//        BlogVo blogVo = blogMapper.queryById(1);
//        blogVo.setContent("66666");
//        blogRepository.save(blogVo);
    }

    @Test
    public void testDelete(){


        DeleteIndexRequest request = new DeleteIndexRequest("blogvo");

    }

    @Test
    public void testSearchBy(){
//        SearchQuery searchQuery = new NativeSearchQueryBuilder()
//                //查询条件
//                .withQuery(QueryBuilders.multiMatchQuery("高颜值6767677","title","content"))
//                .withSort(SortBuilders.fieldSort("createTime").order(SortOrder.ASC))
//                .withPageable(PageRequest.of(0,2))
//                .withHighlightFields(
//                        new HighlightBuilder.Field("title").preTags("<em>").postTags("</em>"),
//                   new HighlightBuilder.Field("content").preTags("<em>").postTags("</em>")
//                ).build();
//        Page<BlogVo> page = blogRepository.search(searchQuery);
//        System.out.println(page.getTotalElements());
//        System.out.println(page.getTotalPages());
//        System.out.println(page.getNumber());
//        System.out.println(page.getSize());
//        for (BlogVo blogVo : page){
//            System.out.println(blogVo);
//        }
    }



    @Test
    public void queryEsVo(){
    System.out.println(esMapper.queryBlog());
    }

    @Test
    public void  queryMyLikeBlog(){
        List<Integer> list = blogLikeMapper.queryUserLikeBlog("Hot12345");
        List<EsVo> list1 = esMapper.queryEsBlogListByMyLikeBlog(list);
        for (EsVo esVo : list1){
            System.out.println(esVo);
        }
    }
    @Test
    public void testSearchByTemplate() {
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.multiMatchQuery("高颜值", "title", "content"))
                .withSort(SortBuilders.fieldSort("createTime").order(SortOrder.DESC))
                .withPageable(PageRequest.of(0, 10))
                .withHighlightFields(
                        new HighlightBuilder.Field("title").preTags("<em>").postTags("</em>"),
                        new HighlightBuilder.Field("content").preTags("<em>").postTags("</em>")
                ).build();

        Page<EsVo> page = elasticTemplate.queryForPage(searchQuery,EsVo.class, new SearchResultMapper() {
            @Override
            public <T> AggregatedPage<T> mapResults(SearchResponse response, Class<T> aClass, Pageable pageable) {
                SearchHits hits = response.getHits();
                if (hits.getTotalHits() <= 0) {
                    return null;
                }

                List<EsVo> list = new ArrayList<>();
                for (SearchHit hit : hits) {
                    EsVo esVo = new EsVo();


                    esVo.setId(Integer.valueOf(hit.getSourceAsMap().get("id").toString()));

                    esVo.setAccountId(hit.getSourceAsMap().get("accountId").toString());

                    esVo.setUsername(hit.getSourceAsMap().get("username").toString());

                    esVo.setHeaderUrl(hit.getSourceAsMap().get("headerUrl").toString());

                    esVo.setTitle(hit.getSourceAsMap().get("title").toString());

                    esVo.setContent(hit.getSourceAsMap().get("content").toString());

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

                return new AggregatedPageImpl(list, pageable,
                        hits.getTotalHits(), response.getAggregations(), response.getScrollId(), hits.getMaxScore());
            }
        });

        System.out.println(page.getTotalElements());
        System.out.println(page.getTotalPages());
        System.out.println(page.getNumber());
        System.out.println(page.getSize());
        for (EsVo post : page) {
            System.out.println(post);
        }
    }
}
