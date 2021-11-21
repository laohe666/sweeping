package com.laohe.schedul;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.laohe.common.util.HttpClientUtils;
import com.laohe.dao.HistoryIpDao;
import com.laohe.dao.HistorySegmentDao;
import com.laohe.process.IPSegmentProcess;
import com.laohe.process.IPUrlSegmentProcess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import us.codecraft.webmagic.Spider;

import java.io.IOException;
import java.util.List;

/**
 * @author Li XunHuan
 * @version 5.0.0
 * created at 2021/11/21 13:29
 * copyright @2021 李寻欢
 */
@Configuration      //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling   // 2.开启定时任务
public class WebInfoSchedul {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private HistorySegmentDao segmentDao;

    @Autowired
    private HistoryIpDao historyIpDao;

    @Scheduled(cron = "0 */1 * * * ?")
    public void getWebInfo() {
        //从Redis中读取标识
        String scaning = (String) redisTemplate.opsForValue().get("scaning");

        //如果正在扫描中的话
        if (scaning == null && "Yes".equals(scaning)) {
            return;
        }
        //如果没在扫描中从数据库中读出未扫描的网段
        PageHelper.startPage(0,6);
        List<String> list = segmentDao.selectNoScanSegment();
        PageInfo<String> pageInfo = new PageInfo<>(list);
        List<String> list1 = pageInfo.getList();
        if (list.size() < 0 && list == null) {
            return;
        }
        //有数据开始扫描时 更新状态为Yes
        redisTemplate.opsForValue().set("scaning","Yes");
        try {
        list1.forEach(seg -> {
            String url = "https://chapangzhan.com/" + seg;
            Spider.create(new IPSegmentProcess()).addUrl(url).run();

                segmentDao.updateSegmentFlg(seg);
        });
        } catch (Exception e) {
            System.out.println("扫描网段时异常 ,线程中断,更新扫描状态");
        } finally {
            redisTemplate.opsForValue().set("scaning","No");
        }

    }

    /**
     * 定时去扫没扫描过的ip
     */
    @Scheduled(cron = "0 */1 * * * ?")
    public void scanIp() {
        //从Redis中读取标识
        String scaning = (String) redisTemplate.opsForValue().get("scaning");
        //如果正在扫描中的话
        if (scaning == null && "Yes".equals(scaning)) {
            return;
        }
        //如果没在扫描中从数据库中读出未扫描的网段
        PageHelper.startPage(0,20);
        List<String> list = historyIpDao.getIps();
        PageInfo<String> pageInfo = new PageInfo<>(list);
        List<String> list1 = pageInfo.getList();
        if (list.size() < 0 && list == null) {
            return;
        }
        //有数据开始扫描时 更新状态为Yes
        redisTemplate.opsForValue().set("scaning","Yes");


        list1.forEach(l -> {
            try {
                Spider.create(new IPUrlSegmentProcess()).addUrl(l).setDownloader(HttpClientUtils.getHttpClientDownLoader()).run();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

//    @Scheduled(cron = "0 */1 * * * ?")
//    public void test() {
//        System.out.println(redisTemplate.opsForValue().get("scaning"));
//        redisTemplate.convertAndSend("webInfo","12333");
//    }
}
