package com.fanyiran.mygod.appupdate;

import androidx.annotation.Keep;

/**
 * Created by fanqiang on 2019-05-17.
 */
@Keep
public class UpdateAppJson {

    /**
     * version : 1.0.0
     * appurl :
     * updateContent :
     * md5:
     */

    private String version;
    private String appurl;
    private String updateContent;
    private String saveFilePath;
    private String md5;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getAppurl() {
        return appurl;
    }

    public void setAppurl(String appurl) {
        this.appurl = appurl;
    }

    public String getUpdateContent() {
        return updateContent;
    }

    public void setUpdateContent(String updateContent) {
        this.updateContent = updateContent;
    }

    public String getSaveFilePath() {
        return saveFilePath;
    }

    public void setSaveFilePath(String saveFilePath) {
        this.saveFilePath = saveFilePath;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }
}
