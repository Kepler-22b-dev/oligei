package com_api_auto.Tester;

import org.testng.annotations.Test;
import org.testng.annotations.DataProvider;

/**
 * 数据提供者测试类
 */
public class dataProvidersTester {
    @Test(dataProvider = "dp")
    public void f(Integer n, String s) {
    }

//    @DataProvider
//    public Object[][] dp() {
//        return new Object[][] {
//                new Object[] { 1, "a" },
//                new Object[] { 2, "b" },
//        };
//    }
}
