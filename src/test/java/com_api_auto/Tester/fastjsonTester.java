package com_api_auto.Tester;

import com.alibaba.fastjson.JSONObject;

import java.util.Map;

public class fastjsonTester {

    public static void main(String[] args) {
        String stringjson = "{\"loginname\":\"tanxian\",\"password\":\"a123456\"} ";
        Map<String,String> jsonMap = (Map<String, String>) JSONObject.parse(stringjson);
            for (String key : jsonMap.keySet()) {
                System.out.println(key+":"+jsonMap.get(key));
        }
    }
}
