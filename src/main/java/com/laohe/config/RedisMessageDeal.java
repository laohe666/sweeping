package com.laohe.config;

import com.laohe.entity.WebInfo;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @author Li XunHuan
 * @version 5.0.0
 * created at 2021/11/20 17:27
 * copyright @2021 李寻欢
 */
@Component
public class RedisMessageDeal{



    private String  flg ;
    public void acceptMessage(String info) {
        System.out.println("劳资收到了信息了" + info);
    }

}
