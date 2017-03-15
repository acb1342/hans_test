package com.mobilepark.doit5.elcg.service;

import java.io.FileInputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.SSLContext;

import com.mobilepark.doit5.common.util.IOUtil;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import com.uangel.platform.log.TraceLog;

/**
 * Created by kodaji on 15. 9. 16.
 */
@Service
public class HttpClietServiceImpl implements HttpClientService {

	private Header[] resHeaders;
    private StatusLine statusLine;

    private KeyStore getKeyStore(String filename) {
        if (filename == null) return null;

        KeyStore trustStore = null;
        FileInputStream fin = null;
        try {
            fin = new FileInputStream(filename);
            trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            trustStore.load(fin, "nopassword".toCharArray());
        } catch (Exception e) {
            TraceLog.error(e.getMessage(), e);
        } finally {
            IOUtil.close(fin);
        }
        return trustStore;
    }

    private SSLConnectionSocketFactory getSSL() throws
            NoSuchAlgorithmException,
            KeyStoreException,
            KeyManagementException {

        SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(this.getKeyStore(null), new TrustSelfSignedStrategy()).build();
        SSLConnectionSocketFactory factory = new SSLConnectionSocketFactory(
                sslContext,
                new String[] {"TLSv1"},
                null,
                SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
        return factory;
    }
    private CloseableHttpClient getHttpClient(String url) throws
        NoSuchAlgorithmException,
        KeyStoreException,
        KeyManagementException {

        CloseableHttpClient httpClient = HttpClients.createDefault();
        if (url.indexOf("https") == 0) httpClient = HttpClients.custom().setSSLSocketFactory(this.getSSL()).build();

        return httpClient;
    }

    @Override
    public String httpDelete(String url, Header[] headers) {

        CloseableHttpClient httpClient = null;
        CloseableHttpResponse httpResponse = null;
        String response = "";
        try {
            HttpDelete httpDelete = new HttpDelete(url);
            httpDelete.setHeaders(headers);

            // Request LOG
            TraceLog.info("Request ----------------------------------------------------------------------------------");
            TraceLog.info(httpDelete.getRequestLine().toString());
            for (Header header : headers) {
                TraceLog.info(header.toString());
            }

            httpClient = this.getHttpClient(url);
            httpResponse = httpClient.execute(httpDelete);

            HttpEntity resEntity = httpResponse.getEntity();
            response = EntityUtils.toString(resEntity, "UTF-8");

            // Response LOG
            TraceLog.info("Response----------------------------------------------------------------------------------");
            TraceLog.info(httpResponse.getStatusLine().toString());
            resHeaders = httpResponse.getAllHeaders();
            for (Header header : resHeaders) {
                TraceLog.info(header.toString());
            }
            TraceLog.info("");
            TraceLog.info(response);
        } catch (Exception e) {
            TraceLog.error(e.getMessage(), e);
            response = "600";
        } finally {
            IOUtil.close(httpResponse);
            IOUtil.close(httpClient);
        }
        return response;
    }

    @Override
    public String httpGet(String url, Header[] headers) {

        CloseableHttpClient httpClient = null;
        CloseableHttpResponse httpResponse = null;
        String response = "";
        try {
            HttpGet httpGet = new HttpGet(url);
            httpGet.setHeaders(headers);

            // Request LOG
            TraceLog.info("Request ----------------------------------------------------------------------------------");
            TraceLog.info(httpGet.getRequestLine().toString());
            for (Header header : headers) {
                TraceLog.info(header.toString());
            }

            httpClient = this.getHttpClient(url);
            httpResponse = httpClient.execute(httpGet);

            HttpEntity resEntity = httpResponse.getEntity();
            response = EntityUtils.toString(resEntity, "UTF-8");

            // Response LOG
            TraceLog.info("Response----------------------------------------------------------------------------------");
            TraceLog.info(httpResponse.getStatusLine().toString());
            resHeaders = httpResponse.getAllHeaders();
            for (Header header : resHeaders) {
                TraceLog.info(header.toString());
            }
            TraceLog.info("");
            TraceLog.info(response);
        } catch (Exception e) {
            TraceLog.error(e.getMessage(), e);
            response = "600";
        } finally {
            IOUtil.close(httpResponse);
            IOUtil.close(httpClient);
        }
        return response;
    }

    @Override
    public String httpPost(String LOG_PREFIX, String url, Header[] headers, String body) {

        CloseableHttpClient httpClient = null;
        CloseableHttpResponse httpResponse = null;
        String response = "";
        try {
            StringEntity reqEntity = new StringEntity(body, "UTF-8");
            reqEntity.setChunked(true);

            HttpPost httpPost = new HttpPost(url);
            httpPost.setHeaders(headers);
            httpPost.setEntity(reqEntity);

            String logPrefix = "[scss->things]" + LOG_PREFIX;
            // Request LOG
            TraceLog.info(logPrefix + httpPost.getRequestLine().toString());
            for (Header header : headers) {
                TraceLog.info(logPrefix + header.toString());
            }
            TraceLog.info(logPrefix);
            TraceLog.info(logPrefix + EntityUtils.toString(reqEntity), "UTF-8");

            httpClient = this.getHttpClient(url);
            httpResponse = httpClient.execute(httpPost);

            statusLine = httpResponse.getStatusLine();

            HttpEntity resEntity = httpResponse.getEntity();
            response = EntityUtils.toString(resEntity, "UTF-8");

            // Response LOG
            logPrefix = "[things->scss]" + LOG_PREFIX;
            TraceLog.info(logPrefix + httpResponse.getStatusLine().toString());
            resHeaders = httpResponse.getAllHeaders();
            for (Header header : resHeaders) {
                TraceLog.info(logPrefix + header.toString());
            }
            TraceLog.info(logPrefix);
            TraceLog.info(logPrefix + response);
        } catch (Exception e) {
            TraceLog.error(e.getMessage(), e);
            response = "600";
        } finally {
            IOUtil.close(httpResponse);
            IOUtil.close(httpClient);
        }
        return response;
    }
    @Override
    public String httpPut(String LOG_PREFIX, String url, Header[] headers, String body) {

        CloseableHttpClient httpClient = null;
        CloseableHttpResponse httpResponse = null;
        String response = "";
        try {
            StringEntity reqEntity = new StringEntity(body, "UTF-8");
            reqEntity.setChunked(true);

            HttpPut httpPut = new HttpPut(url);
            httpPut.setHeaders(headers);
            httpPut.setEntity(reqEntity);

            // Request LOG
            String logPrefix = "[scss->things]" + LOG_PREFIX;
            TraceLog.info(logPrefix + httpPut.getRequestLine().toString());
            for (Header header : headers) {
                TraceLog.info(logPrefix + header.toString());
            }
            TraceLog.info(logPrefix);
            TraceLog.info(logPrefix + EntityUtils.toString(reqEntity), "UTF-8");

            httpClient = this.getHttpClient(url);
            httpResponse = httpClient.execute(httpPut);

            statusLine = httpResponse.getStatusLine();

            HttpEntity resEntity = httpResponse.getEntity();
            response = EntityUtils.toString(resEntity, "UTF-8");

            // Response LOG
            logPrefix = "[things->scss]" + LOG_PREFIX;
            TraceLog.info(logPrefix + httpResponse.getStatusLine().toString());
            resHeaders = httpResponse.getAllHeaders();
            for (Header header : resHeaders) {
                TraceLog.info(logPrefix + header.toString());
            }
            TraceLog.info(logPrefix);
            TraceLog.info(logPrefix + response);
        } catch (Exception e) {
            TraceLog.error(e.getMessage(), e);
            response = "600";
        } finally {
            IOUtil.close(httpResponse);
            IOUtil.close(httpClient);
        }
        return response;
    }
    @Override
    public StatusLine getStatusLine() {
        return statusLine;
    }
    @Override
    public Header getHeader(String name) {
        for (Header header : resHeaders) {
            if (header.getName() == null) continue;
            if (header.getName().equals(name)) return header;
        }
        return null;
    }
}
