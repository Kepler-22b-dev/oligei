package com_api_auto.util;

import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.*;

public class HttpUtilForPost {

        private static void ToPostbyFormEntity(String url, Map<String, String> parameterMap) throws IOException {
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
            Header[] allHeaders = HttpResponse.getAllHeaders();
            for (Header allHeader : allHeaders) {
                System.out.println(allHeader.getName() + ":" + allHeader.getValue());
            }
        }

        public static void ToPostbyJSON(String url, String JSONString) throws IOException {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpPost post = new HttpPost(url);
            StringEntity stringEntity = new StringEntity(JSONString);
            post.setEntity(stringEntity);
            CloseableHttpResponse HttpResponse = httpClient.execute(post);
            String result = EntityUtils.toString(HttpResponse.getEntity());
            System.out.println(result);
        }

        @Test
        public void f2() throws IOException {
            String url = "https://177976bb-66ae-483b-8a26-c1943670c85a.mock.pstmn.io";
            String string = "{\"name\":\"google\"}";
            ToPostbyJSON(url, string);
        }

        @Test
        public void f3() throws IOException {
            String url = "http://test.lemonban.com/lmcanon_web_auto/mvc/member/api/member/login";
            Map<String, String> parameterMap = new HashMap<>();
            parameterMap.put("nickname","18612266860");
            parameterMap.put("password","dc483e80a7a0bd9ef71d8cf973673924");
            ToPostbyFormEntity(url,parameterMap);
        }
    }

