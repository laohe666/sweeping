package com.laohe.entity;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Li XunHuan
 * @version 5.0.0
 * created at 2021/11/20 17:50
 * copyright @2021 李寻欢
 */
@Data
@TableName("history_ip")
public class HistoryIp implements Serializable {
    /**
     * 主键id
     */
    @TableId
    private String id;

    /**
     * ip
     */
    private String ip;

    /**
     * 拼接好的url
     */
    private String url;

    /**
     * address 归属那个服务器
     */
    private String address;

    /***
     * 当前ip下历史记录个数
     */
    private String historyNum;

    /**
     * 创建时间
     */
    private String createTime;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
