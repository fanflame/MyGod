package com.fanyiran.mygod.appupdate;

import androidx.annotation.Keep;

/**
 * Created by fanqiang on 2019-05-17.
 */
@Keep
public class JsonUpdateApp {

    /**
     * version : 1.0.3
     * imagesVersion : 6
     * appurl : https://github.com/fanflame/MyGod/blob/master/releaseapks/zzz?raw=true
     * updateContent :
     * md5 : apkMd5ssss
     * verion : ssss
     */

    private String version;
    private int imagesVersion;
    private String appurl;
    private String updateContent;
    private String md5;
    private String verion;
    private String saveFilePath;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public int getImagesVersion() {
        return imagesVersion;
    }

    public void setImagesVersion(int imagesVersion) {
        this.imagesVersion = imagesVersion;
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

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public String getVerion() {
        return verion;
    }

    public void setVerion(String verion) {
        this.verion = verion;
    }

    public String getSaveFilePath() {
        return saveFilePath;
    }

    public void setSaveFilePath(String saveFilePath) {
        this.saveFilePath = saveFilePath;
    }
}
