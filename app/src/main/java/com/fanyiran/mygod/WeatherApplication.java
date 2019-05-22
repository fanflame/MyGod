package com.fanyiran.mygod;

import android.app.Application;

import com.fanyiran.mygod.base.http.HttpManager;
import com.fanyiran.mygod.base.http.OkHttpHelper;
import com.fanyiran.mygod.appupdate.AppUpdateManager;
import com.tencent.bugly.crashreport.CrashReport;

/**
 * Created by fanqiang on 2019-05-10.
 */
public class WeatherApplication extends Application {
    private static WeatherApplication application;
    @Override
    public void onCreate() {
        application = this;
        super.onCreate();
        CrashReport.initCrashReport(getApplicationContext(), "90f847d6ff", false);
        LocationHelper.getInstance().startLocation(this);
        HttpManager.getInstance().initHttpClient(new OkHttpHelper());

        AppUpdateManager.getInstance().checkUpdateApp();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        LocationHelper.getInstance().stopLocation();
    }

    public static WeatherApplication getInstance() {
        return application;
    }
}
