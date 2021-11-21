package com.laohe.common.util;

import com.alibaba.fastjson.JSON;
import com.laohe.common.entity.CommonStr;
import com.laohe.entity.WebInfo;
import io.netty.handler.codec.Headers;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

/**
 * @author Li XunHuan
 * @version 5.0.0
 * created at 2021/11/21 1:57
 * copyright @2021 李寻欢
 */
@Component
public class JsoupUtils {

    private static RedisTemplate<String, Object> redisTemplate;
    /**
     * Jsoup爬取ip下的网站
     */
    public static void getWebUrls(List<String> urls) throws IOException {
        urls.forEach(ipUrl -> {


//            System.out.println("-----" + ipUrl);
            Document document = null;
            try {

                document = Jsoup.connect(ipUrl).userAgent(CommonStr.USER_AGENT)
                        .header("Connection","keep-Alive")
                        .header("Cookie","Hm_lvt_d39191a0b09bb1eb023933edaa468cd5=1637383669,1637390736,1637401237,1637431664; Hm_lpvt_d39191a0b09bb1eb023933edaa468cd5=1637431664")
                        .header("Cache-Control", "max-age=0")
                        .get();
                Element list = document.getElementById("list");
                Elements allUrls = list.getElementsByTag("a");
                //集成一个set集合 当前
                for (Element u : allUrls) {
                    String url = u.text();
                    WebInfo webInfo = new WebInfo();
                    webInfo.setUrl(url);
                    //将url对象存入到Redis中去
                    redisTemplate.convertAndSend("webInfo", JSON.toJSON(webInfo));
                    System.out.println(url);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        });

    }

    @Autowired
    public void getRedistemplate(RedisTemplate<String, Object> redisTemplate) {
        JsoupUtils.redisTemplate = redisTemplate;
    }
}
