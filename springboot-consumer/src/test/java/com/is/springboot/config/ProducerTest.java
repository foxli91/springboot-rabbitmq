package com.is.springboot.config;

import com.is.springboot.config.SpringRabbitConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author li-shuai
 * @Description //todo
 * @ClassName ProducerTest
 * @date 2020.11.11 22:58
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class ProducerTest {
    // 注入完 template
    @Autowired
    private RabbitTemplate template;
    @Test
    public void sendMessage(){
        template.convertAndSend(SpringRabbitConfig.TOPIC_EXCHANGE_NAME,"boot.sli","springboot测试");
    }


}
