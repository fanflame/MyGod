package com.fanyiran.librollover;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Created by fanqiang on 2019-05-14.
 */
public class RollOverFrameLayout extends FrameLayout {
    private String lastContent;
    private TextView tvFirst;
    private TextView tvSecond;
    private RollConfig config;

    public RollOverFrameLayout(@NonNull Context context) {
        super(context);
        initView();
    }

    public RollOverFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        View.inflate(getContext(), R.layout.rollover_layout, this);
        tvFirst = findViewById(R.id.tvFirst);
        tvSecond = findViewById(R.id.tvSecond);
    }

    public void setRollConfig(RollConfig config) {
        if(config == null)
            throw new IllegalArgumentException("config is null");
        this.config = config;
        tvSecond.setTextColor(config.getColor());
        tvFirst.setTextColor(config.getColor());
        tvFirst.setTextSize(config.getTextSize());
        tvSecond.setTextSize(config.getTextSize());
        if (config.isTextVerticalDirection()) {
            tvFirst.setMaxEms(1);
            tvSecond.setMaxEms(1);
        }
        if (config.getTypeFace() != null) {
            tvFirst.setTypeface(config.getTypeFace());
            tvSecond.setTypeface(config.getTypeFace());
        }

        if (config.isShowShadow()) {
            tvFirst.setShadowLayer(5,5,5,config.getShadowColor());
            tvSecond.setShadowLayer(5,5,5,config.getShadowColor());
        }
    }

    public void setContent(String content) {
        setContent(content,getConfig().getColor());
    }

    public void setContent(String content, int color) {
        setContent(content,color,getConfig().getShadowColor());
    }

    public void setContent(String content, int contentColor,int shadowColor) {
        if (lastContent != null
                && lastContent.equals(content)
                && getConfig().getColor() == contentColor
                && getConfig().getShadowColor() == shadowColor) {
            return;
        }
        lastContent = content;
        getConfig().setColor(contentColor);
        getConfig().setShadowColor(shadowColor);
        changeTv();
        tvSecond.setText(content);
        tvSecond.setTextColor(contentColor);
        tvFirst.setTextColor(contentColor);
        if (getConfig().isShowShadow()) {
            tvFirst.setShadowLayer(5,5,5,shadowColor);
            tvSecond.setShadowLayer(5,5,5,shadowColor);
        }
        startAnimate();
    }

    private void changeTv() {
        TextView tvTemp = tvFirst;
        tvFirst = tvSecond;
        tvSecond = tvTemp;
    }

    private void startAnimate() {
        ObjectAnimator animatorFrist;
        ObjectAnimator animatorSecond;
        if (getConfig().isScrollUpToDown()) {
            animatorFrist = ObjectAnimator.ofFloat(tvSecond, getTransDirection(), getAnimEndPosition(), 0);
            animatorSecond = ObjectAnimator.ofFloat(tvFirst, getTransDirection(), 0, -getAnimEndPosition());
        } else {
            animatorFrist = ObjectAnimator.ofFloat(tvFirst, getTransDirection(),  0,getAnimEndPosition());
            animatorSecond = ObjectAnimator.ofFloat(tvSecond, getTransDirection(),  -getAnimEndPosition(),0);
        }

        AnimatorSet set = new AnimatorSet();
        set.setDuration(getConfig().getDuration());
        set.setInterpolator(getConfig().getInterpolator());
        set.playTogether(animatorFrist,animatorSecond);
        set.start();
    }

    private String getTransDirection() {
        if(getConfig().isVerticalScroll())
            return "translationY";
        else
            return "translationX";
    }

    private float getAnimEndPosition() {
        if (getConfig().isVerticalScroll())
            return getHeight();
        else return getWidth();
    }

    private RollConfig getConfig() {
        if (config == null) {
            config = new RollConfig.Builder().build();
        }
        return config;
    }


}
