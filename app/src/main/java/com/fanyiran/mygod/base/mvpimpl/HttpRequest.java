package com.fanyiran.mygod.base.mvpimpl;

import com.fanyiran.mygod.base.http.listener.HttpListener;

/**
 * Created by fanqiang on 2019-05-13.
 */
public class HttpRequest {
    private Builder builder;

    private HttpRequest(Builder builder) {
        this.builder = builder;
    }

    public HttpListener getListener() {
        return builder.listener;
    }

    public String getUrl() {
        return builder.url;
    }

    public Class getJsonType() {
        return builder.jsonType;
    }

    public String getSavePath() {
        return builder.savePath;
    }

    public String getFileName() {
        return builder.fileName;
    }

    public static class Builder {
        public String fileName;
        private String savePath;
        private HttpListener listener;
        private String url;
        private Class jsonType;

        public Builder() {

        }

        public Builder setListener(HttpListener listener) {
            this.listener = listener;
            return this;
        }

        public Builder setUrl(String url) {
            this.url = url;
            return this;
        }


        public HttpRequest build() {
            return new HttpRequest(this);
        }


        public Builder setJsonType(Class jsonType) {
            this.jsonType = jsonType;
            return this;
        }

        public Builder setSavePath(String savePath) {
            this.savePath = savePath;
            return this;
        }

        public Builder setSaveFileName(String fileName) {
            this.fileName = fileName;
            return this;
        }
    }

}
