package com.is.springboot.config;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

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
    public void sendMessage() {
        template.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            /***
             * Description confirm
             * @param correlationData 相关配置信息
             * @param ack 交换机是否成功收到消息，true表示成功，false表示失败
             * @param cause  失败的原因
             */
            @Override
            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
                System.out.println("confirm方法被执行了");
                System.out.println("配置信息:" + correlationData);
                System.out.println("是否成功" + ack);
                if (ack) {
                    System.out.println("发送成功了");
                    System.out.println("失败的原因" + cause);
                } else {
                    System.out.println("发送失败了");
                    System.out.println("失败的原因" + cause);
                }
            }
        });
        for (int i = 0; i <1000 ; i++) {

            template.convertAndSend(SpringRabbitConfig.TOPIC_EXCHANGE_NAME , "boot", "[i="+i+" ]ack发送了 172.16.121.145 测试");
           /* try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/
        }
        }

    @Test
    public void testReturn() {
        //设置交换机处理失败消息的模式，注意：新版的rabbitMQ不设置也会执行返回
        template.setMandatory(true);
        template.setReturnCallback(new RabbitTemplate.ReturnCallback() {
            /***
             * Description returnedMessage
             * @param message message 消息对象
             * @param i i 消息码
             * @param s s 错误信息
             * @param s1 s1  交换机信息
             * @param s2 s2  路由键
             * @return void
             * @author li-shuai
             * @date 15:42 2020/11/12
             */
            @Override
            public void returnedMessage(Message message, int i, String s, String s1, String s2) {
                System.out.println("return执行了.....");
                System.out.println("message：" + message);
                System.out.println("message：" + new String(message.getBody()));
                System.out.println("code" + i);
                System.out.println("s" + s);
                System.out.println("交换机:" + s1);
                System.out.println("路由键：" + s2);
            }
        });
        template.convertAndSend(SpringRabbitConfig.TOPIC_EXCHANGE_NAME, "boot", "ack发送了 172.16.121.145 测试");

    }


}
