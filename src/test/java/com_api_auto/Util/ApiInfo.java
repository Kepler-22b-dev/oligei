package com_api_auto.Util;

public class ApiInfo {
    //ApiId(接口编号)
    public String ApiId;
    //ApiName（接口名称）
    public String ApiName;
    // Type（接口提交方式）
    public String Type;
    // URL（接口地址）
    public String URL;

    public String getApiId() {
        return ApiId;
    }

    public void setApiId(String apiId) {
        ApiId = apiId;
    }

    public String getApiName() {
        return ApiName;
    }

    public void setApiName(String apiName) {
        ApiName = apiName;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    @Override
    public String toString() {
        return "apiInfo{" +
                "ApiId='" + ApiId + '\'' +
                ", ApiName='" + ApiName + '\'' +
                ", Type='" + Type + '\'' +
                ", URL='" + URL + '\'' +
                '}';
    }

}
