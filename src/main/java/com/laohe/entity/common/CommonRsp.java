package com.laohe.entity.common;

import com.alibaba.fastjson.JSON;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Li XunHuan
 * @version 5.0.0
 * created at 2021/11/18 22:10
 * copyright @2021 李寻欢
 */
@Data
public class CommonRsp<T> implements Serializable {

    /**
     * 消息内容
     */
    private String content;

    /**
     * 消息状态码
     */
    private String code;

    /**
     * 消息数据
     */
    private T data;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
