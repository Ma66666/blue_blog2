package com.blog.entity.dao.es;





import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
//声明实体，主键类型
public interface BlogRepository extends ElasticsearchRepository<EsVo,Integer> {
}
