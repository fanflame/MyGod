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
import com.fanyiran.mygod.appupdate.JsonUpdateApp;
import com.fanyiran.mygod.utils.Utils;

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

    private JsonUpdateApp jsonUpdateApp;

    public static AppUpdateDialogFragment getInstance(JsonUpdateApp jsonUpdateApp){
        AppUpdateDialogFragment appUpdateDialogFragment = new AppUpdateDialogFragment();
        appUpdateDialogFragment.jsonUpdateApp = jsonUpdateApp;
        return appUpdateDialogFragment;
    }

    private AppUpdateDialogFragment(){

    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_appupdate,container);
        ButterKnife.bind(this,view);
//        if (jsonUpdateApp.getSaveFilePath() != null) {
//            tvContent.setText(jsonUpdateApp.getUpdateContent());
//        }
        tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.installApp(getContext(), jsonUpdateApp.getSaveFilePath());
            }
        });
        return view;
    }

    @OnClick(R.id.tvConfirm)
    public void onConfirmClick(){
        Utils.installApp(getContext(), jsonUpdateApp.getSaveFilePath());
    }
}
