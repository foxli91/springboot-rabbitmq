package com.is.rabbitmq.producer;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author li-shuai
 * @Description //todo
 * @ClassName ProducerMain
 * @date 2020.11.10 13:21
 */
public class ProducerPubSubMain {
    public static void main(String[] args) throws IOException, TimeoutException {
        // 创建连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        // 设置参数 虚拟机，用户名，密码....
        // 家里
//        factory.setHost("192.168.7.130");
        // 公司
        factory.setHost("172.16.121.145");
        factory.setVirtualHost("/lsv");
        factory.setUsername("lishuai");
        factory.setPassword("lishuai");
        factory.setPort(5672);
        // 创建连接 connection
        Connection connection = factory.newConnection();
        // 创建 channel
        Channel channel = connection.createChannel();
        /** public DeclareOk exchangeDeclare(String exchange, BuiltinExchangeType type, boolean durable, boolean autoDelete, boolean internal，Map<String, Object> arguments) throws IOException {
        return this.exchangeDeclare(exchange, type.getType(), durable, autoDelete, arguments);
         String exchange 交换机名称
         BuiltinExchangeType type    交换机类型
                    DIRECT("direct"), 点对点交换机
                    FANOUT("fanout"),  广播形式的交换机
                    TOPIC("topic"),   通配符形式的交换机
                    HEADERS("headers");  很少用不做学习
         boolean durable   是否持久化
         boolean autoDelete   是否自动删除
         boolean internal   内部 一般设置为false
         Map<String, Object> arguments  参数
    }*/
        String exchangeName="message_exchange";
        // 创建 交换机
        channel.exchangeDeclare(exchangeName, BuiltinExchangeType.FANOUT,true,false,false,null);

        // 创建 队列
        String aliBaba="alibaba";
        String microsoft="microsoft";

        channel.queueDeclare(aliBaba,true,false,false,null);
        channel.queueDeclare(microsoft,true,false,false,null);
        /**queueBind(String queue, String exchange, String routingKey)
         * queue 队列名称
         * exchange 交换机名称
         * routingKey 路由key
         *      如果交换机是 fanout就设置为空字符串
         * */
        // 绑定交换机和队列

        channel.queueBind(aliBaba,exchangeName,"");
        channel.queueBind(microsoft,exchangeName,"");
        // 发送消息
        String messages="我是22222查询方法.....log.info";
        // exchangeName 交换机，第二个是路由key没有就是""
        channel.basicPublish(exchangeName,"",null,messages.getBytes());
        // 释放资源
        channel.close();
        connection.close();

    }
}
