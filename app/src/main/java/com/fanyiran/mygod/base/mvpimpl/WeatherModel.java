package com.fanyiran.mygod.base.mvpimpl;

import com.fanyiran.mygod.base.mvp.AbstractModle;
import com.fanyiran.mygod.base.http.HttpManager;

/**
 * Created by fanqiang on 2019-05-10.
 */
public class WeatherModel implements AbstractModle {
    @Override
    public void modelDestroy() {

    }

    @Override
    public void requestWeather(HttpRequest request) {
        HttpManager.getInstance().getData(request);
    }
}
