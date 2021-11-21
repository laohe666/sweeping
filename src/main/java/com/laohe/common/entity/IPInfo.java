package com.laohe.common.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Li XunHuan
 * @version 5.0.0
 * created at 2021/11/21 17:16
 * copyright @2021 李寻欢
 */
@Data
public class IPInfo implements Serializable {
    private String ip;
    private int port;
    private String outip;
}
