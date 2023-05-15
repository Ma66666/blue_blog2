package com.blog;

import com.blog.config.FanoutConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = BlueBlogApplication.class)
public class mptest {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private FanoutConfig fanoutConfig;

    @Test
    public void send(){
        rabbitTemplate.convertAndSend(fanoutConfig.getOrderEventExchange(),fanoutConfig.getOrderCloseDelayRoutingKey(),"1");
    }
}
