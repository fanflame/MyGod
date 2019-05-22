package com.fanyiran.mygod.appupdate;

/**
 * Created by fanqiang on 2019-05-17.
 */
public class UpdateConfig {
    private static final int UPDATE_WIFI = 282;//只在wifi升级
    private static final int UPDATE_DATANET = 283;//数据网络也可以升级
    private Builder builder;

    private UpdateConfig(Builder builder) {
        this.builder = builder;
    }

    public boolean isForceUpdate() {
        return builder.forceUpdate;
    }

    public String getSavePath() {
        return builder.savePath;
    }

    public String getVersionUrl() {
        return builder.versionUrl;
    }

    public int getUpdateNetType() {
        return builder.UPDATE_NET_TYPE;
    }

    public static class Builder {
        private int UPDATE_NET_TYPE = UPDATE_WIFI;
        private boolean forceUpdate;
        private String savePath;
        private String versionUrl;

        public Builder() {

        }

        public Builder setForceUpdate(boolean forceUpdate) {
            this.forceUpdate = forceUpdate;
            return this;
        }


        public Builder setSavePath(String savePath) {
            this.savePath = savePath;
            return this;
        }


        public Builder setVersionUrl(String versionUrl) {
            this.versionUrl = versionUrl;
            return this;
        }

        public UpdateConfig build() {
            return new UpdateConfig(this);
        }


        public Builder setUpdateNetType(int UPDATE_NET_TYPE) {
            this.UPDATE_NET_TYPE = UPDATE_NET_TYPE;
            return this;
        }
    }
}
