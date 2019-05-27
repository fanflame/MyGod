package com.fanyiran.mygod.base.mvpimpl;

import android.Manifest;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.fanyiran.librollover.RollConfig;
import com.fanyiran.librollover.RollOverFrameLayout;
import com.fanyiran.mygod.LocationHelper;
import com.fanyiran.mygod.R;
import com.fanyiran.mygod.appupdate.AppUpdateManager;
import com.fanyiran.mygod.appupdate.JsonImages;
import com.fanyiran.mygod.base.BaseActivity;
import com.fanyiran.mygod.base.http.WeatherData;
import com.fanyiran.mygod.base.mvp.AbstractPresenter;
import com.fanyiran.mygod.base.mvp.IView;
import com.fanyiran.mygod.eventbeans.ImageJsonUpdate;
import com.fanyiran.mygod.eventbeans.LocationEventBean;
import com.fanyiran.mygod.utils.Constants;
import com.fanyiran.mygod.utils.FileUtils;
import com.fanyiran.mygod.utils.TemperatuerColor;
import com.fanyiran.mygod.utils.image.ImageLoader;
import com.fanyiran.utils.AsycTaskUtil;
import com.fanyiran.utils.LogUtil;
import com.fanyiran.utils.ToastUtils;
import com.fanyiran.utils.looperexecute.CustomRunnable;
import com.fanyiran.utils.looperexecute.MainLooperExecuteUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;
import java.util.concurrent.Callable;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WeatherActivity extends BaseActivity implements IView, SwipeRefreshLayout.OnRefreshListener {
    private static final String TAG = "WeatherActivity";
    private static final int REQUEST_PERMISSION = 562;

    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefresh;
    @BindView(R.id.tvLocation)
    RollOverFrameLayout tvLocation;
    @BindView(R.id.tvTemp)
    RollOverFrameLayout tvTemp;
    @BindView(R.id.tvWeather)
    RollOverFrameLayout tvWeather;
    @BindView(R.id.tvWindPower)
    RollOverFrameLayout tvWindPower;
    @BindView(R.id.tvWindDirection)
    RollOverFrameLayout tvWindDirection;
    @BindView(R.id.tvHumidity)
    RollOverFrameLayout tvHumidity;
    @BindView(R.id.tvUpdateTime)
    RollOverFrameLayout tvUpdateTime;
    @BindView(R.id.ivLocal)
    ImageView ivLocal;
    @BindView(R.id.ivMenu)
    ImageView ivMenu;
    @BindView(R.id.drawerLayout)
    DrawerLayout drawerLayout;
    @BindView(R.id.leftContainer)
    FrameLayout leftContainer;


    private LocationEventBean lastLocation;
    private AbstractPresenter presenter;
    private WeatherData.LivesBean lastLivesBeans;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        setContentView(R.layout.activity_weather);
        ButterKnife.bind(this);
        initPresenter();
        configText();
        swipeRefresh.setOnRefreshListener(this);

        initView();

        if (!checkPermissions()) {
            return;
        }

        presenter.getLastWeather();
        AppUpdateManager.getInstance().checkUpdateApp();

    }

    private void initView(){
        drawerLayout.setEnabled(false);
    }



    @Override
    protected void onResume() {
        super.onResume();
        onRefresh();
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLocationEvent(LocationEventBean location) {
        if (location.getError() != null) {
            ToastUtils.showText(this, String.format("定位失败：%s", location.getError()));
            swipeRefresh.setRefreshing(false);
            return;
        }
        this.lastLocation = location;
        initPresenter();
        presenter.getWeather(location.getAdCode());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onImageJsonUpdate(ImageJsonUpdate jsonImages) {
        AsycTaskUtil.getInstance().createAsycTask(new Callable(){

            @Override
            public Object call() throws Exception {
                JsonImages beanFromJson = (JsonImages) FileUtils.getBeanFromJson(JsonImages.class,
                        Constants.APP_SD_PATH, Constants.JSON_IMAGES);
                if (beanFromJson == null) {
                    LogUtil.v(TAG,"imagejson == null");
                    return null;
                }
                Random random = new Random();
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR,0);
                calendar.set(Calendar.AM,0);
                calendar.set(Calendar.SECOND,0);
                calendar.set(Calendar.MILLISECOND,0);
                calendar.set(Calendar.MINUTE,0);

                random.setSeed(calendar.getTimeInMillis());
                int index = random.nextInt(beanFromJson.getImages().size());
                return Constants.getImageUrl(beanFromJson.getImages().get(index));
            }
        }, result -> {
            CustomRunnable runnable = new CustomRunnable();
            runnable.setRunnable(new Runnable() {
                @Override
                public void run() {
                    ImageLoader.getInstance().loadImageWithCached(WeatherActivity.this,
                            (String) result,
                            ivLocal, R.drawable.placeholder);
                }
            });
            MainLooperExecuteUtil.getInstance().postDelay(runnable);
        });
    }

    private void initPresenter() {
        if (presenter == null) {
            presenter = new WeatherPresenter(this);
        }
    }

    private void configText() {
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/Raleway-Light.ttf");
//        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/Quicksand-Regular.ttf");
//        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/LoveYaLikeASister.ttf");
//        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/Reckoner.ttf");
//        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/Reckoner_Bold.ttf");

        RollConfig config = new RollConfig.Builder()
                .setColor(Color.BLACK)
                .setDuration(2000)
                .setScrollUpToDown(true)
                .setVerticalScroll(true)
                .setTypeFace(typeface)
                .build();
        tvLocation.setRollConfig(getLocationConfig());
        tvTemp.setRollConfig(getTempConfig());
        tvWeather.setRollConfig(getTempConfig());

        tvWindPower.setRollConfig(getVerticalConfig());
        tvWindDirection.setRollConfig(getVerticalConfig());
        tvHumidity.setRollConfig(getVerticalConfig());
        tvUpdateTime.setRollConfig(config);
    }

    @Override
    public void onRefresh() {
        LocationHelper.getInstance().startLocation(this);
    }

    @OnClick(R.id.ivMenu)
    public void onMenuClick(){
        ToastUtils.showText(this,getResources().getString(R.string.coming_soon));
//        drawerLayout.openDrawer(leftContainer);
    }

    @Override
    protected void onDestroy() {
        if (presenter != null) {
            presenter.destroy();
        }
        super.onDestroy();
    }

    ///////////////////////////////////////////////////////////////////////////
    // http getData result start
    ///////////////////////////////////////////////////////////////////////////
    @Override
    public void onUpdateWeather(WeatherData weatherData) {
        LogUtil.v(TAG, String.format("weather:%s", weatherData));
        if (weatherData != null && weatherData.getInfo().equals("OK") && weatherData.getLives() != null && weatherData.getLives().size() == 1) {
            lastLivesBeans = weatherData.getLives().get(0);
            updateWeather();
        }
    }

    private void updateWeather() {
        if (lastLivesBeans == null || tvLocation == null)
            return;
        if (swipeRefresh.isRefreshing()) {
            swipeRefresh.setRefreshing(false);
        }
        int colorRes = TemperatuerColor.getColorRes(Integer.valueOf(lastLivesBeans.getTemperature()));
        tvLocation.setContent(String.format("%s %s", lastLivesBeans.getProvince(), lastLivesBeans.getCity()),
                Color.WHITE, getResources().getColor(colorRes, null));

        tvTemp.setContent(String.format("%s℃", lastLivesBeans.getTemperature()), getResources().getColor(colorRes, null));
        tvWeather.setContent(String.format("%s", lastLivesBeans.getWeather()));

        tvWindPower.setContent(String.format("风力  %s", lastLivesBeans.getWindpower()));
        tvWindDirection.setContent(String.format("风向  %s", lastLivesBeans.getWinddirection()));
        tvHumidity.setContent(String.format("湿度  %s", lastLivesBeans.getHumidity()));
        tvUpdateTime.setContent(String.format("更新时间:%s", lastLivesBeans.getReporttime()));

        ColorStateList colorStateList = ColorStateList.valueOf(getResources().getColor(colorRes, null));
        // 需要先设置background才会生效
        ivMenu.setBackgroundTintList(colorStateList);

        // 需要先设置image(drawable)才会生效
        ivMenu.setImageTintList(colorStateList);
    }

    @Override
    public void onHttpError(String error) {
        LogUtil.v(TAG, String.format("error weather:%s", error));
        ToastUtils.showText(WeatherActivity.this, "获取超时");
        swipeRefresh.setRefreshing(false);
    }

    @Override
    public void onHttpRetry() {
        LogUtil.v(TAG, "weather retry");
    }
    ///////////////////////////////////////////////////////////////////////////
    // http getData result end
    ///////////////////////////////////////////////////////////////////////////

    private RollConfig getVerticalConfig() {
        return new RollConfig.Builder()
                .setColor(Color.BLACK)
                .setDuration(2000)
                .setScrollUpToDown(true)
                .setVerticalScroll(false)
                .setTypeFace(Typeface.createFromAsset(getAssets(), "fonts/Raleway-Light.ttf"))
                .setTextVerticalDirection(true)
                .setTextSize(15)
                .build();

    }

    private RollConfig getTempConfig() {
        return new RollConfig.Builder()
                .setColor(Color.BLACK)
                .setDuration(2000)
                .setScrollUpToDown(true)
                .setVerticalScroll(false)
                .setTypeFace(Typeface.createFromAsset(getAssets(), "fonts/Raleway-Light.ttf"))
                .setTextVerticalDirection(true)
                .setTextSize(40)
                .build();
    }

    private RollConfig getLocationConfig() {
        return new RollConfig.Builder()
                .setColor(Color.WHITE)
                .setDuration(2000)
                .setScrollUpToDown(true)
                .setVerticalScroll(true)
                .setTypeFace(Typeface.createFromAsset(getAssets(), "fonts/Raleway-Light.ttf"))
                .setTextVerticalDirection(false)
                .setTextSize(25)
                .setShowShadow(true)
                .build();
    }

    private boolean checkPermissions() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        String[] permissions = new String[]{
                Manifest.permission.INTERNET
                , Manifest.permission.ACCESS_COARSE_LOCATION
                , Manifest.permission.ACCESS_FINE_LOCATION
                , Manifest.permission.ACCESS_NETWORK_STATE
                , Manifest.permission.ACCESS_WIFI_STATE
                , Manifest.permission.CHANGE_WIFI_STATE
                , Manifest.permission.READ_PHONE_STATE
                , Manifest.permission.WRITE_EXTERNAL_STORAGE
                , Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS
                , Manifest.permission.BLUETOOTH
                , Manifest.permission.BLUETOOTH_ADMIN};
        ArrayList<String> result = new ArrayList<>(permissions.length);
        for (String permission : permissions) {
            if (checkSelfPermission(permission) == PackageManager.PERMISSION_DENIED) {
                result.add(permission);
            }
        }
        if (result.size() == 0) {
            return true;
        }
        String[] requestPermissions = new String[result.size()];
        result.toArray(requestPermissions);
        requestPermissions(requestPermissions, REQUEST_PERMISSION);
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION) {
            for (int grantResult : grantResults) {
                if (grantResult == PackageManager.PERMISSION_DENIED) {
                    ToastUtils.showText(this, "权限受限，无法使用本应用全部功能!");
                    return;
                }
            }
            onRefresh();
        }
    }
}
