package com.laohe.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.laohe.entity.WebInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WebInfoDao extends BaseMapper<WebInfo> {

    List<WebInfo> selectWebInfos();

}
