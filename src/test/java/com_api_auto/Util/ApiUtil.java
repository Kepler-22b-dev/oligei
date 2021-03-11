package com_api_auto.Util;

import com_api_auto.ApiInfo;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ApiUtil {

    //并发用ConcurrentHashMap；

    private static Map<String, ApiInfo> apiInfoMap = new ConcurrentHashMap<>();

    //合理使用资源
    static {
        Object[][] datas = ExcelUtil_v3.readExcel("/testcase/ApiInfo.xlsx", 1);
        for (Object[] data : datas) {
            ApiInfo apiInfo = new ApiInfo();
            String ApiId = data[0].toString();
            apiInfo.setApiId(ApiId);
            apiInfo.setCaseID(data[1].toString());
            apiInfo.setApiName(data[2].toString());
            apiInfo.setType(data[3].toString());
            apiInfo.setURL(data[4].toString());
            apiInfo.setRequestData(data[5].toString());
            apiInfoMap.put(data[0].toString(), apiInfo);
        }
    }

    public static String getUrlByApiId(String apiId) {
        return apiInfoMap.get(apiId).getURL();
    }

    public static String getRequestTypeByApiId(String apiId) {
        return apiInfoMap.get(apiId).getType();
    }


    public static void main(String[] args) {
        Set<String> keySet = apiInfoMap.keySet();
        for (String key : keySet) {
            System.out.println(key +"-->" +apiInfoMap.get(key));
        }
    }
}
