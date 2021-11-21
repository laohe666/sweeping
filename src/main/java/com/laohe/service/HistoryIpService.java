package com.laohe.service;

import com.laohe.common.entity.CommonStr;
import com.laohe.entity.HistoryIp;

public interface HistoryIpService {

    /**
     * 插入到IP数据库中
     */
    boolean addHistoryIp(HistoryIp historyIp);
}
