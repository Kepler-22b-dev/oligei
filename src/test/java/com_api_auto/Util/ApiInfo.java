package com_api_auto.Util;

import java.security.PublicKey;

public class ApiInfo {
    //ApiId(接口编号)
    public String ApiId;
    //ApiName（接口名称）
    public String ApiName;
    // Type（接口提交方式）
    public String Type;
    // URL（接口地址）
    public String URL;

    @Override
    public String toString() {
        return "ApiInfo{" +
                "ApiId='" + ApiId + '\'' +
                ", ApiName='" + ApiName + '\'' +
                ", Type='" + Type + '\'' +
                ", URL='" + URL + '\'' +
                ", CaseID='" + CaseID + '\'' +
                ", RequestData='" + RequestData + '\'' +
                '}';
    }

    public String getCaseID() {
        return CaseID;
    }

    public void setCaseID(String caseID) {
        CaseID = caseID;
    }

    public String CaseID;

    public String getRequestData() {
        return RequestData;
    }

    public void setRequestData(String requestData) {
        RequestData = requestData;
    }

    public  String RequestData;

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

}
