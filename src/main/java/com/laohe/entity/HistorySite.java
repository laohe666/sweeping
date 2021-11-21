package com.laohe.entity;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.io.Serializable;

/**
 * @author Li XunHuan
 * @version 5.0.0
 * created at 2021/11/18 22:02
 * copyright @2021 李寻欢
 */
@TableName("history_site")
@Data
public class HistorySite implements Serializable {

    /**
     * 主键
     */
    @TableId
    private String id;

    /**
     * 网站的url
     */
    private String url;

    /**
     * 网站的创建时间
     */
    private String createTime;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
