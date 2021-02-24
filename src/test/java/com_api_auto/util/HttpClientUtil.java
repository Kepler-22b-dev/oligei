package util;

import com.alibaba.fastjson.JSON;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpClientUtil {
    private static CloseableHttpClient httpClient = HttpClients.createDefault();

    /**
     * 带参数的get请求
     */
    public static HttpResult doGet(String url, Map<String, Object> map) throws Exception
    {

        // 声明URIBuilder
        URIBuilder uriBuilder = new URIBuilder(url);

        // 判断参数map是否为非空
        if (map != null)
        {
            // 遍历参数
            for (Map.Entry<String, Object> entry : map.entrySet())
            {
                // 设置参数
                uriBuilder.setParameter(entry.getKey(), entry.getValue().toString());
            }
        }

        // 2 创建httpGet对象，相当于设置url请求地址
        HttpGet httpGet = new HttpGet(uriBuilder.build());

        // 3 使用HttpClient执行httpGet
        CloseableHttpResponse response = httpClient.execute(httpGet);

        // 4 解析结果，封装返回对象httpResult，相当于显示相应的结果
        // 状态码
        // response.getStatusLine().getStatusCode();
        // 响应体，字符串，如果response.getEntity()为空，下面这个代码会报错,所以解析之前要做非空的判断
        // EntityUtils.toString(response.getEntity(), "UTF-8");
        HttpResult httpResult = null;
        // 解析数据封装HttpResult
        if (response.getEntity() != null)
        {
            httpResult = new HttpResult(response.getStatusLine().getStatusCode(),
                    EntityUtils.toString(response.getEntity(), "UTF-8"));

        }
        else
        {
            httpResult = new HttpResult(response.getStatusLine().getStatusCode(), "");
        }

        // 返回
        return httpResult;
    }

    /**
     * 不带参数的get请求
     */
    public static HttpResult doGet(String url) throws Exception
    {
        HttpResult httpResult = doGet(url, null);
        return httpResult;
    }

    /**
     * 带参数的post请求
     */
    public static HttpResult doPost(String url, Map<String, Object> reqMap, Map<String, String> headersMap)
            throws Exception
    {
        // 声明httpPost请求
        HttpPost httpPost = new HttpPost(url);
        for (Map.Entry<String, String> entry : headersMap.entrySet())
            httpPost.addHeader(entry.getKey(), entry.getValue());

        // 判断map不为空
        if (reqMap != null)
        {

            // 创建form表单对象
            StringEntity formEntity = new StringEntity(JSON.toJSONString(reqMap), "UTF-8");

            // 把表单对象设置到httpPost中
            httpPost.setEntity(formEntity);
        }

        // 使用HttpClient发起请求，返回response
        CloseableHttpResponse response = httpClient.execute(httpPost);

        // 解析response封装返回对象httpResult
        HttpResult httpResult = null;
        if (response.getEntity() != null)
        {
            httpResult = new HttpResult(response.getStatusLine().getStatusCode(),
                    EntityUtils.toString(response.getEntity(), "UTF-8"));
        }
        else
        {
            httpResult = new HttpResult(response.getStatusLine().getStatusCode(), "");
        }

        // 返回结果
        return httpResult;
    }

    /**
     * 不带参数的post请求
     */
    public static HttpResult doPost(String url) throws Exception
    {
        HttpResult httpResult = doPost(url, null, null);
        return httpResult;
    }

    /**
     * 带参数的Put请求
     */
    public static HttpResult doPut(String url, Map<String, Object> map) throws Exception
    {
        // 声明httpPost请求
        HttpPut httpPut = new HttpPut(url);

        // 判断map不为空
        if (map != null)
        {
            // 声明存放参数的List集合
            List<NameValuePair> params = new ArrayList<NameValuePair>();

            // 遍历map，设置参数到list中
            for (Map.Entry<String, Object> entry : map.entrySet())
            {
                params.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
            }

            // 创建form表单对象
            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(params, "UTF-8");

            // 把表单对象设置到httpPost中
            httpPut.setEntity(formEntity);
        }

        // 使用HttpClient发起请求，返回response
        CloseableHttpResponse response = httpClient.execute(httpPut);

        // 解析response封装返回对象httpResult
        HttpResult httpResult = null;
        if (response.getEntity() != null)
        {
            httpResult = new HttpResult(response.getStatusLine().getStatusCode(),
                    EntityUtils.toString(response.getEntity(), "UTF-8"));
        }
        else
        {
            httpResult = new HttpResult(response.getStatusLine().getStatusCode(), "");
        }

        // 返回结果
        return httpResult;
    }

    /**
     * 带参数的Delete请求
     */
    public static HttpResult doDelete(String url, Map<String, Object> map) throws Exception
    {

        // 声明URIBuilder
        URIBuilder uriBuilder = new URIBuilder(url);

        // 判断参数map是否为非空
        if (map != null)
        {
            // 遍历参数
            for (Map.Entry<String, Object> entry : map.entrySet())
            {
                // 设置参数
                uriBuilder.setParameter(entry.getKey(), entry.getValue().toString());
            }
        }

        // 2 创建httpGet对象，相当于设置url请求地址
        HttpDelete httpDelete = new HttpDelete(uriBuilder.build());

        // 3 使用HttpClient执行httpGet，相当于按回车，发起请求
        CloseableHttpResponse response = httpClient.execute(httpDelete);

        // 4 解析结果，封装返回对象httpResult，相当于显示相应的结果
        // 状态码
        // response.getStatusLine().getStatusCode();
        // 响应体，字符串，如果response.getEntity()为空，下面这个代码会报错,所以解析之前要做非空的判断
        // EntityUtils.toString(response.getEntity(), "UTF-8");
        HttpResult httpResult = null;
        // 解析数据封装HttpResult
        if (response.getEntity() != null)
        {
            httpResult = new HttpResult(response.getStatusLine().getStatusCode(),
                    EntityUtils.toString(response.getEntity(), "UTF-8"));
        }
        else
        {
            httpResult = new HttpResult(response.getStatusLine().getStatusCode(), "");
        }

        // 返回
        return httpResult;
    }

    /**
     * 返回常用请求头
     * @return
     */
    public static Map<String, String> commentHeader()
    {
        Map<String, String> map = new HashMap<>();
        map.put("Content-Type", "application/json");
        return map;
    }
}
