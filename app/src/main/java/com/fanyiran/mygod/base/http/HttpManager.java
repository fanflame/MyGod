package com.fanyiran.mygod.base.http;

import com.fanyiran.mygod.base.mvpimpl.HttpRequest;

/**
 * Created by fanqiang on 2019-05-10.
 */
public class HttpManager {
    private IHttpHelper httpClient;
    private static final HttpManager ourInstance = new HttpManager();

    public static HttpManager getInstance() {
        return ourInstance;
    }

    private HttpManager() {
    }

    public void initHttpClient(IHttpHelper client) {
        this.httpClient = client;
    }

    public void getData(HttpRequest request) {
        checkHttpClient();
        httpClient.getData(request.getUrl(),request.getListener(),request.getJsonType());
    }

    public void getFile(HttpRequest request) {
        checkHttpClient();
        httpClient.getFile(request.getUrl(),request.getSavePath(),request.getFileName(),request.getListener());
    }

//    public void getImageJson() {
//        checkHttpClient();
//        httpClient.getJsonFile("https://gitee.com/fanyiran/MyGodData/raw/master/image.json");
//    }

    private void checkHttpClient() {
        if (httpClient == null) {
            throw new IllegalArgumentException("http client not init");
        }
    }
}
