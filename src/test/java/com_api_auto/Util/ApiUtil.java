package com_api_auto.Util;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ApiUtil {

    private static Map<String, ApiInfo> apiInfoMap = new HashMap<>();

    //合理使用资源
    static {
        Object[][] datas = ExcelUtil_v3.readExcel("/testcase/ApiInfo.xlsx", 1);
        for (Object[] data : datas) {
            ApiInfo apiInfo = new ApiInfo();
            String ApiId = data[0].toString();
            apiInfo.setApiId(ApiId);
            apiInfo.setApiName(data[2].toString());
            apiInfo.setApiName(data[1].toString());
            apiInfo.setType(data[3].toString());
            apiInfo.setURL(data[4].toString());
            apiInfo.setRequestData(data[5].toString());

//            System.out.println(apiInfo);
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
