package com.hkf.coffee.http;

import com.hkf.coffee.http.base.HttpParams;
import com.hkf.coffee.http.base.SSLContextUtil;
import com.hkf.coffee.jars.nohttp.NoHttp;
import com.hkf.coffee.jars.nohttp.OnResponseListener;
import com.hkf.coffee.jars.nohttp.Request;
import com.hkf.coffee.jars.nohttp.RequestMethod;
import com.hkf.coffee.jars.nohttp.RequestQueue;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;

/**
 * 提供访问网络快捷功能
 * Created by wanglei on 16/3/28.
 */
public class HttpManager {
    private static HttpManager geekHttp;
    private RequestQueue queue;

    public static HttpManager getHttp() {
        if (geekHttp == null) {
            synchronized (HttpManager.class) {
                if (geekHttp == null) {
                    geekHttp = new HttpManager();
                }
            }
        }
        return geekHttp;
    }

    public HttpManager() {
        this.queue = NoHttp.newRequestQueue();
    }

    /**
     * 获取请求队列
     *
     * @return
     */
    public RequestQueue getQueue() {
        return queue;
    }

    /**
     * http-GET请求   不带参数
     *
     * @param what
     * @param URL
     * @param responseListener
     */
    public void httpGet(int what, String URL, OnResponseListener responseListener) {
        Request<String> request = NoHttp.createStringRequest(URL, RequestMethod.GET);
        queue.add(what, request, responseListener);
    }

    /**
     * http-GET请求  带参数
     *
     * @param what
     * @param URL
     * @param params
     * @param responseListener
     */
    public void httpGet(int what, String URL, HttpParams params, OnResponseListener responseListener) {
        Request<String> request = NoHttp.createStringRequest(URL, RequestMethod.GET);
        params.handle(request);
        queue.add(what, request, responseListener);
    }


    /**
     * http-POST请求  不带参数
     *
     * @param what
     * @param URL
     * @param responseListener
     */
    public void httpPost(int what, String URL, OnResponseListener responseListener) {
        Request<String> request = NoHttp.createStringRequest(URL, RequestMethod.POST);
        queue.add(what, request, responseListener);
    }

    /**
     * http-POST请求  带参数
     *
     * @param what
     * @param URL
     * @param params
     * @param responseListener
     */
    public void httpPost(int what, String URL, HttpParams params, OnResponseListener responseListener) {
        Request<String> request = NoHttp.createStringRequest(URL, RequestMethod.POST);
        params.handle(request);
        queue.add(what, request, responseListener);
    }

    /**
     * https-GET请求   不带参数  有证书
     *
     * @param what
     * @param URL
     * @param cerName          //放在assets里的证书
     * @param responseListener
     */
    public void httpsGet(int what, String URL, String cerName, OnResponseListener responseListener) {
        Request<String> request = NoHttp.createStringRequest(URL, RequestMethod.GET);
        SSLContext sslContext = SSLContextUtil.getSSLContext(cerName);
        if (sslContext != null) {
            SSLSocketFactory socketFactory = sslContext.getSocketFactory();
            request.setSSLSocketFactory(socketFactory);
        }
        queue.add(what, request, responseListener);
    }

    /**
     * https-GET请求   不带参数  没证书
     *
     * @param what
     * @param URL
     * @param responseListener
     */
    public void httpsGet(int what, String URL, OnResponseListener responseListener) {
        Request<String> request = NoHttp.createStringRequest(URL, RequestMethod.GET);
        SSLContext sslContext = SSLContextUtil.getSLLContext();
        if (sslContext != null) {
            SSLSocketFactory socketFactory = sslContext.getSocketFactory();
            request.setSSLSocketFactory(socketFactory);
            request.setHostnameVerifier(SSLContextUtil.HOSTNAME_VERIFIER);
        }
        queue.add(what, request, responseListener);
    }

    /**
     * https-GET请求  带参数  有证书
     *
     * @param what
     * @param URL
     * @param params
     * @param cerName          //放在assets里的证书
     * @param responseListener
     */
    public void httpsGet(int what, String URL, HttpParams params, String cerName, OnResponseListener responseListener) {
        Request<String> request = NoHttp.createStringRequest(URL, RequestMethod.GET);
        SSLContext sslContext = SSLContextUtil.getSSLContext(cerName);
        if (sslContext != null) {
            SSLSocketFactory socketFactory = sslContext.getSocketFactory();
            request.setSSLSocketFactory(socketFactory);
        }
        params.handle(request);
        queue.add(what, request, responseListener);
    }

    /**
     * https-GET请求  带参数  没证书
     *
     * @param what
     * @param URL
     * @param params
     * @param responseListener
     */
    public void httpsGet(int what, String URL, HttpParams params, OnResponseListener responseListener) {
        Request<String> request = NoHttp.createStringRequest(URL, RequestMethod.GET);
        SSLContext sslContext = SSLContextUtil.getSLLContext();
        if (sslContext != null) {
            SSLSocketFactory socketFactory = sslContext.getSocketFactory();
            request.setSSLSocketFactory(socketFactory);
            request.setHostnameVerifier(SSLContextUtil.HOSTNAME_VERIFIER);
        }
        params.handle(request);
        queue.add(what, request, responseListener);
    }


    /**
     * https-POST请求  不带参数 有证书
     *
     * @param what
     * @param URL
     * @param cerName          //放在assets里的证书
     * @param responseListener
     */
    public void httpsPost(int what, String URL, String cerName, OnResponseListener responseListener) {
        Request<String> request = NoHttp.createStringRequest(URL, RequestMethod.POST);
        SSLContext sslContext = SSLContextUtil.getSSLContext(cerName);
        if (sslContext != null) {
            SSLSocketFactory socketFactory = sslContext.getSocketFactory();
            request.setSSLSocketFactory(socketFactory);
        }
        queue.add(what, request, responseListener);
    }

    /**
     * https-POST请求  不带参数  没证书
     *
     * @param what
     * @param URL
     * @param responseListener
     */
    public void httpsPost(int what, String URL, OnResponseListener responseListener) {
        Request<String> request = NoHttp.createStringRequest(URL, RequestMethod.POST);
        SSLContext sslContext = SSLContextUtil.getSLLContext();
        if (sslContext != null) {
            SSLSocketFactory socketFactory = sslContext.getSocketFactory();
            request.setSSLSocketFactory(socketFactory);
            request.setHostnameVerifier(SSLContextUtil.HOSTNAME_VERIFIER);
        }
        queue.add(what, request, responseListener);
    }

    /**
     * https-POST请求  带参数 有证书
     *
     * @param what
     * @param URL
     * @param params
     * @param cerName          //放在assets里的证书
     * @param responseListener
     */
    public void httpsPost(int what, String URL, HttpParams params, String cerName, OnResponseListener responseListener) {
        Request<String> request = NoHttp.createStringRequest(URL, RequestMethod.POST);
        SSLContext sslContext = SSLContextUtil.getSSLContext(cerName);
        if (sslContext != null) {
            SSLSocketFactory socketFactory = sslContext.getSocketFactory();
            request.setSSLSocketFactory(socketFactory);
        }
        params.handle(request);
        queue.add(what, request, responseListener);
    }

    /**
     * https-POST请求  带参数 没证书
     *
     * @param what
     * @param URL
     * @param params
     * @param responseListener
     */
    public void httpsPost(int what, String URL, HttpParams params, OnResponseListener responseListener) {
        Request<String> request = NoHttp.createStringRequest(URL, RequestMethod.POST);
        SSLContext sslContext = SSLContextUtil.getSLLContext();
        if (sslContext != null) {
            SSLSocketFactory socketFactory = sslContext.getSocketFactory();
            request.setSSLSocketFactory(socketFactory);
            request.setHostnameVerifier(SSLContextUtil.HOSTNAME_VERIFIER);
        }
        params.handle(request);
        queue.add(what, request, responseListener);
    }
}
