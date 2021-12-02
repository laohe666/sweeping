package com.laohe.process;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.api.R;
import com.laohe.common.entity.CommonStr;
import com.laohe.common.util.RedisUtils;
import com.laohe.dao.HistoryIpDao;
import com.laohe.entity.HistoryIp;
import com.laohe.entity.WebInfo;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Li XunHuan
 * @version 5.0.0
 * created at 2021/11/20 18:02
 * copyright @2021 李寻欢
 */
@Component
public class IPUrlSegmentProcess implements PageProcessor {

    private static RedisTemplate<String, Object> redisTemplate;

    private static HistoryIpDao historyIpDao;

    Site site = new Site().setUserAgent(CommonStr.USER_AGENT)
            .setTimeOut(CommonStr.OUT_TIME)
            .setCharset(CommonStr.UTF_8)
            .setSleepTime(2000);


    @Override
    public void process(Page page) {
        Html html = page.getHtml();
        Document document = html.getDocument();
        Element list = document.getElementById("list");
        Elements allUrls = list.getElementsByTag("a");
        //扫描出所有网站的url
        List<String> urls = new ArrayList<>();
        for (Element u : allUrls) {
            String url = u.text();
            urls.add("http://" + url);
        }
        getWebInfo(urls);
        //再更新当前url的状态为Yes
        try {
            historyIpDao.updateHistoryIpFlg(page.getUrl().toString());
        } catch (Exception e) {
            System.out.println("更新IP的URL状态失败");
        }

    }

    @Override
    public Site getSite() {
        return site;
    }

    @Autowired
    private void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        IPUrlSegmentProcess.redisTemplate = redisTemplate;
    }

    @Autowired
    private void setHistoryIpDao(HistoryIpDao historyIpDao) {
        IPUrlSegmentProcess.historyIpDao = historyIpDao;
    }

    //然后再去爬取当前ip下的网站信息
    public void getWebInfo(List<String> urls) {
        String[] us = new String[urls.size()];
        for (int i = 0; i<urls.size() ; i++) {
            us[i] = urls.get(i);
        }
        if (urls.size()>50) {
            Spider.create(new WebInfoProcess()).addUrl(us).thread(20).run();
        } else {
            Spider.create(new WebInfoProcess()).addUrl(us).thread(10).run();
        }

    }
}
