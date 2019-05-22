package com.fanyiran.mygod.eventbeans;

/**
 * Created by fanqiang on 2019-05-13.
 */
public class LocationEventBean {
    private String error;
    private String city;
    private String cityCode;
    private String adCode;

    public LocationEventBean(String error) {
        this.error = error;
    }

    public LocationEventBean(String city, String cityCode, String adCode) {
        this.city = city;
        this.cityCode = cityCode;
        this.adCode = adCode;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getAdCode() {
        return adCode;
    }

    public void setAdCode(String adCode) {
        this.adCode = adCode;
    }
}
