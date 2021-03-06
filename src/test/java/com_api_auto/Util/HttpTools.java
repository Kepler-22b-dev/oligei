package com_api_auto.Util;

import org.apache.http.NameValuePair;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import java.io.IOException;
import java.util.*;

public class HttpTools {

    public static void get(String url, Map<String, String> parametersMap) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        List<NameValuePair> parameters = new ArrayList<>();
        Set<String> keySets = parametersMap.keySet();
        for (String key : keySets) {
            parameters.add(new BasicNameValuePair(key, parametersMap.get(key)));
        }
        String forequarters = URLEncodedUtils.format(parameters, "Utf8");
        if(parameters.isEmpty()){
            url += forequarters;
        }else {
            url +="?" + forequarters;
        }
        HttpGet get = new HttpGet(url);

        CloseableHttpResponse closeableHttpResponse = httpClient.execute(get);
        String closeableHttpResponseString = EntityUtils.toString(closeableHttpResponse.getEntity());
        System.out.println(closeableHttpResponseString);
    }

    public static void post(String url, Map<String, String> parameterMap) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //以键值对的形式存放入参到list列表中；
        List<NameValuePair> parameters = new ArrayList<>();
        //获取map的key值，并存放在set集合中；
        Set<String> keySet = parameterMap.keySet();
        //遍历生成的set，将获取到的key值添加到list中
        for (String Key : keySet) {
            parameters.add(new BasicNameValuePair(Key, parameterMap.get(Key)));
        }
        String forequarters = URLEncodedUtils.format(parameters, "Utf8");
        if(parameters.isEmpty()){
            url += forequarters;
        }else {
            url +="?" + forequarters;
        }
        HttpPost post = new HttpPost(url);
        CloseableHttpResponse HttpResponse = httpClient.execute(post);
        int statusCode = HttpResponse.getStatusLine().getStatusCode();
        System.out.println(statusCode);
    }

    public static void post(String url, String JSONString) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost post = new HttpPost(url);
        StringEntity stringEntity = new StringEntity(JSONString);
        post.setEntity(stringEntity);
        CloseableHttpResponse HttpResponse = httpClient.execute(post);
        String result = EntityUtils.toString(HttpResponse.getEntity());
        System.out.println(result);
    }
}
