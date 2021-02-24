package section01;

import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.testng.annotations.Test;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class GetTester {

    public static void ToGet(String url, Map<String, String> parameterMap) {
        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            List<NameValuePair> parameters = new ArrayList<NameValuePair>();
            Set<String> keySet = parameterMap.keySet();
            for (String Key : keySet) {
                parameters.add(new BasicNameValuePair(Key, parameterMap.get(Key)));
            }
            StringBuilder stringBuilder = new StringBuilder();
            for(int i =0;i<parameters.size();i++){
                if (i==0){
                    stringBuilder.append("?");
                }else {
                    stringBuilder.append("&");
                }
            }

            System.out.println(url);
            HttpPost get = new HttpPost(url);
            CloseableHttpResponse httpResponse = httpClient.execute(get);
            String result = EntityUtils.toString(httpResponse.getEntity());
            System.out.println(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void ToPostbyFormEntity(String url, Map<String, String> parameterMap) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
//        String url = "http://test.lemonban.com/lmcanon_web_auto/mvc/member/api/member/login";
        HttpPost post = new HttpPost(url);
        List<NameValuePair> parameters = new ArrayList<NameValuePair>();
        Set<String> keySet = parameterMap.keySet();
        for (String Key : keySet) {
            parameters.add(new BasicNameValuePair(Key, parameterMap.get(Key)));
        }
        StringBuilder stringBuilder = new StringBuilder();
        for(int i =0;i<parameters.size();i++){
            if (i==0){
                stringBuilder.append("?");
            }else {
                stringBuilder.append("&");
            }
        }

        System.out.println(url);
        CloseableHttpResponse HttpResponse = httpClient.execute(post);
        Header[] allHeaders = HttpResponse.getAllHeaders();
        for (Header allHeader : allHeaders) {
            System.out.println(allHeader.getName() + ":" + allHeader.getValue());
        }
    }

    public static void ToPostbyJSON(String url, String JSONString) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost post = new HttpPost(url);
        StringEntity stringEntity = new StringEntity(JSONString);
        post.setEntity(stringEntity);
        CloseableHttpResponse HttpResponse = httpClient.execute(post);
        String result = EntityUtils.toString(HttpResponse.getEntity());
        System.out.println(result);
    }

    @Test
    public void f() {
        String url = "https://de9a913f-8435-4912-84ef-75d63a7e5ea5.mock.pstmn.io";
        Map<String, String> parameterMap = null;
        parameterMap.put("name", "gungun");
        parameterMap.put("project", "javaApiAutoDefault");
        ToGet(url, parameterMap);
    }


    @Test
    public void f2() throws IOException {
        String url = "https://177976bb-66ae-483b-8a26-c1943670c85a.mock.pstmn.io";
        String string = "{\"name\":\"google\"}";
        ToPostbyJSON(url, string);
    }
}
