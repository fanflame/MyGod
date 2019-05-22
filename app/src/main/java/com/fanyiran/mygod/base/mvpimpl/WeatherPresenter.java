package com.fanyiran.mygod.base.mvpimpl;

import com.fanyiran.mygod.R;
import com.fanyiran.mygod.WeatherApplication;
import com.fanyiran.mygod.base.mvp.AbstractPresenter;
import com.fanyiran.mygod.base.mvp.IView;
import com.fanyiran.mygod.base.http.listener.HttpListener;
import com.fanyiran.mygod.base.http.WeatherData;
import com.fanyiran.mygod.base.http.listener.HttpListenerAdpater;
import com.fanyiran.mygod.utils.Constants;
import com.fanyiran.mygod.utils.FileUtils;
import com.fanyiran.utils.AsycTaskUtil;
import com.fanyiran.utils.LogUtil;
import com.fanyiran.utils.looperexecute.CustomRunnable;
import com.fanyiran.utils.looperexecute.MainLooperExecuteUtil;

import java.util.concurrent.Callable;

/**
 * Created by fanqiang on 2019-05-10.
 */
public class WeatherPresenter extends AbstractPresenter {
    private static final String TAG = "WeatherPresenter";
    private static final String url = "https://restapi.amap.com/v3/weather/weatherInfo?key=%s&city=%s";
    private CustomRunnable updateWeatherRunnable;

    private HttpListener httpListener = new HttpListenerAdpater() {
        @Override
        public void onHttpSuccess(final Object weatherData) {
            FileUtils.saveBeanToJson(weatherData, Constants.LAST_WEATHER_FILE_PATH, Constants.LAST_WEATHER_FILE_NAME);

            CustomRunnable customRunnable = new CustomRunnable();
            customRunnable.setRunnable(new Runnable() {
                @Override
                public void run() {
                    mIview.onUpdateWeather((WeatherData) weatherData);
                }
            });
            MainLooperExecuteUtil.getInstance().postDelay(customRunnable);
        }

        @Override
        public void onRetry(int retryCount) {
            CustomRunnable customRunnable = new CustomRunnable();
            customRunnable.setRunnable(new Runnable() {
                @Override
                public void run() {
                    mIview.onHttpRetry();
                }
            });
            MainLooperExecuteUtil.getInstance().postDelay(customRunnable);
        }

        @Override
        public void onHttpError(final String error) {
            CustomRunnable customRunnable = new CustomRunnable();
            customRunnable.setRunnable(new Runnable() {
                @Override
                public void run() {
                    mIview.onHttpError(error);
                }
            });
            MainLooperExecuteUtil.getInstance().postDelay(customRunnable);
        }
    };
    public WeatherPresenter(IView iView) {
        super(iView);
        iniModel();
    }

    @Override
    public void iniModel() {
        mImodel = new WeatherModel();
    }

    @Override
    public void getWeather(String cityCode) {
        mImodel.requestWeather(new HttpRequest.Builder().setListener(httpListener).
                setUrl(String.format(url, WeatherApplication.getInstance().getString(R.string.amap_key_weather),cityCode))
                .setJsonType(WeatherData.class)
                .build());
    }

    @Override
    public void getLastWeather() {
        AsycTaskUtil.getInstance().createAsycTask(new Callable() {
            @Override
            public Object call() throws Exception {
                return FileUtils.getBeanFromJson(WeatherData.class, Constants.LAST_WEATHER_FILE_PATH, Constants.LAST_WEATHER_FILE_NAME);
            }
        }, new AsycTaskUtil.OnTaskListener() {
            @Override
            public void onTaskFinished(Object result) {
                LogUtil.v(TAG,String.format("last weather:%s",result));
                if (result != null) {
                    mIview.onUpdateWeather((WeatherData) result);
                }
            }
        });
    }
}
