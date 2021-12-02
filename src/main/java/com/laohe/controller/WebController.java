package com.laohe.controller;

import com.baomidou.mybatisplus.extension.api.R;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.laohe.dao.WebInfoDao;
import com.laohe.entity.HistorySite;
import com.laohe.entity.WebInfo;
import com.laohe.entity.common.CommonRsp;
import com.laohe.service.impl.HistorySiteServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Li XunHuan
 * @version 5.0.0
 * created at 2021/11/20 14:55
 * copyright @2021 李寻欢
 */
@RestController
@RequestMapping
public class WebController {

    @Autowired
    private HistorySiteServiceImpl historySiteService;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private WebInfoDao webInfoDao;

    private String scaing = "scaning";
    private String no = "No";
    /**
     * 添加扫描
     */
    @RequestMapping("addSegment")
    public CommonRsp addSegment (String url) {
        HistorySite site = new HistorySite();
        site.setUrl(url);
        //先查询当前网站是否被扫描过了
        CommonRsp commonRsp = historySiteService.addHistorySite(site);
        String scaning = (String) redisTemplate.opsForValue().get("scaning");
        commonRsp.setContent("添加扫描成功,服务器扫描状态:" + scaning);
        return commonRsp;
    }

    /**
     * 获取扫描状态
     */
    @RequestMapping("getScanState")
    public String getScanState() {
        String scaning = (String) redisTemplate.opsForValue().get("scaning");
        return scaning;
    }

    /**
     * 获取当前历史扫描出来的网站记录
     */
    @RequestMapping("getWebInfoHistory")
    public PageInfo<WebInfo> getWebInfoHistory(int num, int size) {
        PageHelper.startPage(num,size);
        List<WebInfo> webInfos = webInfoDao.selectWebInfos();
        PageInfo<WebInfo> pageInfo = new PageInfo<>(webInfos);
        return pageInfo;
    }

    /**
     * 重置扫描状态
     */
    @RequestMapping("reset")
    public String resetState() {

        redisTemplate.opsForValue().set(scaing,no);
        return "更改状态成功";
    }

    /**
     * 查看当前扫描状态
     */
    @RequestMapping("showstate")
    public String showState() {
        return (String) redisTemplate.opsForValue().get("scaning");
    }
}
