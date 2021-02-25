package com_api_auto;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HttpClientUtil {

    /**
     * Http请求的GET方法
     * @param url 输入请求的url
     * @param parameters 请求参数，keyvalue格式，合并到目标分支，哈哈哈
     * @return
     */
    public static String ToGet(String url, List<NameValuePair> parameters) {
        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            for (int i = 0; i < parameters.size(); i++) {
                if (i == 0) {
                    url += "?";
                } else {
                    url += "&";
                }
                NameValuePair nameValuePair = parameters.get(i);
                String name = nameValuePair.getName();
                String value = nameValuePair.getValue();
                url += name + "=" + value;
            }
            HttpGet get = new HttpGet(url);
            CloseableHttpResponse response = null;
            response = httpClient.execute(get);
            String result = EntityUtils.toString(response.getEntity());
            System.out.println(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "接口返回异常";
    }

    /**
     * Http请求的GET方法
     * @param url 请求地址
     * @return
     */
    public static String ToGet(String url) {
        List<NameValuePair> parameters = new ArrayList<NameValuePair>();
        ToGet(url, parameters);
        return "==";
    }

    /**
     * HTTP请求的POST方法
     * @param url 请求地址
     * @param parameters 请求参数，键值对形式
     * @return
     */
    public static String ToPost(String url, List<NameValuePair> parameters) {
        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpPost post = new HttpPost(url);
            String result = null;
            post.setEntity(new UrlEncodedFormEntity(parameters));
            CloseableHttpResponse response = httpClient.execute(post);
            String resutl = EntityUtils.toString(response.getEntity());
            System.out.println(result);
            response.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "接口返回异常";

    }

    @Test
    public void f() {
        List<NameValuePair> parameters = new ArrayList<NameValuePair>();
        String url = "https://fc3dd024-bbf2-4023-ab27-fcba2569c8ae.mock.pstmn.io";
        ToGet(url);
    }

    @Test
    public void f1() {
        List<NameValuePair> parameters = new ArrayList<NameValuePair>();
        parameters.add(new BasicNameValuePair("nickname", "18612266860"));
        parameters.add(new BasicNameValuePair("password", "dc483e80a7a0bd9ef71d8cf973673924"));
        String url = "http://test.lemonban.com/lmcanon_web_auto/mvc/member/api/member/login";
        ToPost(url, parameters);
    }

}
