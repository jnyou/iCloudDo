package org.jnyou.anoteinventoryservice.tools;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.apache.http.ParseException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.apache.poi.util.StringUtil;

import java.io.IOException;
import java.nio.charset.Charset;

/**
 * @author jnyou
 */
public class HttpClientUtil {

    /**
     * httpclient读取内容时使用的字符集
     */
    public static final String CONTENT_CHARSET = "utf-8";
    public static final Charset UTF_8 = Charset.forName("UTF-8");
    public static final String CONTENT_TYPE_JSON_CHARSET = "application/json;charset=" + CONTENT_CHARSET;
    public static final Integer CONNECTTIMEOUT = 5000; //请求超时时间

    /**
     * 请求前置服务工具类
     * 默认超时时间5秒
     *
     * @param url
     * @param parameters
     * @return
     * @throws ParseException
     * @throws IOException
     */
    public static String postJson(String url, String parameters)
            throws ParseException, IOException {

        return postJson(url, parameters, CONNECTTIMEOUT);
    }

    /**
     * @param url
     * @param parameters
     * @param timeout
     * @return
     * @throws ParseException
     * @throws IOException
     */
    public static String postJson(String url, String parameters, int timeout)
            throws ParseException, IOException {
        //logger.info("地址url:[" + url + "]传入的参数串：[" + parameters + "]");
        CloseableHttpClient client = buildHttpClient(false);
        CloseableHttpResponse response = null;
        String body = null;
        try {
            HttpPost method = new HttpPost(url);
            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectTimeout(timeout) //连接超时时间
                    .setSocketTimeout(timeout)  //获取数据的超时时间
                    .build();
            if (StringUtils.isNotEmpty(parameters)) {
                // 添加参数
                StringEntity entity = new StringEntity(parameters, CONTENT_CHARSET);
                entity.setContentEncoding(CONTENT_CHARSET);
                entity.setContentType(CONTENT_TYPE_JSON_CHARSET);
                method.setEntity(entity);
                method.setConfig(requestConfig);//设置请求超时时间
                //设置编码
                response = client.execute(method);
                int statusCode = response.getStatusLine().getStatusCode();
                body = EntityUtils.toString(response.getEntity(), UTF_8);
                if (statusCode != HttpStatus.SC_OK) {
                    throw new RuntimeException("http响应不是200!响应报文:" + body);
                }
            }
        } finally {
            if (client != null) {
                client.close();
            }
            if (response != null) {
                response.close();
            }
        }
        //logger.info("地址url:[" + url + "]返回的参数串：[" + body + "]");
        return body;
    }

    /**
     * @param isMultiThread
     * @return
     */
    private static CloseableHttpClient buildHttpClient(boolean isMultiThread) {
        CloseableHttpClient client;
        if (isMultiThread)
            client = HttpClientBuilder
                    .create()
                    .setConnectionManager(
                            new PoolingHttpClientConnectionManager()).build();
        else
            client = HttpClientBuilder.create().build();
        return client;
    }
}