package com_api_auto;

import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.FileEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ToPost {

    public static void post() {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String url = "http://test.lemonban.com/lmcanon_web_auto/mvc/member/api/member/login";
        HttpPost post = new HttpPost(url);
        List<NameValuePair> parameters = new ArrayList<NameValuePair>();
        parameters.add(new BasicNameValuePair("nickname", "18612266860"));
        parameters.add(new BasicNameValuePair("password", "dc483e80a7a0bd9ef71d8cf973673924"));
        CloseableHttpResponse response = null;
        try {
            post.setEntity(new UrlEncodedFormEntity(parameters));
            response = httpClient.execute(post);
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert response != null;
        Header[] headers = response.getAllHeaders();
        for (Header header : headers) {
            System.out.println(header.getName() + "+" + header.getValue());
        }
    }


    public static void ToPost(String url, StringEntity stringEntity, Map<String, String> header) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost post = new HttpPost(url);
        if (header != null && header.size() > 0) {
            header.entrySet().forEach(t -> post.addHeader(t.getKey(), t.getValue()));
        }
        post.setEntity(stringEntity);
        String result = null;
        try {
            CloseableHttpResponse response = httpClient.execute(post);
            result = EntityUtils.toString(response.getEntity());
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(result);
    }


    @Test
    public void f() throws UnsupportedEncodingException {
        post();
//        System.out.println("======");
        String url = "https://177976bb-66ae-483b-8a26-c1943670c85a.mock.pstmn.io";
        StringEntity stringEntity = new StringEntity("{name: \"google\"}");
        Map<String, String> header = new HashMap<String, String>();
//        header.put("content-type","application/json");
        header.put("host","177976bb-66ae-483b-8a26-c1943670c85a.mock.pstmn.io");
        header.put("accept-encoding","gzip, deflate, br");
        ToPost(url,stringEntity,header);
    }
}
