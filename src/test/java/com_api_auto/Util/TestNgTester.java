package com_api_auto.Util;

import org.testng.annotations.*;

public class TestNgTester {

    @AfterSuite
    public void AfterSuite(){
        System.out.println("AfterSuite");
    }
    @BeforeSuite
    public void BeforeSuite(){
        System.out.println("BeforeSuite");
    }
    @BeforeTest
    public void BeforeTest(){
        System.out.println("BeforeTest");
    }
    @BeforeClass
    public void BeforeClass(){
        System.out.println("BeforeClass");
    }
    @BeforeGroups
    public void BeforeGroups(){
        System.out.println("BeforeGroups");
    }
    @BeforeMethod
    public void BeforeMethod(){
        System.out.println("BeforeMethod");
    }
}
