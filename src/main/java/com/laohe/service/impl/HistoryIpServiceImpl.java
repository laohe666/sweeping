package com.laohe.service.impl;

import com.laohe.dao.HistoryIpDao;
import com.laohe.entity.HistoryIp;
import com.laohe.entity.HistorySite;
import com.laohe.entity.common.CommonRsp;
import com.laohe.service.HistoryIpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Li XunHuan
 * @version 5.0.0
 * created at 2021/11/20 21:06
 * copyright @2021 李寻欢
 */
@Service
public class HistoryIpServiceImpl implements HistoryIpService {
    @Autowired
    private HistoryIpDao historyIpDao;

    @Override
    public boolean addHistoryIp(HistoryIp historyIp) {
        return historyIpDao.insert(historyIp) > 0;
    }
}
