package com.fanyiran.mygod.base.http;

import com.fanyiran.mygod.base.http.listener.HttpListener;
import com.fanyiran.mygod.utils.FileUtils;
import com.fanyiran.utils.LogUtil;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by fanqiang on 2019-05-10.
 */
public class OkHttpHelper implements IHttpHelper {
    private static final String TAG = "OkHttpHelper";
    @Override
    public  void getData(String request, final HttpListener listener, Class jsonType) {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request getRequest = new Request.Builder().url(request).method(request,null).get().build();
        Call call = okHttpClient.newCall(getRequest);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (listener == null) {
                    LogUtil.v(TAG,"listener is null");
                    return;
                }
                listener.onHttpError("http get failure");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (listener == null) {
                    LogUtil.v(TAG,"listener is null");
                    return;
                }
                if (response.message().equals("OK")) {
                    listener.onHttpSuccess(new Gson().fromJson(response.body().string(), jsonType));
                } else {
                    listener.onHttpError("http get failure:"+response.message());
                }
            }
        });
    }

    @Override
    public void getFile(String request, String savePath, String saveName,HttpListener listener) {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request getRequest = new Request.Builder().url(request).method(request,null).get().build();
        Call call = okHttpClient.newCall(getRequest);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                listener.onHttpError("http get failure");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.message().equals("OK") && response.body() != null) {
                    FileUtils.saveToFile(response.body().byteStream(),savePath,saveName);
                    listener.onHttpSuccess(true,new File(savePath,saveName).getAbsolutePath());
                } else {
                    listener.onHttpError("http get failure:"+response.message());
                }
            }
        });
    }

    @Override
    public void stop() {

    }
}
