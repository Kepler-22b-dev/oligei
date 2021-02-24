package util;

public class HttpResult {
    // 响应的状态码
    private int code;
    // 响应的响应体
    private String body;

    public HttpResult(int statusCode, String toString) {
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "HttpResult{" +
                "code=" + code +
                ", body='" + body + '\'' +
                '}';
    }
}
