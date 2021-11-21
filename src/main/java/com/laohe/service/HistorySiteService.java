package com.laohe.service;

import com.laohe.entity.HistorySite;
import com.laohe.entity.common.CommonRsp;

/**
 * @author Li XunHuan
 * @version 5.0.0
 * created at 2021/11/18 22:06
 * copyright @2021 李寻欢
 */
public interface HistorySiteService {

    /**
     * 添加放入url的记录
     */
    CommonRsp addHistorySite(HistorySite site);
}
