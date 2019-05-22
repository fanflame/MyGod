package com.fanyiran.mygod.base.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.fanyiran.mygod.R;
import com.fanyiran.mygod.utils.Constants;
import com.fanyiran.mygod.utils.FileUtils;
import com.fanyiran.mygod.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by fanqiang on 2019-05-20.
 */
public class LeftContainerFragment extends Fragment {
    @BindView(R.id.btnClearAllCache)
    Button btnClearAllCache;
    @BindView(R.id.tvVersion)
    TextView tvVersion;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.leftcontainer, container);
        ButterKnife.bind(this,inflate);
        initView();
        return inflate;
    }

    @OnClick(R.id.btnClearAllCache)
    public void onClearAllCache(){
        // TODO: 2019-05-20 异步？
        Glide.get(getContext()).clearDiskCache();
        FileUtils.clearPath(Constants.APP_DOWNLOAD_PATH);
    }

    private void initView(){
        tvVersion.setText(String.format("版本号:%s",Utils.getVersionName(getContext())));
    }
}
