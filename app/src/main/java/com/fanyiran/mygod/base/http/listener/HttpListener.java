package com.fanyiran.mygod.base.http.listener;

/**
 * Created by fanqiang on 2019-05-10.
 */
public interface HttpListener<T extends Object> {
    void onHttpSuccess(T t);
    void onHttpSuccess(boolean isSaveFileSuccess,String filePath);
    void onRetry(int retryCount);
    void onHttpError(String error);
}
