package com_api_auto.Tester;

import com.alibaba.fastjson.JSONObject;
import com_api_auto.Util.ApiUtil;
import com_api_auto.Util.ExcelUtil_v3;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import java.io.IOException;
import java.util.Map;
import static com_api_auto.Util.HttpTools.get;
import static com_api_auto.Util.HttpTools.post;

public class API_AUTO_Tester {

    @Test(dataProvider = "getDatas")
    public void login(String CaseID, String ApiId, String RequestData) throws IOException {
        String url = ApiUtil.getUrlByApiId(ApiId);
        Map<String, String> parameterMap = (Map<String, String>) JSONObject.parse(RequestData);
        String type = ApiUtil.getRequestTypeByApiId(ApiId);
        if ("post".equalsIgnoreCase(type)) {
            post(url, parameterMap);
        } else
            get(url, parameterMap);
    }

    @DataProvider()
    public static Object[][] getDatas() {
        Object[][] datas = ExcelUtil_v3.readExcel("/ApiInfo.xlsx", 2);
        return datas;
    }

}
