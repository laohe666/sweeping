package com.laohe.common.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.query.SortQuery;

import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author Li XunHuan
 * @version 5.0.0
 * created at 2021/11/18 22:00
 * copyright @2021 李寻欢
 */
public class RedisUtils {

    @Autowired
    private static RedisTemplate<String, Object> redisTemplate;

    /**
     * 插入到Redis中
     */
    public static void setRedis(String key, Object value) {
        //存入到redis中去
        redisTemplate.opsForValue().set(key, value);

    }
//
//    public static void main(String[] args) {
//        try {   //根据主机名返回其可能的所有InetAddress对象
//            InetAddress[] addresses = InetAddress.getAllByName("88750004.com");
//            for(InetAddress addr:addresses){
//                if (addr.getHostAddress().length() >= 16) {
//                    continue;
//                }
//                System.out.println(addr.getHostAddress());
//            }
//           } catch (UnknownHostException e) {
//                         e.printStackTrace();
//           }
//    }
}
