package com.laohe.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Li XunHuan
 * @version 5.0.0
 * created at 2021/11/18 21:21
 * copyright @2021 李寻欢
 */
@Configuration
@MapperScan("com.laohe.dao")
public class MybatisPlusConfig {
    /**
     * 注入分页内容
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        //设置单页最大条数,默认配置为500条, -1不限制
        paginationInterceptor.setLimit(500);
        return paginationInterceptor;
    }
}
