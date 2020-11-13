package com.is.springboot.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author li-shuai
 * @Description //todo
 * @ClassName SpringRabbitConfig
 * @date 2020.11.11 22:42
 */
@Configuration
public class SpringRabbitConfig {
    public static final String TOPIC_EXCHANGE_NAME="sbt_ack_message_exchange";

    public static final String QUEUE_NAME="sbt_ack_alibaba_queue";
    private static final String QUEUE_NAME_M="sbt_ack_ms_queue";
    public static final String TTL_QUEUE="sbt_ack_ttl_queue";
    // 交换机
    @Bean("bootExchange")
    public Exchange  createExchange(){
        return ExchangeBuilder.directExchange(TOPIC_EXCHANGE_NAME).durable(true).autoDelete().build();
    }
    // 队列
    @Bean("alibabaQueue")
    public Queue createQueue(){
        return QueueBuilder.durable(QUEUE_NAME).build();
    }

    // 队列
    @Bean("msQueue")
    public Queue createMsQueue(){
        return QueueBuilder.durable(QUEUE_NAME_M).build();
    }
    // 绑定交换机和队列 binding
       // 1. 知道哪个队列
       // 2. 知道哪个交换机
       //  3. 设置routeKey
    @Bean
    public Binding bindingQueue(@Qualifier("alibabaQueue") Queue queue, @Qualifier("bootExchange") Exchange exchange){

        return BindingBuilder.bind(queue).to(exchange).with("boot").noargs();
    }

    @Bean
    public Binding bindingMsQueue(@Qualifier("msQueue") Queue queue, @Qualifier("bootExchange") Exchange exchange){

        return BindingBuilder.bind(queue).to(exchange).with("direct").noargs();
    }
    /***
     * Description ttl 的队列，里面的map就是对于的配置参数
     * @param
     * @return org.springframework.amqp.core.Queue
     * @author li-shuai
     * @date 13:15 2020/11/13
     */
    @Bean("test_ttl_queue")
    public Queue createTtlQueue(){
        /**
         x-message-ttl                  Number
         x-expires                      Number
         x-max-length                   Number
         x-max-length-bytes             Number
         x-overflow                     String
         x-dead-letter-exchange         String
         x-dead-letter-routing-key      String
         x-single-active-consumer       Boolean
         x-max-priority                 Number
         x-queue-mode   (lazy)          String
         x-queue-master-locator         String
         */
        Map<String,Object> map=new HashMap<>(11);
        // 设置过期时间为10秒
        map.put("x-message-ttl",Integer.valueOf(10000));
        return QueueBuilder.durable(TTL_QUEUE).withArguments(map).build();
    }

    @Bean
    public Binding bindingTtlQueue(@Qualifier("test_ttl_queue") Queue queue, @Qualifier("bootExchange") Exchange exchange){

      return   BindingBuilder.bind(queue).to(exchange).with("ttl").noargs();
    }

}
