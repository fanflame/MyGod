package com.fanyiran.mygod.base.http;

import com.fanyiran.mygod.base.http.listener.HttpListener;

/**
 * Created by fanqiang on 2019-05-10.
 */
public interface IHttpHelper {
     void getData(String request, HttpListener listener, Class jsonType);

     void getFile(String request,String savePath,String fileName,HttpListener listener);

     void stop();
}
