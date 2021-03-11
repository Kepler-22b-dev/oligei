package com_api_auto.base;

import com_api_auto.Util.ExcelUtil_v3;
import org.testng.annotations.AfterSuite;

public class BaseTester {
    @AfterSuite
    public void afterSuit(){
        System.out.println("========================");
        ExcelUtil_v3.batchWriteData("src/test/resources/testcase/ApiInfo.xlsx","target/ApiInfo.xlsx",1);
    }
}
