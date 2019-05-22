package com.fanyiran.mygod.base.mvp;

import com.fanyiran.mygod.base.http.WeatherData;

/**
 * Created by fanqiang on 2019-05-10.
 */
public interface IView {
    void onUpdateWeather(WeatherData weatherData);

    void onHttpError(String error);

    void onHttpRetry();
}
