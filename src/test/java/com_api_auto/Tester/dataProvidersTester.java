package com_api_auto.Tester;

import org.testng.annotations.Test;
import org.testng.annotations.DataProvider;

import static com_api_auto.Tester.ExcelUtil.readExcel;

/**
 * 数据提供者测试类
 */
public class dataProvidersTester {
    @Test(dataProvider = "dp")
    public void f() {
        System.out.println();
    }

    @DataProvider
    public Object[] dp() {
        Object[][] datas = readExcel("/DataProvides.xlsx",
                1,
                2,
                8,
                1,
                5);
        for (Object[] data : datas) {
            for (Object datum : data) {
                System.out.print(datum + "  ");
            }

        }
        return new Object[0];
    }
    }
