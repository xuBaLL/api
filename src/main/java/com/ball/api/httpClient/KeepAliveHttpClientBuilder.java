package com.ball.api.httpClient;

import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

import javax.net.ssl.SSLContext;
import java.security.NoSuchAlgorithmException;

public class KeepAliveHttpClientBuilder {

    private static HttpClient httpClient;

    /**
     * 获取http长连接
     */
    public synchronized HttpClient getKeepAliveHttpClient() {
        if (httpClient == null) {
            LayeredConnectionSocketFactory sslsf = null;
            try {
                sslsf = new SSLConnectionSocketFactory(SSLContext.getDefault());
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }

            Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder
                    .<ConnectionSocketFactory>create().register("https", sslsf)
                    .register("http", new PlainConnectionSocketFactory()).build();
            PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
            cm.setMaxTotal(HttpClientConstant.MAX_TOTAL);
            cm.setDefaultMaxPerRoute(HttpClientConstant.MAX_CONN_PER_ROUTE);

            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectTimeout(HttpClientConstant.CONNECT_TIMEOUT)
                    .setSocketTimeout(HttpClientConstant.SOCKET_TIMEOUT).build();
            // 创建连接
            httpClient = HttpClients.custom().setDefaultRequestConfig(requestConfig).setConnectionManager(cm).build();
        }

        return httpClient;
    }
}