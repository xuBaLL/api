package com.ypjtech.modules.utils.httpClient;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;

public class HttpClientUtil {

    private static Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);
    /**
     * 通过post方式调用http接口
     * @param url     url路径
     * @param reqParam    json格式的参数
     * @param retryNum     重发次数
     * @return
     * @throws Exception
     */
    public static String sendPostRequest(String url, String reqParam, int retryNum) {
        //声明返回结果
        String result = "";
        HttpEntity httpEntity = null;
        HttpResponse httpResponse = null;
        HttpClient httpClient = null;
        try {
            // 创建连接
            httpClient = HttpClientFactory.getInstance().getHttpClient();
            // 设置请求头和报文
            HttpPost httpPost = HttpClientFactory.getInstance().httpPost(url);
            Header header=new BasicHeader("Accept-Encoding",null);
            httpPost.setHeader(header);

            // 设置报文和通讯格式
            if(reqParam!=null){
                StringEntity stringEntity = new StringEntity(reqParam,HttpClientConstant.UTF8_ENCODE);
                stringEntity.setContentEncoding(HttpClientConstant.UTF8_ENCODE);
                stringEntity.setContentType(HttpClientConstant.APPLICATION_JSON);
                httpPost.setEntity(stringEntity);

            }
            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectTimeout(30 * 1000) // 设置连接超时时间
                    .setConnectionRequestTimeout(30 * 1000) // 设置请求超时时间
                    .setSocketTimeout(30 * 1000) // 设置数据传输超时时间
                    .build();
            httpPost.setConfig(requestConfig);
            logger.info("请求{}接口的参数为{}",url,reqParam);
            //执行发送，获取相应结果
            httpResponse = httpClient.execute(httpPost);
            httpEntity= httpResponse.getEntity();
            result = EntityUtils.toString(httpEntity);
        } catch (Exception e) {
            logger.error("请求{}接口出现异常",url,e);
            if (retryNum > 0) {
                logger.info("请求{}出现异常:{}，进行重发。进行第{}次重发",url,e.getMessage(),(HttpClientConstant.REQ_TIMES-retryNum +1));
                result = sendPostRequest(url, reqParam, retryNum - 1);
                if (result != null && !"".equals(result)) {
                    return result;
                }
            }
        }finally {
            try {
                EntityUtils.consume(httpEntity);
            } catch (IOException e) {
                logger.error("http请求释放资源异常",e);
            }
        }
        return result;
    }


    /**
     * 通过post方式调用http接口
     * @param url     url路径
     * @param reqBody    json格式的参数
     * @param retryNum     重发次数
     * @return
     * @throws Exception
     */
    public static String sendPostRequest(String url, String authorization, String reqBody, int retryNum) {
        //声明返回结果
        String result = "";
        HttpEntity httpEntity = null;
        HttpResponse httpResponse = null;
        HttpClient httpClient = null;
        try {
            // 创建连接
            httpClient = HttpClientFactory.getInstance().getHttpClient();
            // 设置请求头和报文
            HttpPost httpPost = HttpClientFactory.getInstance().httpPost(url);
            Header header=new BasicHeader("Accept-Encoding",null);
            httpPost.setHeader(header);
            if(StringUtils.isNotEmpty(authorization)){
                httpPost.addHeader("Authorization",authorization);
            }
            // 设置报文和通讯格式
            if(reqBody!=null){
                StringEntity stringEntity = new StringEntity(reqBody,HttpClientConstant.UTF8_ENCODE);
                stringEntity.setContentEncoding(HttpClientConstant.UTF8_ENCODE);
                stringEntity.setContentType(HttpClientConstant.APPLICATION_JSON);
                httpPost.setEntity(stringEntity);

            }
            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectTimeout(30 * 1000) // 设置连接超时时间
                    .setConnectionRequestTimeout(30 * 1000) // 设置请求超时时间
                    .setSocketTimeout(30 * 1000) // 设置数据传输超时时间
                    .build();
            httpPost.setConfig(requestConfig);
            logger.info("请求{}接口的参数为{}",url,reqBody);
            //执行发送，获取相应结果
            httpResponse = httpClient.execute(httpPost);
            httpEntity= httpResponse.getEntity();
            result = EntityUtils.toString(httpEntity);
        } catch (Exception e) {
            logger.error("请求{}接口出现异常",url,e);
            if (retryNum > 0) {
                logger.info("请求{}出现异常:{}，进行重发。进行第{}次重发",url,e.getMessage(),(HttpClientConstant.REQ_TIMES-retryNum +1));
                result = sendPostRequest(url, authorization,reqBody, retryNum - 1);
                if (result != null && !"".equals(result)) {
                    return result;
                }
            }
        }finally {
            try {
                EntityUtils.consume(httpEntity);
            } catch (IOException e) {
                logger.error("http请求释放资源异常",e);
            }
        }
        return result;
    }


    public  static String sendGetRequest(String url, int retryNum)  {
        //声明返回结果
        String result = "";
        HttpEntity httpEntity = null;
        HttpResponse httpResponse = null;
        HttpClient httpClient = null;
        try {
            httpClient = HttpClientFactory.getInstance().getHttpClient();
            HttpGet httpGet = HttpClientFactory.getInstance().httpGet(url,null);
            logger.info("请求{}接口的参数为{}",url);

            // 通过client调用execute方法，得到我们的执行结果就是一个response，所有的数据都封装在response里面了

            httpResponse = httpClient.execute(httpGet);
            httpEntity = httpResponse.getEntity();
            // 通过EntityUtils 来将我们的数据转换成字符串
            result = EntityUtils.toString(httpEntity,"UTF-8");
        } catch (Exception e) {
            logger.error("请求{}接口出现异常",url,e);
            if (retryNum > 0) {
                logger.info("请求{}出现异常:{}，进行重发。进行第{}次重发",url,e.getMessage(),(HttpClientConstant.REQ_TIMES-retryNum +1));
                result = sendGetRequest(url,  retryNum - 1);
                if (result != null && !"".equals(result)) {
                    return result;
                }
            }
        }finally {
            try {
                EntityUtils.consume(httpEntity);
            } catch (IOException e) {
                logger.error("http请求释放资源异常",e);
            }
        }
        return result;
    }

    public  static String sendGetRequest(String url, HashMap map, int retryNum)  {
        //声明返回结果
        String result = "";
        HttpEntity httpEntity = null;
        HttpResponse httpResponse = null;
        HttpClient httpClient = null;
        try {
            httpClient = HttpClientFactory.getInstance().getHttpClient();
            HttpGet httpGet = HttpClientFactory.getInstance().httpGet(url,map);
//            logger.info("请求{}接口的参数为{}",url);

            // 通过client调用execute方法，得到我们的执行结果就是一个response，所有的数据都封装在response里面了

            httpResponse = httpClient.execute(httpGet);
            httpEntity = httpResponse.getEntity();
            // 通过EntityUtils 来将我们的数据转换成字符串
            result = EntityUtils.toString(httpEntity,"UTF-8");
        } catch (Exception e) {
            logger.error("请求{}接口出现异常",url,e);
            if (retryNum > 0) {
                logger.info("请求{}出现异常:{}，进行重发。进行第{}次重发",url,e.getMessage(),(HttpClientConstant.REQ_TIMES-retryNum +1));
                result = sendGetRequest(url, map, retryNum - 1);
                if (result != null && !"".equals(result)) {
                    return result;
                }
            }
        }finally {
            try {
                EntityUtils.consume(httpEntity);
            } catch (IOException e) {
                logger.error("http请求释放资源异常",e);
            }
        }
        return result;
    }

    /**
     * 对象形式的参数
     * @param url
     * @param t
     * @param retryNum
     * @param <T>
     * @return
     */
    public  static <T> String sendGetRequest(String url, T t, int retryNum)  {
        //声明返回结果
        String result = "";
        HttpEntity httpEntity = null;
        HttpResponse httpResponse = null;
        HttpClient httpClient = null;
        try {
            httpClient = HttpClientFactory.getInstance().getHttpClient();
            HttpGet httpGet = HttpClientFactory.getInstance().httpGet(url,t);
            httpResponse = httpClient.execute(httpGet);
            httpEntity = httpResponse.getEntity();
            // 通过EntityUtils 来将我们的数据转换成字符串
            result = EntityUtils.toString(httpEntity,"UTF-8");
        } catch (Exception e) {
            logger.error("请求{}接口出现异常",url,e);
            if (retryNum > 0) {
                logger.info("请求{}出现异常:{}，进行重发。进行第{}次重发",url,e.getMessage(),(HttpClientConstant.REQ_TIMES-retryNum +1));
                result = sendGetRequest(url, t, retryNum - 1);
                if (result != null && !"".equals(result)) {
                    return result;
                }
            }
        }finally {
            try {
                EntityUtils.consume(httpEntity);
            } catch (IOException e) {
                logger.error("http请求释放资源异常",e);
            }
        }
        return result;
    }


    /**
     * 通过post方式调用http接口
     * @param url     url路径
     * @param map    map格式的参数
     * @param retryNum     重发次数
     * @return
     * @throws Exception
     */
    public static String sendPostRequest(String url, Map<String, String> map, int retryNum) {
        //声明返回结果
        String result = "";
        HttpEntity httpEntity = null;
        UrlEncodedFormEntity entity = null;
        HttpResponse httpResponse = null;
        HttpClient httpClient = null;
        try {
            // 创建连接
            httpClient = HttpClientFactory.getInstance().getHttpClient();
            // 设置请求头和报文
            HttpPost httpPost = HttpClientFactory.getInstance().httpPost(url);
            //设置参数
            List<NameValuePair> list = new ArrayList<NameValuePair>();
            Iterator iterator = map.entrySet().iterator();
            while(iterator.hasNext()){
                Map.Entry<String, String> elem = (Map.Entry<String, String>) iterator.next();
                list.add(new BasicNameValuePair(elem.getKey(),elem.getValue()));
            }
            entity = new UrlEncodedFormEntity(list,HttpClientConstant.UTF8_ENCODE);
            httpPost.setEntity(entity);
            logger.info("请求{}接口的参数为{}",url,map);
            httpResponse = httpClient.execute(httpPost);
            httpEntity= httpResponse.getEntity();
            result = EntityUtils.toString(httpEntity);
        } catch (Exception e) {
            logger.error("请求{}接口出现异常",url,e);
            if (retryNum > 0) {
                logger.info("请求{}出现异常:{}，进行重发。进行第{}次重发",url,e.getMessage(),(HttpClientConstant.REQ_TIMES-retryNum +1));
                result = sendPostRequest(url, map, retryNum - 1);
                if (result != null && !"".equals(result)) {
                    return result;
                }
            }
        }finally {
            try {
                EntityUtils.consume(httpEntity);
            } catch (IOException e) {
                logger.error("http请求释放资源异常",e);
            }
        }
        return result;

    }


}