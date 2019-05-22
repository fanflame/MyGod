package com.fanyiran.librollover;

import android.graphics.Typeface;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;

/**
 * Created by fanqiang on 2019-05-14.
 */
public class RollConfig {
    private Builder builder;
    private RollConfig(Builder builder) {
        this.builder = builder;
    }

    public boolean isVerticalScroll() {
        return builder.isVerticalScroll;
    }

    public boolean isScrollUpToDown() {
        return builder.isScrollUpToDown;
    }

    public int getDuration() {
        return builder.duration;
    }

    public Interpolator getInterpolator() {
        if (builder.interpolator == null) {
            builder.interpolator = new DecelerateInterpolator();
        }
        return builder.interpolator;
    }

    public Typeface getTypeFace() {
        return builder.typeFace;
    }

    public int getColor() {
        return builder.color;
    }

    public void setColor(int color) {
        builder.setColor(color);
    }

    public boolean isTextVerticalDirection() {
        return builder.isTextVerticalDirection;
    }

    public int getTextSize() {
        return builder.textSize;
    }

    public boolean isShowShadow() {
        return builder.showShadow;
    }

    public int getShadowColor() {
        return builder.shadowColor;
    }

    public void setShadowColor(int color) {
        builder.setShadowColor(color);
    }

    public static class Builder {
        private boolean isVerticalScroll = true;
        private boolean isScrollUpToDown = false;
        private int duration = 500;
        private Interpolator interpolator;
        private int color;
        private Typeface typeFace;
        private boolean isTextVerticalDirection;
        private int textSize = 10;
        private boolean showShadow = false;
        private int shadowColor;

        public Builder(){

        }

        public Builder setVerticalScroll(boolean verticalScroll) {
            isVerticalScroll = verticalScroll;
            return this;
        }

        /**
         * @param scrollUpToDown
         * @return 设置从上到下/从下到上或者从左到右/从右到左滚动
         */
        public Builder setScrollUpToDown(boolean scrollUpToDown) {
            isScrollUpToDown = scrollUpToDown;
            return this;
        }

        public Builder setDuration(int duration) {
            this.duration = duration;
            return this;
        }

        public Builder setInterpolator(Interpolator interpolator) {
            this.interpolator = interpolator;
            return this;
        }

        public Builder setColor(int color) {
            this.color = color;
            return this;
        }

        public Builder setTypeFace(Typeface typeFace) {
            this.typeFace = typeFace;
            return this;
        }


        public RollConfig build(){
            return new RollConfig(this);
        }

        public Builder setTextVerticalDirection(boolean textVerticalDirection) {
            isTextVerticalDirection = textVerticalDirection;
            return this;
        }

        public Builder setTextSize(int textSize) {
            this.textSize = textSize;
            return this;
        }

        public Builder setShowShadow(boolean showShadow) {
            this.showShadow = showShadow;
            return this;
        }


        public Builder setShadowColor(int shadowColor) {
            this.shadowColor = shadowColor;
            return this;
        }
    }
}
