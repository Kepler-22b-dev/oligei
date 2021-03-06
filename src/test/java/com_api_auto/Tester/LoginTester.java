package com_api_auto.Tester;

import com_api_auto.Util.ExcelUtil;
import com_api_auto.Util.HttpUtilForPost;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class LoginTester {

    @Test(dataProvider = "getDatas")
    public void login(String CaseID, String ApiId, String RequestData) throws IOException {
        String url = "http://test.lemonban.com/lmcanon_web_auto/mng/login.html";
        Map<String, String> parameterMap = new HashMap<>();
        parameterMap.put("nickname", "18612266860");
        parameterMap.put("password", "a123456");
        HttpUtilForPost.ToPostbyFormEntity(url, parameterMap);
    }

    /**
     * 创建一个数据提供者
     *
     * @return
     */
    @DataProvider()
    public static Object[][] getDatas() {
        Object[][] datas = ExcelUtil.readExcel("/ApiInfo.xlsx", 2, 2, 4, 1, 3);
        return datas;
    }

}
