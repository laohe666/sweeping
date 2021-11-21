package com.laohe.entity;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Li XunHuan
 * @version 5.0.0
 * created at 2021/11/20 20:03
 * copyright @2021 李寻欢
 */
@TableName("web_info")
@Data
public class WebInfo implements Serializable {

    /**
     * 主键id
     */
    @TableId
    private String id;

    /**
     * 网站的url
     */
    private String url;

    /**
     * 网站的标题
     */
    private String title;

    /**
     * 网站的源码
     */
    private String code;

    /**
     * 创建时间
     */
    private String createTime;


    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
