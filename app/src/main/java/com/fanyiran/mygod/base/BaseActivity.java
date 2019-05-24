package com.fanyiran.mygod.base;

import androidx.appcompat.app.AppCompatActivity;

import com.fanyiran.mygod.appupdate.JsonUpdateApp;
import com.fanyiran.mygod.utils.dialog.AppUpdateDialogFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by fanqiang on 2019-05-14.
 */
public class BaseActivity extends AppCompatActivity {
    private AppUpdateDialogFragment appUpdateDialogFragment;
    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }
    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUpdateApp(JsonUpdateApp jsonUpdateApp){
        if (appUpdateDialogFragment != null) {
            appUpdateDialogFragment.dismiss();
        }
        appUpdateDialogFragment = AppUpdateDialogFragment.getInstance(jsonUpdateApp);
        appUpdateDialogFragment.show(getSupportFragmentManager(),"");
    }

    @Override
    protected void onDestroy() {
        if (appUpdateDialogFragment != null) {
            appUpdateDialogFragment.dismiss();
        }
        super.onDestroy();
    }
}
