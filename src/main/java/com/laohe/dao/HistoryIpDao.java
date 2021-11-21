package com.laohe.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.laohe.entity.HistoryIp;
import org.jsoup.Connection;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Repository
public interface HistoryIpDao extends BaseMapper<HistoryIp> {
    int updateHistoryIpFlg(String url);

    List<String> getIps();
}
