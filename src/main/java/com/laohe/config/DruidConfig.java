package com.laohe.config;


import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Li XunHuan
 * @version 5.0.0
 * created at 2021/11/18 21:15
 * copyright @2021 李寻欢
 */

public class DruidConfig {
    @Bean
    public ServletRegistrationBean druidServlet() {
        // 主要实现WEB监控的配置处理
        ServletRegistrationBean registrationBean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");

        //对监控配置
        //白名单
        registrationBean.addInitParameter("allow","*");
        //黑名单
        registrationBean.addInitParameter("deny","");
        //用户名
        registrationBean.addInitParameter("loginUsername","admin");
        //密码
        registrationBean.addInitParameter("loginPassword","admin");
        //是否可以重置数据源
        registrationBean.addInitParameter("resetEnable","false");

        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean() ;
        filterRegistrationBean.setFilter(new WebStatFilter());
        // 所有请求进行监控处理
        filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.css,/druid/*");
        return filterRegistrationBean ;
    }

}
