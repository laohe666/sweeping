package com.laohe;

import static org.junit.Assert.assertTrue;

import com.laohe.common.util.JsoupUtils;
import com.laohe.dao.HistorySiteDao;
import com.laohe.entity.HistorySite;
import com.laohe.process.IPSegmentProcess;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import us.codecraft.webmagic.Spider;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Unit test for simple App.
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class AppTest 
{
    @Autowired
    private HistorySiteDao historySiteDao;

    @Autowired
    RedisTemplate<String,Object> redisTemplate;
    @Test
    public void shouldAnswerWithTrue() throws IOException {
//        HistorySite historySite = new HistorySite();
//        historySite.setUrl("www.baidu.com");
//        historySiteDao.insert(historySite);
//        redisTemplate.convertAndSend("webInfo","123456");
//        Spider.create(new IPSegmentProcess()).addUrl("https://chapangzhan.com/103.72.144.0/24").run();
//        List<String> array = new ArrayList<>();
//        array.add("https://site.ip138.com/154.39.249.3");
//        JsoupUtils.getWebUrls(array);
        redisTemplate.convertAndSend("webInfo","我是张三");
    }



}
