package com.blog.MQ.listener;

import com.blog.dao.ActivityListMapper;
import com.blog.dao.ActivityMapper;
import com.blog.dao.es.EsVo;
import com.blog.entity.Activity;
import com.blog.entity.Vo.ActivityListVo;
import com.blog.util.ElasticsearchUtil;
import com.blog.util.GetSetRedis;
import com.rabbitmq.client.Channel;
import org.elasticsearch.action.update.UpdateRequest;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.UpdateQuery;
import org.springframework.data.elasticsearch.core.query.UpdateQueryBuilder;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

@Component
public class SpringRabbitListener {
    @Autowired
    private ElasticsearchUtil elasticsearchUtil;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Autowired
    private ElasticsearchTemplate elasticTemplate;

    @Autowired
    private ActivityListMapper listMapper;

    @Autowired
    private ActivityMapper activityMapper;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "Blue.queue1"),
            exchange = @Exchange(name = "Blue.Topic", type = ExchangeTypes.TOPIC),
            key = "BlogUnLike"
    ))
    public void listenTopicQueue1(String msg)  {
        UpdateRequest updateRequest = new UpdateRequest();
        Map<String,Object> map = new HashMap<>();
        map.put("likeCount",redisTemplate.opsForSet().size(GetSetRedis.getBlogLikeKey(msg)).intValue());
        updateRequest.doc(map);
        UpdateQueryBuilder updateQueryBuilder = new UpdateQueryBuilder();
        updateQueryBuilder.withId(msg);
        updateQueryBuilder.withUpdateRequest(updateRequest);
        updateQueryBuilder.withClass(EsVo.class);
        UpdateQuery updateQuery = updateQueryBuilder.build();
        elasticTemplate.update(updateQuery);

        System.out.println("消费者接收到topic.queue1的消息：【" + msg + "】");

    }



    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "Blue.queue2"),
            exchange = @Exchange(name = "Blue.Topic", type = ExchangeTypes.TOPIC),
            key = "BlogLike"
    ))
    public void listenTopicQueue2(String msg){
        UpdateRequest updateRequest = new UpdateRequest();
        Map<String,Object> map = new HashMap<>();
        map.put("likeCount",redisTemplate.opsForSet().size(GetSetRedis.getBlogLikeKey(msg)).intValue());
        updateRequest.doc(map);
        UpdateQueryBuilder updateQueryBuilder = new UpdateQueryBuilder();
        updateQueryBuilder.withId(msg);
        updateQueryBuilder.withUpdateRequest(updateRequest);
        updateQueryBuilder.withClass(EsVo.class);
        UpdateQuery updateQuery = updateQueryBuilder.build();
        elasticTemplate.update(updateQuery);
        System.out.println("消费者接收到topic.queue2的消息：【" + msg + "】");
    }



    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "order.close.queue"),
            exchange = @Exchange(name = "order.event.exchange", type = ExchangeTypes.TOPIC),
            key = "order.close.routing.key"
    ))
    public void listenTopicQueue4(String msg, Channel channel, @Headers Map<String, Object> headers) throws IOException {
        ActivityListVo activityListVo = listMapper.queryOrderIndex(msg);
        if (activityListVo.getStatus()==0){
         listMapper.updateOrder(msg);
        }
        Long deliveryTag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
        System.out.println("消费者接收到topic.queue4的消息：【" + msg + "】");
        channel.basicAck(deliveryTag, false);
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "order.close.queue2"),
            exchange = @Exchange(name = "order.event.exchange2", type = ExchangeTypes.TOPIC),
            key = "order.close.routing.key2"
    ))
    public void listenTopicQueue5(String msg, Channel channel, @Headers Map<String, Object> headers) throws IOException {
//        ActivityListVo activityListVo = listMapper.queryOrderIndex(msg);
//        if (activityListVo.getStatus()==0){
//            listMapper.updateOrder(msg);
//        }
        int y = Integer.parseInt(msg);
        Activity activity = activityMapper.queryActivityById(y);

        int count = listMapper.queryCountOrder(y);

        if (count*2<activity.getActivityPeople()){
         activityMapper.updateActivityStatus(y,4);
            listMapper.setOrderStatus(y);
        }else {
            activityMapper.updateActivityStatus(y,5);
        }


        Long deliveryTag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
        System.out.println("消费者接收到topic.queue5的消息：【" + activity + "】");
        channel.basicAck(deliveryTag, false);
    }


}
