package com_api_auto.Tester;

import com.alibaba.fastjson.JSONObject;
import com_api_auto.Util.ApiUtil;
import com_api_auto.Util.HttpTools;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Map;

import static com_api_auto.Util.ExcelUtil_v3.*;

public class API_AUTO_Tester {

    @Test(dataProvider = "getDatas")
    public void login(String ApiId, String CaseID, String ApiName, String Type, String URL, String RequestData) throws IOException {
        String Url = ApiUtil.getUrlByApiId(ApiId);
        Map<String, String> parameterMap = (Map<String, String>) JSONObject.parse(RequestData);
        String type = ApiUtil.getRequestTypeByApiId(ApiId);
        String result = " ";
        if ("post".equalsIgnoreCase(type)) {
            result = HttpTools.post(URL, parameterMap);
        } else {
            result = HttpTools.get(URL, parameterMap);
        }
        System.out.println(result);
        WriteData("target/test-classes/ApiInfo.xlsx",1,CaseID,7,result);
    }

    @DataProvider()
    public static Object[] getDatas() {
        Object[][] datas = readExcel("/testcase/ApiInfo.xlsx", 1);
        return datas;
    }

}
