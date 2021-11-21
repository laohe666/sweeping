package com.laohe.handler;

import org.apache.http.NoHttpResponseException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @author Li XunHuan
 * @version 5.0.0
 * created at 2021/11/21 15:00
 * copyright @2021 李寻欢
 */
@ControllerAdvice
public class MyExceptionHandler {
    @ExceptionHandler(value = NoHttpResponseException.class)
    public void execptionHandler(NoHttpResponseException e) {
        //关闭所有线程
        System.out.println("主机异常了  主机异常了 主机异常了");
    }

}
