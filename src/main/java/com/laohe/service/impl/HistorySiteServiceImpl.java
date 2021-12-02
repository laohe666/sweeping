package com.laohe.service.impl;

import com.laohe.common.util.NetUtils;
import com.laohe.dao.HistorySegmentDao;
import com.laohe.dao.HistorySiteDao;
import com.laohe.entity.HistorySegment;
import com.laohe.entity.HistorySite;
import com.laohe.entity.common.CommonRsp;
import com.laohe.process.UrlProcess;
import com.laohe.service.HistorySiteService;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import us.codecraft.webmagic.Spider;

import java.util.List;

/**
 * @author Li XunHuan
 * @version 5.0.0
 * created at 2021/11/18 22:08
 * copyright @2021 李寻欢
 */
@Service
public class HistorySiteServiceImpl implements HistorySiteService {

    @Autowired
    private HistorySiteDao historySiteDao;

    @Autowired
    private HistorySegmentDao historySegmentDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CommonRsp addHistorySite(HistorySite site) {
        CommonRsp commonRsp = new CommonRsp();
        //先查询当前网站是否被扫描过了
        String url = historySiteDao.selectByUrl(site.getUrl());

        if (!StringUtils.isBlank(url)) {
            commonRsp.setContent("该网站已经扫描过了！！！");
            return commonRsp;
        }
        //先插入到数据库中去
        historySiteDao.insert(site);
//        //如果没扫描过,就获取当前网站的ip
//        List<String> webAllUrl = NetUtils.getWebAllUrl(site.getUrl());
//        //插入到数据库中去
//        webAllUrl.forEach(w -> {
//            HistorySegment historySegment = new HistorySegment();
//            historySegment.setSegment(w);
//            try {
//                historySegmentDao.insert(historySegment);
//            } catch (Exception e) {
//                System.out.println("主键重复");
//            }
//        });
        Spider.create(new UrlProcess()).addUrl("https://site.ip138.com/"+site.getUrl());
        //将当前网段存储到数据库中去
        return commonRsp;
    }
}
