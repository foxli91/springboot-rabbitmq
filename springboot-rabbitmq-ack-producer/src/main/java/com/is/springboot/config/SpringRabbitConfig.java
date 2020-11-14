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
    public static final String TOPIC_EXCHANGE_NAME = "sbt_ack_message_exchange";

    public static final String QUEUE_NAME = "sbt_ack_alibaba_queue";
    private static final String QUEUE_NAME_M = "sbt_ack_ms_queue";
    public static final String TTL_QUEUE = "sbt_ack_ttl_queue";
   /* public static final String TOPIC_EXCHANGE_NAME="sbt_ack_message_exchange";

    public static final String DIRECT_QUEUE_NAME="sbt_ack_alibaba_queue";
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
        return QueueBuilder.durable(DIRECT_QUEUE_NAME).build();
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
    }*/
    /***
     * Description ttl 的队列，里面的map就是对于的配置参数
     * @param
     * @return org.springframework.amqp.core.Queue
     * @author li-shuai
     * @date 13:15 2020/11/13
     */
   /* @Bean("test_ttl_queue")
    public Queue createTtlQueue(){*/
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
      /*  Map<String,Object> map=new HashMap<>(11);
        // 设置过期时间为100秒
        map.put("x-message-ttl",Integer.valueOf(100000));
        return QueueBuilder.durable(TTL_QUEUE).withArguments(map).build();
    }*/

   /* @Bean
    public Binding bindingTtlQueue(@Qualifier("test_ttl_queue") Queue queue, @Qualifier("bootExchange") Exchange exchange){

      return   BindingBuilder.bind(queue).to(exchange).with("ttl").noargs();
    }*/
    /****************死信队列demo 测试****************************/
    /**
     * 正常的交换机
     */
    public static final String DIRECT_NAME = "money_direct_exchange_sli";
    /**
     * 队列名称
     */
    public static final String DIRECT_QUEUE_NAME = "money_direct_queue_sli";
    /**
     * 死信交换机
     */
    public static final String DEAD_LETTER_EXCHANGE = "dead_letter_exchange_sli";
    /**
     * 死信队列
     */
    public static final String DEAD_LETTER_QUEUE = "dead_letter_queue";

    /***
     * 创建正常交换机
     */
    @Bean("directExchange")
    public Exchange createExchange() {
        return ExchangeBuilder.directExchange(DIRECT_NAME).durable(true).autoDelete().build();
    }

    /***
     * 创建正常队列
     */
    @Bean("directQueue")
    public Queue createQueue() {
        /**成为死信的三种情况
         *  1.队列消息长度到达限制**
         *  2.消费者拒绝接收消费消息，basicNack/basicReject，并且不把消息重写放入原目标队列， requeue=false**
         *  3.原队列存在消息过期设置，消息到达超时时间未被消费
         * */

        HashMap<String, Object> args = new HashMap<>(5);
        // 设置死信交换机名称
        args.put("x-dead-letter-exchange", "dead_letter_exchange_sli");
        // 设置死信队列的路由key
        args.put("x-dead-letter-routing-key", "dlxKey");
        // 1. 设置队列过期时间 10秒
        // 1. 设置队列过期时间 60秒
        args.put("x-message-ttl", Integer.valueOf(60000));
        // 2. 设置队列的长度 10条
//        args.put("x-max-length", Integer.valueOf(10));
        return QueueBuilder.durable(DIRECT_QUEUE_NAME).withArguments(args).build();
    }

    /***
     * 将队列和正常的交换机绑定
     */
    @Bean
    public Binding directQueueBinding(@Qualifier("directQueue") Queue queue, @Qualifier("directExchange") Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("moneyKey").noargs();
    }

    /**
     * 创建死信交换机
     */
    @Bean("dlxExchange")
    public Exchange createDeadLetterExchange() {
        return ExchangeBuilder.directExchange(DEAD_LETTER_EXCHANGE).build();
    }

    /**
     * 创建死信队列
     */
    @Bean("dlxQueue")
    public Queue createDlxQueue() {
        return QueueBuilder.durable(DEAD_LETTER_QUEUE).build();
    }

    /**
     * 将死信队列和死信交换机绑定
     */
    @Bean
    public Binding directDlxQueueBinding(@Qualifier("dlxQueue") Queue queue, @Qualifier("dlxExchange") Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("dlxKey").noargs();
    }

    /**
     * 延迟交换机
     */

    public static final String DELAY_EXCHANGE = "DELAY_EXCHANGE";
    public static final String DELAY_QUEUE = "DELAY_QUEUE";
    public static final String DELAY_ROUTING_KEY = "DELAY_ROUTING_KEY";

    @Bean("delayExchange")
    public Exchange delayExchange() {
        Map<String, Object> args = new HashMap<>(1);
//       x-delayed-type    声明 延迟队列Exchange的类型
        args.put("x-delayed-type", "direct");
        return new CustomExchange(DELAY_EXCHANGE, "x-delayed-message", true, false, args);
    }

    @Bean("delayQueue")
    public Queue delayQueue() {
        return QueueBuilder.durable(DELAY_QUEUE).build();
    }

    /**
     * 将延迟队列通过routingKey绑定到延迟交换器
     *
     * @return
     */
   /* @Bean
    public Binding delayQueueBindExchange() {
        return new Binding(DELAY_QUEUE, Binding.DestinationType.QUEUE, DELAY_EXCHANGE, DELAY_ROUTING_KEY, null);
    }*/
   @Bean
   public Binding delayQueueBindExchange(@Qualifier("delayQueue") Queue queue, @Qualifier("delayExchange") Exchange exchange){
       return BindingBuilder.bind(queue).to(exchange).with(DELAY_ROUTING_KEY).noargs();
   }


}
