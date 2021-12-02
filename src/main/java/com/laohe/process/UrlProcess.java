package com.laohe.process;

import com.laohe.common.entity.CommonStr;
import com.laohe.dao.HistoryIpDao;
import com.laohe.dao.HistorySegmentDao;
import com.laohe.dao.HistorySiteDao;
import com.laohe.entity.HistorySegment;
import com.laohe.entity.HistorySite;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;

/**
 * @author Li XunHuan
 * @version 5.0.0
 * created at 2021/12/1 20:20
 * copyright @2021 李寻欢
 */
@Component
public class UrlProcess implements PageProcessor {
    Site site = new Site().setUserAgent(CommonStr.USER_AGENT)
            .setTimeOut(CommonStr.OUT_TIME)
            .setCharset(CommonStr.UTF_8)
            .setSleepTime(2000);

    private static HistorySegmentDao historySegmentDao;

    @Override
    public void process(Page page) {
        Html html = page.getHtml();
        Document document = html.getDocument();
        Element j_ip_history = document.getElementById("J_ip_history");
        Elements a = j_ip_history.getElementsByTag("a");

        for (Element historyIp : a) {
            HistorySegment historySegment = new HistorySegment();
            String hip = historyIp.getElementsByTag("a").get(0).text();
            String ipseg =  hip.substring(0,hip.lastIndexOf(".")+1)+"0/24";
            historySegment.setSegment(ipseg);
            try {
                historySegmentDao.insert(historySegment);
            } catch (Exception e) {
                System.out.println("IPSEG插入错误");
            }

        }
    }

    @Override
    public Site getSite() {
        return site;
    }

    @Autowired
    public void setHistorySiteDao(HistorySegmentDao historySegmentDao) {
        UrlProcess.historySegmentDao = historySegmentDao;
    }
}
