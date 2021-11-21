package com.laohe.config;

import com.laohe.websocket.MyWebSocket;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;


/**
 * @author Li XunHuan
 * @version 5.0.0
 * created at 2021/11/20 16:39
 * copyright @2021 李寻欢
 * redis消息队列 -- 订阅者
 */
@Configuration
public class RedisMessageListener {

    /**
     * 创建连接工厂
     */
    @Bean
    public RedisMessageListenerContainer container(LettuceConnectionFactory factory, MessageListenerAdapter adapter) {

        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(factory);
        container.addMessageListener(adapter,new PatternTopic("webInfo"));
        return container;
    }

    /**
     * 绑定新消息监听者和接收者的方法
     */
    @Bean
   public MessageListenerAdapter listenerAdapter(MyWebSocket webSocket){
        MessageListenerAdapter adapter = new MessageListenerAdapter(webSocket, "acceptMessage");
        return adapter;
    }

}
