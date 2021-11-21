package com.laohe.controller;

import com.baomidou.mybatisplus.extension.api.R;
import com.laohe.entity.HistorySite;
import com.laohe.entity.common.CommonRsp;
import com.laohe.service.impl.HistorySiteServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    /**
     * 添加扫描
     */
    @RequestMapping("addSegment")
    public CommonRsp addSegment (@RequestBody HistorySite site) {
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
}
