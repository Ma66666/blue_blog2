package com.blog.config;

import lombok.Data;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
@Data
public class FanoutConfig2 {
    /**
     * 交换机
     */
    private String orderEventExchange="order.event.exchange2";
    /**
     * 延迟队列，不能被消费者监听
     */
    private String orderCloseDelayQueue = "order.close.delay.queue2";
    /**
     * 关单队列，延迟队列的消息过期后转发的队列，用于被消费者监听
     */
    private String orderCloseQueue = "order.close.queue2";

    /**
     * 交换机
     */
    private String orderCloseExchange="order.close.exchange2";

    /**
     * 进入到延迟队列的routingKey
     */
    private String orderCloseDelayRoutingKey = "order.close.delay.routing.key2";
    /**
     * 进入死信队列的routingKey，消息过期进入死信队列的key
     */
    private String orderCloseRoutingKey = "order.close.routing.key2";
    /**
     * 过期时间，毫秒单位，临时改为1分钟过期
     */
    private Integer ttl = 1000 * 10;
    /**
     * 消息转换器
     * @return
     */

    /**
     * 创建交换机，topic类型，一般一个业务一个交换机
     * @return
     */
    @Bean
    public Exchange orderEventExchange1(){
        return new TopicExchange(orderEventExchange,true,false);
    }
    /**
     * 延迟队列getOrderEventExchange
     * @return
     */
    @Bean
    public Queue orderCloseDelayQueue1(){
        Map<String,Object> args = new HashMap<>(3);
        args.put("x-dead-letter-exchange",orderEventExchange);
        args.put("x-dead-letter-routing-key",orderCloseRoutingKey);
        args.put("x-message-ttl",ttl);
        return new Queue(orderCloseDelayQueue,true,false,false,args);

    }
    
    /**
     *
     * 死信队列，是一个普通队列，用于被监听
     * @return
     */
    @Bean
    public Queue orderCloseQueue1(){
        return new Queue(orderCloseQueue,true,false,false);
    }
    /**
     * 第一个队列 即延迟队列和交换机建立绑定关系
     * @return
     */
    @Bean
    public Binding orderCloseDelayBinding1(){
        return new Binding(orderCloseDelayQueue,
                Binding.DestinationType.QUEUE,orderEventExchange,orderCloseDelayRoutingKey,null);
    }
    /**
     * 死信队列和死信交换机建立绑定关系
     * @return
     */
    @Bean
    public Binding orderCloseBinding1(){

        return new Binding(orderCloseQueue,
                Binding.DestinationType.QUEUE,orderEventExchange,orderCloseRoutingKey,null);
    }



}
