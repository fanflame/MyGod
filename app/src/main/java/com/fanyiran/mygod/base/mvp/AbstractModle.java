package com.fanyiran.mygod.base.mvp;

import com.fanyiran.mygod.base.mvpimpl.HttpRequest;

/**
 * Created by fanqiang on 2019-05-10.
 */
public interface AbstractModle {
    void modelDestroy();

    void requestWeather(HttpRequest request);
}
