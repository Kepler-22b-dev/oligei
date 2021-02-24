package util;

import okhttp3.ConnectionPool;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import okio.Buffer;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * OkHttpClient.
 *
 * @author wujun
 * @since 2018/10/26
 */
public class OkHttpClientUtils {

    private static final Logger logger = LoggerFactory.getLogger(OkHttpClientUtils.class);

    private static OkHttpClient okHttpClient = null;

    /**
     * application/x-www-form-urlencoded utf-8
     */
    private static final String MEDIA_TYPE_FORM_UTF_8 =
            "application/x-www-form-urlencoded; charset=UTF-8";

    /**
     * application/jason utf-8
     */
    private static final String MEDIA_TYPE_JSON_UTF_8 = "application/json; charset=UTF-8";

    /**
     * text/xml utf-8
     */
    private static final String MEDIA_TYPE_XML_UTF_8 = "text/xml; charset=UTF-8";

    /**
     * USER_AGENT
     */
    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) " +
            "AppleWebKit/537.36 (KHTML, like Gecko) Chrome/76.0.3809.132 Safari/537.36";

    /**
     * ACCEPT
     */
    private static final String ACCEPT =
            "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9";

    /**
     * 根据map获取get请求参数
     *
     * @param queries
     * @return
     */
    protected static StringBuilder getQueryString(String url, Map<String, Object> queries) {
        StringBuilder params = new StringBuilder(url);
        if (queries != null && queries.keySet().size() > 0) {
            boolean firstFlag = true;
            for (Map.Entry<String, Object> entry : queries.entrySet()) {
                if (firstFlag) {
                    params.append("?");
                    firstFlag = false;
                } else {
                    params.append("&");
                }
                params.append(entry.getKey()).append("=").append(entry.getValue());
            }
        }
        return params;
    }

    private static X509TrustManager x509TrustManager() {
        return new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
            }

            @Override
            public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }
        };
    }

    /**
     * 对外提供的获取支持自签名的okhttp客户端
     *
     * @param certificate 自签名证书的输入流
     * @return 支持自签名的客户端
     */
    public static OkHttpClient getTrustClient(String certificate) {

        X509TrustManager trustManager;
        SSLSocketFactory sslSocketFactory;
        try {
            trustManager = trustManagerForCertificates(trustedCertificatesInputStream(certificate));
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, new TrustManager[]{trustManager}, null);
            sslSocketFactory = sslContext.getSocketFactory();
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        }

        return new OkHttpClient.Builder()
                .sslSocketFactory(sslSocketFactory, trustManager)
                //verify san or cn
                //.hostnameVerifier(new DefaultHostnameVerifier())
                .build();
    }

    public static InputStream trustedCertificatesInputStream(String cert) {

        return new Buffer()
                .writeUtf8(cert)
                .inputStream();
    }

    /**
     * 获去信任自签证书的trustManager
     *
     * @param in 自签证书输入流
     * @return 信任自签证书的trustManager
     * @throws GeneralSecurityException
     */
    private static X509TrustManager trustManagerForCertificates(InputStream in)
            throws GeneralSecurityException {
        CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
        Collection<? extends Certificate> certificates = certificateFactory.generateCertificates(in);
        if (certificates.isEmpty()) {
            throw new IllegalArgumentException("expected non-empty set of trusted certificates");
        }

        char[] password = "password".toCharArray(); // Any password will work.
        KeyStore keyStore = newEmptyKeyStore(password);
        int index = 0;
        for (Certificate certificate : certificates) {
            String certificateAlias = Integer.toString(index++);
            keyStore.setCertificateEntry(certificateAlias, certificate);
        }

        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(
                KeyManagerFactory.getDefaultAlgorithm());
        keyManagerFactory.init(keyStore, password);
        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(
                TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init(keyStore);
        TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
        if (trustManagers.length != 1 || !(trustManagers[0] instanceof X509TrustManager)) {
            throw new IllegalStateException("Unexpected default trust managers:"
                    + Arrays.toString(trustManagers));
        }
        return (X509TrustManager) trustManagers[0];
    }

    private static KeyStore newEmptyKeyStore(char[] password) throws GeneralSecurityException {
        try {
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            InputStream in = null; // By convention, 'null' creates an empty key store.
            keyStore.load(in, password);
            return keyStore;
        } catch (IOException e) {
            throw new AssertionError(e);
        }
    }

    private static SSLSocketFactory sslSocketFactory() {
        try {
            //信任任何链接
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, new TrustManager[]{x509TrustManager()}, new SecureRandom());
            return sslContext.getSocketFactory();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static ConnectionPool buildConnectionPool() {
        return new ConnectionPool(200, 5, TimeUnit.MINUTES);
    }

    public static OkHttpClient getOkHttpClient() {
        if (okHttpClient != null) {
            return okHttpClient;
        }
        logger.info("--->>>getOkHttpClient init OkHttpClient");
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.level(HttpLoggingInterceptor.Level.BODY);

        Interceptor defaultHeaerInterceptor = getDefaultHeaerInterceptor();

        okHttpClient = new OkHttpClient.Builder()
                .sslSocketFactory(sslSocketFactory(), x509TrustManager())
                .retryOnConnectionFailure(true)//是否开启缓存
                .connectionPool(buildConnectionPool())//连接池
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                //.addInterceptor(defaultHeaerInterceptor)
                .addInterceptor(loggingInterceptor)
                .build();
        return okHttpClient;
    }

    private static Interceptor getDefaultHeaerInterceptor() {
        Interceptor commonInterceptor = new Interceptor() {

            @NotNull
            @Override
            public Response intercept(@NotNull Chain chain) throws IOException {
                Request request = chain.request()
                        .newBuilder()
                        .addHeader("Accept", ACCEPT)
                        //.addHeader("Content-Type", MEDIA_TYPE_FORM_UTF_8)
                        .addHeader("Accept-Encoding", "gzip, deflate")
                        .addHeader("Accept-Language", "zh-CN,zh;q=0.9")
                        .addHeader("Cache-Control", "no-cache")
                        .addHeader("Connection", "keep-alive")
                        //.addHeader("Referer", 需要的url)
                        .build();
                // 新的请求,添加参数
                //Request newRequest = addParam(request);

                return chain.proceed(request);
            }

            /**
             * 添加公共参数
             *
             * @param oldRequest
             * @return
             */
            private Request addParam(Request oldRequest) {
                HttpUrl.Builder builder = oldRequest.url()
                        .newBuilder()
                        .setEncodedQueryParameter("xx", "xxx");

                Request newRequest = oldRequest.newBuilder()
                        .method(oldRequest.method(), oldRequest.body())
                        .url(builder.build())
                        .build();

                return newRequest;
            }
        };
        return commonInterceptor;
    }

    /**
     * 调用okhttp的newCall方法.
     *
     * @param request
     * @return
     */
    private static String execNewCall(Request request) {
        Response response = null;
        try {
            OkHttpClient okHttpClient = getOkHttpClient();
            response = okHttpClient.newCall(request).execute();
            int status = response.code();
            if (response.isSuccessful()) {
                return response.body().string();
            }
        } catch (Exception e) {
            logger.error("okhttp3 put error req:{} >> ex = {}", request.toString(), ExceptionUtils.getStackTrace(e));

        } finally {
            if (response != null) {
                response.close();
            }
        }
        return "";
    }

    private static String getUserAgent() {
        return USER_AGENT;
    }

    private static Request.Builder withBaseRequestBuilder() {
//        String traceId = WebContextHolder.getTraceId();
//        String traceId = UuidGeneratorUtils.getNextId();
//        logger.info("--->>>withBaseRequestBuilder traceId:{}", traceId);
        Request.Builder requestBuilder = new Request.Builder()
                .removeHeader("User-Agent")
                .addHeader("trace_id", traceId)
                .addHeader("User-Agent", getUserAgent());
        return requestBuilder;
    }

    /**
     * GET请求数据
     *
     * @param url 请求url
     * @return
     */
    public static String get(String url) {
        StringBuilder params = getQueryString(url, null);
        Request request = withBaseRequestBuilder().url(params.toString()).build();
        return execNewCall(request);
    }

    /**
     * GET请求数据
     *
     * @param url    请求的url
     * @param params 请求的参数，在浏览器？后面的数据
     * @return
     */
    public static String get(String url, Map<String, Object> params) {
        StringBuilder queryString = getQueryString(url, params);
        Request request = withBaseRequestBuilder().url(queryString.toString()).build();
        return execNewCall(request);
    }

    /**
     * POST请求发送FORM数据.
     *
     * @param url      请求url
     * @param formBody form数据格式
     * @return
     */
    public static String postFormBody(String url, Map<String, String> formBody) {
        FormBody.Builder builder = new FormBody.Builder();
        //添加参数
        if (formBody != null && formBody.keySet().size() > 0) {
            for (String formName : formBody.keySet()) {
                builder.add(formName, formBody.get(formName));
            }
        }
        Request request = withBaseRequestBuilder().url(url).post(builder.build()).build();
        return execNewCall(request);
    }

    /**
     * post
     *
     * @param url    请求的url
     * @param params post form 提交的参数
     * @return
     */
    public static String postFormParams(String url, Map<String, String> params) {
        FormBody.Builder builder = new FormBody.Builder();
        //添加参数
        if (params != null && params.keySet().size() > 0) {
            for (String key : params.keySet()) {
                builder.add(key, params.get(key));
            }
        }
        Request request = withBaseRequestBuilder().url(url).post(builder.build()).build();
        return execNewCall(request);
    }

    /**
     * POST请求发送JSON数据.
     * <p>{"name":"zhangsan","pwd":"123456"}</p>
     *
     * @param url  请求Url
     * @param json JSON数据格式
     */
    public static String postJsonBody(String url, String json) {
        RequestBody requestBody = RequestBody.create(json, MediaType.parse(MEDIA_TYPE_JSON_UTF_8));
        Request request = withBaseRequestBuilder().url(url).post(requestBody).build();
        return execNewCall(request);
    }

    /**
     * POST请求发送JSON数据
     *
     * @param url    请求Url
     * @param json   JSON数据格式
     * @param header 请求头设置
     */
    public static String postJsonBody(String url, String json, Map<String, String> header) {
        RequestBody requestBody = RequestBody.create(json, MediaType.parse(MEDIA_TYPE_JSON_UTF_8));
        Request.Builder post = withBaseRequestBuilder().url(url).post(requestBody);

        if (header != null && header.size() > 0) {
            header.entrySet().stream().forEach(t -> post.addHeader(t.getKey(), t.getValue()));
        }
        Request request = post.build();
        return execNewCall(request);
    }

    /**
     * POST请求发送XML数据.
     * <p><xml><username>zhangsan</username><pwd>123456</pwd></xml></p>
     *
     * @param url 请求Url
     * @param xml XML数据格式
     */
    public static String postXmlBody(String url, String xml) {
        RequestBody requestBody = RequestBody.create(xml, MediaType.parse(MEDIA_TYPE_XML_UTF_8));
        Request request = withBaseRequestBuilder().url(url).post(requestBody).build();
        return execNewCall(request);
    }

    public static void main(String[] args) {

        String url = "https://api.mch.weixin.qq.com/pay/orderquery";
        String requestXml = "<xml>\n" +
                "\t<appid><![CDATA[wx605913f0d745edb2]]></appid>\n" +
                "\t<attach><![CDATA[137957232784630050%2C1630275542969548801%2C3719542b-f57e-44b0" +
                "-bad7-fc946077a204]]></attach>\n" +
                "\t<body><![CDATA[金贝支付-102001080000000450]]></body>\n" +
                "\t<detail><![CDATA[开发商订单号-3719542b-f57e-44b0-bad7-fc946077a204]]></detail>\n" +
                "\t<mch_id><![CDATA[1480005102]]></mch_id>\n" +
                "\t<nonce_str><![CDATA[99575143697838410296]]></nonce_str>\n" +
                "\t<notify_url><![CDATA[http://w.frp.qinxi1992" +
                ".cn/rest/pay/notify/wxpay/137957232784630050/102001080000000450]]></notify_url" +
                ">\n" +
                "\t<out_trade_no><![CDATA[102001080000000450]]></out_trade_no>\n" +
                "\t<spbill_create_ip><![CDATA[10.12.57.206]]></spbill_create_ip>\n" +
                "\t<total_fee><![CDATA[1]]></total_fee>\n" +
                "\t<trade_type><![CDATA[APP]]></trade_type>\n" +
                "\t<sign>EC44E24F42592104D14C7DFA37B95E28</sign>\n" +
                "</xml>";

        /*try {
            //requestXml = new String(requestXml.getBytes("utf-8"), "ISO8859-1");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }*/

        String responseXml = OkHttpClientUtils.postXmlBody(url, requestXml);

        System.out.println(responseXml);
    }
}
