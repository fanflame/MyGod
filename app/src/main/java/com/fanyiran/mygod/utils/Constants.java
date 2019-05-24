package com.fanyiran.mygod.utils;

import android.os.Environment;

import java.io.File;

/**
 * Created by fanqiang on 2019-05-14.
 */
public class Constants {
    public static final String APP_SD_PATH = Environment.getExternalStorageDirectory()+ File.separator+"MyGod";

    public static final String LAST_WEATHER_FILE_PATH = APP_SD_PATH;
    public static final String LAST_WEATHER_FILE_NAME  = "lastwether.json";
    public static final String JSON_IMAGES  = "images.json";

    public static final int TEMP_MAX  = 50;
    public static final int TEMP_MIN  = -50;

    public static final String UPDATE_APP_URL_VERSION = "https://gitee.com/fanyiran/MyGodData/raw/master/version.json";
    public static final String IMAGES_VERSION = "https://gitee.com/fanyiran/MyGodData/raw/master/images.json";
    public static final String APP_DOWNLOAD_PATH = APP_SD_PATH + File.separator +"appdown";

    public static String getImageUrl(String imageId) {
        return String.format("https://hbimg.huabanimg.com/%s",imageId);
    }
}
