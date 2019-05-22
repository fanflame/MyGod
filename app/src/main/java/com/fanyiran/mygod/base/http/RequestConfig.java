package com.fanyiran.mygod.base.http;

/**
 * Created by fanqiang on 2019-05-10.
 */
public class RequestConfig {
    static final int DEFALUT_RETRYTIMES = 0;
    private Builder builder;
    public RequestConfig(Builder builder) {
        this.builder = builder;
    }

    public int getRetryTimes() {
        if(builder == null)
            return DEFALUT_RETRYTIMES;
        return builder.retryTimes;
    }

    public class Builder {
        private int retryTimes;
        public Builder() {

        }

        public Builder retryTimes(int retryTimes) {
            this.retryTimes = retryTimes;
            return this;
        }

        public RequestConfig build() {
            return new RequestConfig(this);
        }
    }

}
