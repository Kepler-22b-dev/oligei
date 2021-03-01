package section01;

import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class HttpUtil {

    public void post() throws IOException {
        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            String url = "http://test.lemonban.com/lmcanon_web_auto/mvc/member/api/member/login";
            HttpPost post = new HttpPost(url);
            List<NameValuePair> parameters = new ArrayList<NameValuePair>();
            parameters.add(new BasicNameValuePair("nickname","18612266860"));
            parameters.add(new BasicNameValuePair("password","dc483e80a7a0bd9ef71d8cf973673924"));
            post.setEntity(new UrlEncodedFormEntity(parameters));
            CloseableHttpResponse httpResponse = httpClient.execute(post);
            Header[] headers = httpResponse.getAllHeaders();
            for(Header header:headers){
                System.out.println(header.getName()+":"+header.getValue());
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } finally {
        }
    }
    @Test
    public void f() throws IOException {
        post();
    }
}
