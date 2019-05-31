package com.fanyiran.mygod.utils.image;

import android.animation.ObjectAnimator;
import android.graphics.drawable.Drawable;

import com.bumptech.glide.request.transition.Transition;

/**
 * Created by fanqiang on 2019-05-31.
 */
public class CustomViewTransition implements Transition {
    private static final int MAX_VALUE = 1000;
    private ViewAdapter adapter;
    private Drawable current;
    private int animDuration = 500;

    @Override
    public boolean transition(Object current, ViewAdapter adapter) {
        this.current = (Drawable) current;
        this.adapter = adapter;
        ObjectAnimator animator = ObjectAnimator.ofInt(this, "letAnimator", 255, 0, 255);
        animator.setDuration(animDuration);
        animator.start();
        return true;
    }

    public void setLetAnimator(int value) {
        if (value == 0) {
            adapter.setDrawable(current);
        }
        if (adapter.getView() != null) {
            Drawable drawable = adapter.getCurrentDrawable();
            if (drawable != null) {
                drawable.setAlpha(value);
            }
        }
    }

    public Transition<Drawable> setDuration(int animDuration) {
        this.animDuration = animDuration;
        return this;
    }
}
