package com.laohe.common.util;



import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Li XunHuan
 * @version 5.0.0
 * created at 2021/11/20 13:56
 * copyright @2021 李寻欢
 */
public class NetUtils {

    /**
     * 获取当前域名绑定的IP地址
     */
    public static List<String> getWebAllUrl(String url) {
        List<String> ips = new ArrayList<>();
        try {
            //根据主机名返回其可能的所有InetAddress对象
            InetAddress[] addresses = InetAddress.getAllByName(url);
            ips = jointIp(addresses);
        } catch (UnknownHostException e) {
        }
        return ips;
    }

    /**
     * 拼接网段
     * @param addresses
     */
    public static List<String> jointIp(InetAddress[] addresses) {
        List<String> list = new ArrayList<>();
        if (addresses.length <= 0 ) {
            return null;
        }
        for (InetAddress address : addresses) {
            String addressStr = address.getHostAddress();
            if (addressStr.length() >= 16 ) {
                continue;
            }
            String segment = addressStr.substring(0, addressStr.lastIndexOf("."))+".0/24";
            list.add(segment);
        }
        return list;
    }

    /**
     * Jsoup爬取网站
     */


    public static void main(String[] args) {
        List<String> webAllUrl = getWebAllUrl("www.ytx18.vip");
        webAllUrl.forEach(w -> System.out.println(w));
    }


}
