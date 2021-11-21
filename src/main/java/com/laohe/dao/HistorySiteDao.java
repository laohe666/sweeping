package com.laohe.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.laohe.entity.HistorySite;
import org.springframework.stereotype.Repository;

@Repository
public interface HistorySiteDao extends BaseMapper<HistorySite> {

    /**
     * 通过URL查询是否已经被扫描过了
     */
    String selectByUrl(String url);

}
