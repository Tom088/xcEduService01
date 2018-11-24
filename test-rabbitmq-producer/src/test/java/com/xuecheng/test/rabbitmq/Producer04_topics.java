package com.xuecheng.test.rabbitmq;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * rabbitmq入门程序
 */
public class Producer04_topics {

    //队列名称
    private static final String QUEUE_INFORM_EMAIL= "queue_inform_email";
    private static final String QUEUE_INFORM_SMS= "queue_inform_sms";
    private static final String EXCHANGE_TOPICS_INFORM="exchange_topics_inform";
    private static final String ROUTINGKEY_EMAIL="inform.#.email.#";
    private static final String ROUTINGKEY_SMS="inform.#.sms.#";

    public static void main(String[] args) throws Exception {

        Connection connection = null;
        Channel channel = null;
        try {

            //通过连接工厂创建连接和mq建立连接
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("192.168.25.138");
            factory.setPort(5672);
            factory.setUsername("guest");
            factory.setPassword("guest");
            factory.setVirtualHost("/");//rabbitmq默认虚拟机名称为“/”，虚拟机相当于一个独立的mq服务器
            //创建与rabbitmq服务的TCP连接
            connection = factory.newConnection();
            //创建与Exchange的通道，每个连接可以创建多个通道，每个通道代表一个会话任务
            channel = connection.createChannel();
            //声明交换机 String exchange, BuiltinExchangeType type
            /**
             * 参数明细
             * 1、交换机名称
             * 2、交换机类型，fanout、topic、direct、headers
             */
            channel.exchangeDeclare(EXCHANGE_TOPICS_INFORM, BuiltinExchangeType.TOPIC);
            // (String queue, boolean durable, boolean exclusive, boolean autoDelete, Map<String,Object> arguments)
            /**
             * 参数明细：
             * 1、队列名称
             * 2、是否持久化
             * 3、是否独占此队列
             * 4、队列不用是否自动删除
             * 5、参数
             */
            channel.queueDeclare(QUEUE_INFORM_EMAIL,true,false,false,null);
            channel.queueDeclare(QUEUE_INFORM_SMS,true,false,false,null);

            //交换机和队列绑定String queue, String exchange, String routingKey
            /**
             * 参数明细
             * 1、队列名称
             * 2、交换机名称
             * 3、路由key
             */
            channel.queueBind(QUEUE_INFORM_EMAIL,EXCHANGE_TOPICS_INFORM,ROUTINGKEY_EMAIL);
            channel.queueBind(QUEUE_INFORM_SMS,EXCHANGE_TOPICS_INFORM,ROUTINGKEY_SMS);

            //发送消息
            //参数：String exchange, String routingKey, BasicProperties props, byte[] body
            /**
             * 参数明细：
             * 1、exchange，交换机，如果不指定将使用mq的默认交换机（设置为""）
             * 2、routingKey，路由key，交换机根据路由key来将消息转发到指定的队列，如果使用默认交换机，routingKey设置为队列的名称
             * 3、props，消息的属性
             * 4、body，消息内容
             */
            for(int i=0;i<5;i++){
                //发送消息的时候指定routingKey
                String message = "send email inform message to user";
                channel.basicPublish(EXCHANGE_TOPICS_INFORM,"inform.email",null,message.getBytes());
                System.out.println("send to mq "+message);
            }
            for(int i=0;i<5;i++){
                //发送消息的时候指定routingKey
                String message = "send sms inform message to user";
                channel.basicPublish(EXCHANGE_TOPICS_INFORM,"inform.sms",null,message.getBytes());
                System.out.println("send to mq "+message);
            }
            for(int i=0;i<5;i++){
                //发送消息的时候指定routingKey
                String message = "send sms and email inform message to user";
                channel.basicPublish(EXCHANGE_TOPICS_INFORM,"inform.sms.email",null,message.getBytes());
                System.out.println("send to mq "+message);
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (channel != null){
                channel.close();
            }
            if (connection != null){
                connection.close();
            }
        }
    }
}
