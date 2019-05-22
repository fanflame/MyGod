package com.fanyiran.mygod.utils.dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.fanyiran.mygod.R;
import com.fanyiran.mygod.appupdate.UpdateAppJson;
import com.fanyiran.mygod.utils.Utils;

import org.greenrobot.eventbus.EventBus;
import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by fanqiang on 2019-05-20.
 */
public class AppUpdateDialogFragment extends DialogFragment {
    @BindView(R.id.tvContent)
    TextView tvContent;
    @BindView(R.id.tvConfirm)
    TextView tvConfirm;

    private UpdateAppJson updateAppJson;

    public static AppUpdateDialogFragment getInstance(UpdateAppJson updateAppJson){
        AppUpdateDialogFragment appUpdateDialogFragment = new AppUpdateDialogFragment();
        appUpdateDialogFragment.updateAppJson = updateAppJson;
        return appUpdateDialogFragment;
    }

    private AppUpdateDialogFragment(){

    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_appupdate,container);
        ButterKnife.bind(this,view);
//        if (updateAppJson.getSaveFilePath() != null) {
//            tvContent.setText(updateAppJson.getUpdateContent());
//        }
        tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.installApp(getContext(),updateAppJson.getSaveFilePath());
            }
        });
        return view;
    }

    @OnClick(R.id.tvConfirm)
    public void onConfirmClick(){
        Utils.installApp(getContext(),updateAppJson.getSaveFilePath());
    }
}
