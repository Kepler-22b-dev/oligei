package com_api_auto.Tester;

import com.alibaba.fastjson.JSONObject;
import com_api_auto.Util.ExcelUtil_v3;
import com_api_auto.Util.HttpTools;
import com_api_auto.Util.HttpUtilForPost;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class API_AUTO_Tester {

    @Test(dataProvider = "getDatas")
    public void login(String CaseID, String ApiId, String RequestData) throws IOException {
        String url = "http://test.lemonban.com/lmcanon_web_auto/mng/login.html";
        Map<String, String> parameterMap = (Map<String, String>) JSONObject.parse(RequestData);
        HttpTools.post(url, parameterMap);
    }

    @DataProvider()
    public static Object[][] getDatas() {
        Object[][] datas = ExcelUtil_v3.readExcel("/ApiInfo.xlsx", 2);
        return datas;
    }

}
