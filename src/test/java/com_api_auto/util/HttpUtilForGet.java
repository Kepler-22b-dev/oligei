package com_api_auto.util;

import org.apache.http.NameValuePair;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class HttpUtilForGet {


    private void get(String url, HashMap<String, String> parametersMap) {
        CloseableHttpClient httpClient = HttpClients.createDefault();

        List<NameValuePair> parameters = new ArrayList<>();
        Set<String> keySets = parametersMap.keySet();
        for (String key : keySets) {
            parameters.add(new BasicNameValuePair(key, parametersMap.get(key)));
        }
//        StringBuilder stringBuilder = new StringBuilder(url);
//        for (int i = 0; i < parameters.size(); i++) {
//            if (i == 0) {
//                stringBuilder.append("?");
//            } else {
//                stringBuilder.append("&");
//            }
//            NameValuePair nameValuePair = parameters.get(i);
//            String name = nameValuePair.getName();
//            String value = nameValuePair.getValue();
//            stringBuilder.append(name).append("=").append(value);
//        }
//        System.out.println(stringBuilder.toString());
        String forequarters = URLEncodedUtils.format(parameters, "Utf8");
        if(parameters.isEmpty()){
            url += forequarters;
        }else {
            url +="?" + forequarters;
        }
        HttpGet get = new HttpGet(url);
        try {
            CloseableHttpResponse closeableHttpResponse = httpClient.execute(get);
            String closeableHttpResponseString = EntityUtils.toString(closeableHttpResponse.getEntity());
            System.out.println(closeableHttpResponseString);
        } catch (IOException e) {
            //TODO 日志补充
//            Logger logger = Logger.getLogger("请求结果为空");
        }
    }

    @Test
    public void f() {
        String url = "http://api.sap.ucarinc.com/ucarsap/getCheckInfoList";
        HashMap<String, String> parametersMap = new HashMap<>();
        parametersMap.put("employeeCode", "10005598");
        parametersMap.put("beginDate", "2021-02-01");
        parametersMap.put("endDate", "2021-02-28");
        parametersMap.put("tdsourcetag", "s_pctim_aiomsg");
        get(url, parametersMap);

    }
}