package com.laohe.process;

import com.laohe.common.entity.CommonStr;
import com.laohe.common.util.HttpClientUtils;
import com.laohe.common.util.JsoupUtils;
import com.laohe.entity.HistoryIp;
import com.laohe.service.impl.HistoryIpServiceImpl;
import lombok.SneakyThrows;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Li XunHuan
 * @version 5.0.0
 * created at 2021/11/20 14:41
 * copyright @2021 李寻欢
 */
@Component
public class IPSegmentProcess implements PageProcessor {


    private static HistoryIpServiceImpl historyIpService ;

    private static RedisTemplate<String, Object> redisTemplate;

    Site site = new Site().setTimeOut(CommonStr.OUT_TIME)
            .setCharset(CommonStr.UTF_8)
            .setUserAgent(CommonStr.USER_AGENT)
            .setSleepTime(20000).setTimeOut(5000);

    @SneakyThrows
    @Override
    public void process(Page page) {
        Html html = page.getHtml();
        Document document = html.getDocument();
        //获取地址信息
        Element cHd = document.getElementsByClass("c-hd").get(0);
        String address = cHd.getElementsByTag("span").get(0).text();

        Elements ipElements = document.getElementsByClass("J_link");
        //当前IP段下 所有的IP url集合
        List<String> ipUrls = new ArrayList<>();
        for (Element ip : ipElements) {
            HistoryIp historyIp = new HistoryIp();

            String realIp = ip.getElementsByTag("a").get(0).text();
            String ipUrl = "https://site.ip138.com/"+realIp;
            //再获取历史记录发现个数
            String num = ip.getElementsByTag("span").get(0).text();
            historyIp.setAddress(address);
            historyIp.setUrl(ipUrl);
            historyIp.setIp(realIp);
            historyIp.setHistoryNum(num);
            ipUrls.add(ipUrl);
            try {
                historyIpService.addHistoryIp(historyIp);
            } catch (Exception e) {
                System.out.println("扫描网段下ip重复"+ipUrl);
            }

        }
        scanIP(ipUrls);
    }

    @Override
    public Site getSite() {
        return site;
    }

    @Autowired
    public void getHistoryImpl(HistoryIpServiceImpl historyIpService) {
         IPSegmentProcess.historyIpService = historyIpService;
    }

    @Autowired
    public void getRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        IPSegmentProcess.redisTemplate = redisTemplate;
    }

    public  void scanIP(List<String> ipUrls) throws IOException {
        String[] urls = new String[ipUrls.size()];
        for (int i = 0; i < ipUrls.size(); i++) {
            urls[i] = ipUrls.get(i);
        }
        Spider.create(new IPUrlSegmentProcess()).addUrl(urls).setDownloader(HttpClientUtils.getHttpClientDownLoader()).run();
    }
}
