package com.fanyiran.mygod.appupdate;

import com.fanyiran.mygod.utils.Constants;

/**
 * Created by fanqiang on 2019-05-17.
 */
public class AppUpdateManager {
    private IUpdateApp iUpdateApp;
    private static final AppUpdateManager ourInstance = new AppUpdateManager();

    public static AppUpdateManager getInstance() {
        return ourInstance;
    }

    private AppUpdateManager() {
        iUpdateApp = new UpdateAppImpl();
    }

    public void checkUpdateApp() {
        UpdateConfig updateConfig = new UpdateConfig.Builder()
                .setVersionUrl(Constants.UPDATE_APP_URL_VERSION)
                .setSavePath(Constants.APP_DOWNLOAD_PATH)
                .build();

        iUpdateApp.checkAppVersion(updateConfig);
    }


}
