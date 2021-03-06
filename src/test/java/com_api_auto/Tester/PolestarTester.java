package com_api_auto.Tester;

import com_api_auto.Util.HttpTools;
import com_api_auto.Util.HttpUtilForPost;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class PolestarTester {

    @Test
    public void login(){
        String PolestarloginUrl = "http://polestartoolspre.ucarinc.com/polestartools/user/login";
        Map<String,String> loginInfo = new HashMap<>();
        loginInfo.put("username","admin");
        loginInfo.put("password","123pwd321");
        try {
            HttpTools.post(PolestarloginUrl,loginInfo);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
