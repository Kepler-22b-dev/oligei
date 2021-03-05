package com_api_auto.Util;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;

public class restClientUtil {

    public static void get(String url) {
        given().when().get(url).then().log().all();
    }

    @Test
    public void f() {
        String url = "https://fc3dd024-bbf2-4023-ab27-fcba2569c8ae.mock.pstmn.io";
        get(url);
    }
}
