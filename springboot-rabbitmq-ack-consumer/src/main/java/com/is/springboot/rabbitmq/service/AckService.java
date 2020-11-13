package com.is.springboot.rabbitmq.service;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author li-shuai
 * @Description //todo
 * @ClassName AckService
 * @date 2020.11.12 21:37
 */
@Component
public class AckService {
    public static int i=0;
    /***
     * Description 这个是一个守护线程，一旦队列里面有数据发送过来就会执行一次，但是
     * 每次都是一个栈帧方法，压栈弹栈执行完毕之后回收
     * @param str str
     * @param channel channel
     * @param message message
     * @return void
     * @author li-shuai
     * @date 22:20 2020/11/12
     */
    @RabbitListener(queues = "sbt_ack_alibaba_queue")
    @RabbitHandler
    public void getMessages(String str, Channel channel, Message message) {

        System.out.println("count计数器："+(++i));
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        System.out.println("deliveryTag:"+deliveryTag);
        try {
            System.out.println("str:" + str);
            System.out.println("message:" + new String(message.getBody()));
//            if (i==2){
//                int c=1/0;
//            }
//            TimeUnit.SECONDS.sleep(2);
            //告诉服务器收到这条消息 已经被我消费了 可以在队列删掉
            // 这样以后就不会再发了 否则消息服务器以为这条消息没处理掉 后续还会在发
            //第二个参数  表示是否签收多条消息 false 表示不是，true表示是
            channel.basicAck(deliveryTag, true);
        } catch (Exception e) {
            System.out.println("=====执行到catch了=======");
            try {
                //第二个参数  表示是否签收多条消息 false 表示不是，true表示是
                //第三个参数  重回队列，如果设置为true，则消息重写回到队列broker会重新发送消息给消费端
                channel.basicNack(deliveryTag, true, true);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }
}
