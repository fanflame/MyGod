package com.fanyiran.mygod;

import android.content.Context;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.fanyiran.mygod.eventbeans.LocationEventBean;
import com.fanyiran.utils.LogUtil;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by fanqiang on 2019-05-13.
 */
public class LocationHelper {
    public AMapLocationClientOption mLocationOption = null;
    private AMapLocationClient mlocationClient;
    private AMapLocationListener locationListener;
    private AMapLocation aMapLocation;

    private static LocationHelper instance;


    private LocationHelper() {

    }

    public static LocationHelper getInstance() {
        if (instance == null) {
            instance = new LocationHelper();
        }
        return instance;
    }

    public void startLocation(Context context) {
        //声明mLocationOption对象
        mlocationClient = new AMapLocationClient(context);
        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        //设置返回地址信息，默认为true
        mLocationOption.setNeedAddress(true);
        this.locationListener = locationListener;
        //设置定位监听
        mlocationClient.setLocationListener(new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                if (aMapLocation != null) {
                    if (aMapLocation.getErrorCode() == 0) {
                        //定位成功回调信息，设置相关消息
//                        aMapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
//                        aMapLocation.getLatitude();//获取纬度
//                        aMapLocation.getLongitude();//获取经度
//                        aMapLocation.getAccuracy();//获取精度信息
//                        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                        Date date = new Date(aMapLocation.getTime());
//                        df.format(date);//定位时间
//                        aMapLocation.getAddress();//地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
//                        aMapLocation.getCountry();//国家信息
//                        aMapLocation.getProvince();//省信息
                        aMapLocation.getCity();//城市信息
//                        aMapLocation.getDistrict();//城区信息
//                        aMapLocation.getStreet();//街道信息
//                        aMapLocation.getStreetNum();//街道门牌号信息
                        aMapLocation.getCityCode();//城市编码
//                        aMapLocation.getAdCode();//地区编码
//                        aMapLocation.getAOIName();//获取当前定位点的AOI信息
                        if (LocationHelper.this.aMapLocation == null || LocationHelper.this.aMapLocation.getCityCode() != aMapLocation.getCityCode()) {
                            EventBus.getDefault().postSticky(new LocationEventBean(aMapLocation.getCity(), aMapLocation.getCityCode(),aMapLocation.getAdCode()));
                        }
                        LogUtil.v("LocationHelper","location info:citycode "+aMapLocation.getCityCode());
                    } else {
                        //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                        LogUtil.v("LocationHelper", "location Error, ErrCode:"
                                + aMapLocation.getErrorCode() + ", errInfo:"
                                + aMapLocation.getErrorInfo());
                        EventBus.getDefault().postSticky(new LocationEventBean(aMapLocation.getErrorInfo()));
                    }
                }
                LocationHelper.this.aMapLocation = aMapLocation;
            }
        });
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        mLocationOption.setOnceLocation(true);
//        设置定位间隔,单位毫秒,默认为2000ms
//        mLocationOption.setInterval(2000);
        //设置定位参数
        mlocationClient.setLocationOption(mLocationOption);
        // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
        // 注意设置合适的定位时间的间隔（最小间隔支持为1000ms），并且在合适时间调用stopLocation()方法来取消定位请求
        // 在定位结束后，在合适的生命周期调用onDestroy()方法
        // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
        //启动定位
        mlocationClient.startLocation();
    }

    public void stopLocation() {
        if (mlocationClient != null) {
            mlocationClient.unRegisterLocationListener(locationListener);
            mlocationClient.stopLocation();
        }
        mlocationClient = null;
    }
}
