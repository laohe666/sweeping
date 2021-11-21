package com.laohe.process;

import com.alibaba.fastjson.JSON;
import com.laohe.common.entity.CommonStr;
import com.laohe.dao.WebInfoDao;
import com.laohe.entity.WebInfo;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import lombok.Data;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;

/**
 * @author Li XunHuan
 * @version 5.0.0
 * created at 2021/11/20 23:29
 * copyright @2021 李寻欢
 */
@Component
public class WebInfoProcess implements PageProcessor {

    private static WebInfoDao webInfoDao;

    private static RedisTemplate<String, Object> redisTemplate;
    Site site = new Site().setUserAgent(CommonStr.USER_AGENT)
            .setTimeOut(CommonStr.OUT_TIME)
            .setCharset(CommonStr.UTF_8)
            .setSleepTime(2000);

    @Override
    public void process(Page page) {
        Html html = page.getHtml();
        Document document = html.getDocument();
        String title = document.title();
        WebInfo webInfo = new WebInfo();
        webInfo.setUrl(page.getUrl().toString());
        if (!title.contains("反诈公益宣传")) {
            webInfo.setTitle(title);
            webInfo.setCode(html.toString());
        }

        try {
            webInfoDao.insert(webInfo);
        }catch (DuplicateKeyException e) {
            System.out.println("数据库插入异常");
        }

        webInfo.setCode("不存放源码");
        redisTemplate.convertAndSend("webInfo", JSON.toJSON(webInfo));
    }

    @Override
    public Site getSite() {
        return site;
    }

    @Autowired
    private void setWebInfoDao(WebInfoDao webInfoDao){
        WebInfoProcess.webInfoDao = webInfoDao;
    }

    @Autowired
    private void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        WebInfoProcess.redisTemplate = redisTemplate;
    }
}
