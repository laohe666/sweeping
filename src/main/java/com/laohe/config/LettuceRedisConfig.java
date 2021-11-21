package com.laohe.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.io.Serializable;

/**
 * @author Li XunHuan
 * @version 5.0.0
 * created at 2021/11/18 21:07
 * copyright @2021 李寻欢
 */
@Configuration
public class LettuceRedisConfig {
    /**
     * 设置序列化器
     * */
   @Bean
   public RedisTemplate<String, Object> getRedisTemplate(LettuceConnectionFactory factory) {
       RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
       redisTemplate.setKeySerializer(new StringRedisSerializer());
       redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
       redisTemplate.setConnectionFactory(factory);
       return redisTemplate;
   }
}
