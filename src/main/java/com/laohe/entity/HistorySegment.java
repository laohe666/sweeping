package com.laohe.entity;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.security.auth.login.CredentialNotFoundException;
import java.io.Serializable;

/**
 * @author Li XunHuan
 * @version 5.0.0
 * created at 2021/11/21 13:37
 * copyright @2021 李寻欢
 */
@Data
@TableName("history_segment")
public class HistorySegment implements Serializable {

    /**
     * 主键 id
     */
    @TableId
    private String id;

    /**
     * segment 网段
     */
    private String segment;

    /**
     * 创建时间
     */
    private String createTime;

    @Override
    public String toString(){
        return JSON.toJSONString(this);
    }
}
