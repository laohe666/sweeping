package com.laohe.common.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author Li XunHuan
 * @version 5.0.0
 * created at 2021/11/21 17:10
 * copyright @2021 李寻欢
 */
@Data
public class AgentEntity implements Serializable {
    private Integer code;

    private String msg;

    private boolean success;

    private List<IPInfo> data;



}

