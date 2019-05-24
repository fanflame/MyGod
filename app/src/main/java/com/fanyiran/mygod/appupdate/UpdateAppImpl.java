package com.fanyiran.mygod.appupdate;

import com.fanyiran.mygod.WeatherApplication;
import com.fanyiran.mygod.base.mvpimpl.HttpRequest;
import com.fanyiran.mygod.base.http.listener.HttpListener;
import com.fanyiran.mygod.base.http.HttpManager;
import com.fanyiran.mygod.base.http.listener.HttpListenerAdpater;
import com.fanyiran.mygod.eventbeans.ImageJsonUpdate;
import com.fanyiran.mygod.utils.Constants;
import com.fanyiran.mygod.utils.FileUtils;
import com.fanyiran.mygod.utils.Utils;
import com.fanyiran.utils.LogUtil;

import org.greenrobot.eventbus.EventBus;

import java.io.File;

/**
 * Created by fanqiang on 2019-05-17.
 */
public class UpdateAppImpl implements IUpdateApp {
    private static final String TAG = "UpdateAppImpl";
    private HttpListener versionJsonListener;
    private HttpListener downAppListener;
    private HttpListenerAdpater imagesJsonListener;

    @Override
    public void checkAppVersion(UpdateConfig config) {
        HttpRequest request = new HttpRequest.Builder()
                .setUrl(config.getVersionUrl())
                .setJsonType(JsonUpdateApp.class)
                .setSavePath(config.getSavePath())
                .setListener(getJsonListener())
                .build();
        HttpManager.getInstance().getData(request);
    }

    private HttpListener getJsonListener(){
        if (versionJsonListener == null) {
            versionJsonListener = new HttpListenerAdpater() {
                @Override
                public void onHttpSuccess(Object o) {
                    JsonUpdateApp jsonUpdateApp = (JsonUpdateApp) o;
                    String versionName = Utils.getVersionName(WeatherApplication.getInstance());
                    if (versionName.compareTo(jsonUpdateApp.getVersion()) < 0) {
                        // TODO: 2019-05-20 判断本地是否存在以及完整
                        File file = new File(Constants.APP_DOWNLOAD_PATH,String.format("OhMyGod_%s.apk", jsonUpdateApp.getVersion()));
                        if (file.exists()) {
                            String jsonMd5 = Utils.md5(file);
                            if (jsonMd5.equals(jsonUpdateApp.getMd5())) {
                                getDownAppListener().onHttpSuccess(true, file.getAbsolutePath());
                                return;
                            } else {
                                LogUtil.v(TAG,"md5 not compare");
                            }
                        }
                        // TODO: 2019-05-17 判断网络
                        HttpRequest getFileRequest = new HttpRequest.Builder()
                                .setUrl(jsonUpdateApp.getAppurl())
                                .setSavePath(Constants.APP_DOWNLOAD_PATH)
                                .setListener(getDownAppListener())
                                .setSaveFileName(String.format("OhMyGod_%s.apk", jsonUpdateApp.getVersion()))
                                .build();
                        HttpManager.getInstance().getFile(getFileRequest);
                    }
                    JsonImages beanFromJson = (JsonImages) FileUtils.getBeanFromJson(JsonImages.class,
                            Constants.APP_SD_PATH, Constants.JSON_IMAGES);
                    if (beanFromJson == null || beanFromJson.getImagesVersion() < jsonUpdateApp.getImagesVersion()) {
                        HttpRequest imageJsonRequest = new HttpRequest.Builder()
                                .setUrl(Constants.IMAGES_VERSION)
                                .setSavePath(Constants.APP_SD_PATH)
                                .setListener(getImageJsonListener())
                                .setSaveFileName("images.json")
                                .build();
                        HttpManager.getInstance().getFile(imageJsonRequest);
                    } else {
                        EventBus.getDefault().postSticky(new ImageJsonUpdate());
                    }
                }
            };
        }
        return versionJsonListener;
    }

    private HttpListener getImageJsonListener() {
        if (imagesJsonListener == null) {
            imagesJsonListener = new HttpListenerAdpater() {
                @Override
                public void onHttpSuccess(boolean isSaveFileSuccess, String filePath) {
                    EventBus.getDefault().postSticky(new ImageJsonUpdate());
                }
            };
        }
        return imagesJsonListener;
    }

    private HttpListener getDownAppListener() {
        if (downAppListener == null) {
            downAppListener = new HttpListenerAdpater() {
                @Override
                public void onHttpSuccess(boolean isSaveFileSuccess, String filePath) {
                    JsonUpdateApp jsonUpdateApp = new JsonUpdateApp();
                    jsonUpdateApp.setSaveFilePath(filePath);
                    EventBus.getDefault().postSticky(jsonUpdateApp);
                }
            };
        }
        return downAppListener;
    }
}
