package common.util;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * HttpClientUtils������
 */
public class HttpClientUtils {

    /**
     * ִ��get����
     *
     * @param url
     * @param params
     * @return
     */
    public static String doGet(String url, Map<String, String> params) {
        // ����httpclient
        CloseableHttpClient httpClient = HttpClients.createDefault();

        String result = "";
        CloseableHttpResponse httpResponse = null;
        try {
            // ����uri
            URIBuilder builder = new URIBuilder(url);

            // ��������
            if (params != null) {
                for (String key : params.keySet()) {
                    builder.addParameter(key, params.get(key));
                }
            }

            URI uri = builder.build();

            // ����get����
            HttpGet httpGet = new HttpGet(uri);

            // ִ��
            httpResponse = httpClient.execute(httpGet);

            result = EntityUtils.toString(httpResponse.getEntity(), Charset.defaultCharset());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(httpClient, httpResponse);
        }
        return result;
    }

    /**
     * ִ��get����û�в���
     *
     * @param url
     * @return
     */
    public static String doGet(String url) {
        return doGet(url, null);
    }

    /**
     * ִ��post����
     *
     * @param url
     * @param params
     * @return
     */
    public static String doPost(String url, Map<String, String> params) {
        // ����httpclient
        CloseableHttpClient httpClient = HttpClients.createDefault();

        String result = "";
        CloseableHttpResponse httpResponse = null;
        try {
            // ��������
            HttpPost httpPost = new HttpPost(url);

            // ��������
            if (params != null) {
                List<NameValuePair> paramList = new ArrayList<NameValuePair>();
                for (String key : params.keySet()) {
                    paramList.add(new BasicNameValuePair(key, params.get(key)));
                }

                // ģ��form��entity
                HttpEntity formEntity = new UrlEncodedFormEntity(paramList, Charset.defaultCharset()); //ʹ��Ĭ���ַ���
                httpPost.setEntity(formEntity);
            }

            // ִ��
            httpResponse = httpClient.execute(httpPost);
            // ��ȡ���������صĽ��
            result = EntityUtils.toString(httpResponse.getEntity(), Charset.defaultCharset());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(httpClient, httpResponse);
        }
        return result;
    }

    /**
     * ִ��post����û�в���
     *
     * @param url
     * @return
     */
    public static String doPost(String url) {
        return doPost(url, null);
    }
    
 


    /**
     * ִ��post���󣬲���Ϊjson�ַ���
     *
     * @param url
     * @param jsonParam
     * @return
     */
    public static String doPostJsonParam(String url, String jsonParam) {
        // ����httpclient
        CloseableHttpClient httpClient = HttpClients.createDefault();

        String result = "";
        CloseableHttpResponse httpResponse = null;
        try {
            // ��������
            HttpPost httpPost = new HttpPost(url);

            if (null!=jsonParam&&"".equals(jsonParam)) {
                // ��������ʵ��
                StringEntity stringEntity = new StringEntity(jsonParam, ContentType.APPLICATION_JSON);
                httpPost.setEntity(stringEntity);
            }

            // ִ��
            httpResponse = httpClient.execute(httpPost);
            result = EntityUtils.toString(httpResponse.getEntity(), Charset.defaultCharset());

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(httpClient, httpResponse);
        }
        return result;
    }

    /**
     * �ر�httpClient��httpResponse
     *
     * @param httpClient
     * @param httpResponse
     */
    private static void close(CloseableHttpClient httpClient, CloseableHttpResponse httpResponse) {
        if (httpResponse != null) {
            try {
                httpResponse.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (httpClient != null) {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
