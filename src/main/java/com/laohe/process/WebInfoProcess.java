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
            .setCharset(CommonStr.UTF_8);

    @Override
    public void process(Page page) {
        Html html = page.getHtml();
        Document document = html.getDocument();
        String title = document.title();
        WebInfo webInfo = new WebInfo();
        webInfo.setUrl(page.getUrl().toString());

        webInfo.setTitle(title);
        webInfo.setCode(String.valueOf(page.getStatusCode()));
        if (!compare(title)) {
            return;
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

    public boolean compare(String title) {

        if (title.contains("电影") || title.contains("传媒") || title.contains("国产")) {
            return false;
        }
        if (title.contains("影视") || title.contains("小说") || title.contains("高清")) {
            return false;
        }
        if (title.contains("视频") || title.contains("韩国") || title.contains("影院")) {
            return false;
        }
        if (title.contains("四虎") || title.contains("啪啪") || title.contains("久久")) {
            return false;
        }
        if (title.contains("三级") || title.contains("欧美") || title.contains("視頻")) {
            return false;
        }
        if (title.contains("404") || title.contains("无法访问") || title.contains("漫画")) {
            return false;
        }
        if (title.contains("私服") || title.contains("公司") || title.contains("没有找到站点")) {
            return false;
        }
        if (title.contains("日本") || title.contains("AV") || title.contains("福利")) {
            return false;
        }
        if (title.contains("Enter") || title.contains("captcha") || title.contains("域名")) {
            return false;
        }
        if (title.contains("在线观看") || title.contains("无码") || title.contains("图片")) {
            return false;
        }
        if (title.contains("哥哥") || title.contains("媒体")) {
            return false;
        }

        return true;
    }

}


