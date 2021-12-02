package com.laohe.websocket;

import com.alibaba.fastjson.JSON;
import com.laohe.entity.WebInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Li XunHuan
 * @version 5.0.0
 * created at 2021/11/21 20:06
 * copyright @2021 李寻欢
 */
@Component
@ServerEndpoint(value = "/websocket")
public class MyWebSocket {

    /**
     * 存放session的Map
     */
    private static Map<String, Session> clients = new ConcurrentHashMap<>();


    private static RedisTemplate<String, Object> redisTemplate;
    /**
     * 连接成功时
     */
    @OnOpen
    public void onOpen(Session session) {
        //当连接进来时给他返回当前的运行状态
        System.out.println("有连接来了有连接来了" + session.getId());
        if (redisTemplate.opsForValue().get("scaning") == null && "No".equals(redisTemplate.opsForValue().get("scaning"))){
            System.out.println("当前的扫描状态是关闭的");
        } else {
            System.out.println("当前的扫描状态为：" +redisTemplate.opsForValue().get("scaning"));
        }
        clients.put(session.getId(),session);
    }

    /**
     * 当连接关闭时
     */
    @OnClose
    public void onClose(Session session) {
        System.out.println("连接关闭了" + session.getId());
        clients.remove(session.getId());
    }

    /**
     * 收到客户端的消息后
     */
    @OnMessage
    public void onMessage(String message,Session session) {
        //
    }

    @OnError
    public void onError(Session session,Throwable error) {
        error.printStackTrace();
    }

    /**
     * 广播消息给客户端
     */
    private void sendMessage(String message) {
        for (Map.Entry<String, Session> sessionEntry : clients.entrySet()) {
            Session toSession = sessionEntry.getValue();
            toSession.getAsyncRemote().sendText(message);
        }
    }

    public void acceptMessage(String accept) {
        WebInfo webInfo = JSON.parseObject(accept, WebInfo.class);
        webInfo.setCreateTime(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
        System.out.println(accept);
        sendMessage(JSON.toJSONString(webInfo));
    }

    @Autowired
    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        MyWebSocket.redisTemplate = redisTemplate;
    }
}
