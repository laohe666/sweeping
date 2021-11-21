package com.laohe.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.laohe.entity.HistorySegment;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface HistorySegmentDao extends BaseMapper<HistorySegment> {
    List<String> selectNoScanSegment() ;

    int updateSegmentFlg(String segment);
}
