package com.laohe.common.util;

import com.alibaba.fastjson.JSON;
import com.laohe.common.entity.AgentEntity;
import com.laohe.common.entity.CommonStr;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import us.codecraft.webmagic.downloader.HttpClientDownloader;
import us.codecraft.webmagic.proxy.Proxy;
import us.codecraft.webmagic.proxy.SimpleProxyProvider;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author Li XunHuan
 * @version 5.0.0
 * created at 2021/11/21 2:56
 * copyright @2021 李寻欢
 */
public class HttpClientUtils {


    public static void getUrls(List<String> urls) throws IOException {
        urls.forEach(url -> {
            try {


            CloseableHttpClient httpClient = HttpClients.custom()
                    .evictIdleConnections(1, TimeUnit.SECONDS)
                    .build();


            HttpGet request = new HttpGet(url);
            request.setHeader("User-Agent", CommonStr.USER_AGENT);
            CloseableHttpResponse response = httpClient.execute(request);
            String text = EntityUtils.toString(response.getEntity());
            System.out.println(text);
            httpClient.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

        public static String getAgentIp() throws IOException {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpGet request = new HttpGet("http://http.tiqu.letecs.com/getip3?num=1&type=2&pro=&city=0&yys=0&port=1&time=2&ts=0&ys=0&cs=0&lb=5&sb=0&pb=4&mr=1&regions=&gm=4");
            CloseableHttpResponse response = httpClient.execute(request);
            String s = EntityUtils.toString(response.getEntity());
            System.out.println(s);
            return s;
        }

        public static HttpClientDownloader getHttpClientDownLoader() throws IOException {
            String agentIp = getAgentIp();
            AgentEntity agentEntity = JSON.parseObject(agentIp, AgentEntity.class);
            System.out.println(agentEntity.getData());


            HttpClientDownloader downloader = new HttpClientDownloader();
            downloader.setProxyProvider(SimpleProxyProvider.from(
                    new Proxy(agentEntity.getData().get(0).getIp(), agentEntity.getData().get(0).getPort())
            ));
            return downloader;
        }

    public static void main(String[] args) throws IOException {
        HttpClientUtils.getHttpClientDownLoader();
    }
}
