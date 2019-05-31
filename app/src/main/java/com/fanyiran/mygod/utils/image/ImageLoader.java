package com.fanyiran.mygod.utils.image;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.transition.Transition;
import com.bumptech.glide.request.transition.TransitionFactory;
import com.bumptech.glide.request.transition.ViewPropertyTransition;
import com.fanyiran.mygod.utils.GlideApp;

/**
 * Created by fanqiang on 2019-05-15.
 */
public class ImageLoader {
    private static final ImageLoader ourInstance = new ImageLoader();

    public static ImageLoader getInstance() {
        return ourInstance;
    }

    private ImageLoader() {
    }

    /**
     * 获取到图片之后，
     * @param context
     * @param url
     * @param imageView
     */
    public void loadImageWithCached(Context context, String url, ImageView imageView,int resourceId ) {
        ViewPropertyTransition.Animator animator = null;
        GlideApp.with(context)
                .load(url)
                .optionalCenterCrop()
                .placeholder(resourceId)
                .transition(DrawableTransitionOptions.with(new TransitionFactory<Drawable>() {
                    @Override
                    public Transition<Drawable> build(DataSource dataSource, boolean isFirstResource) {
                        return new CustomViewTransition().setDuration(1000);
                    }
                }))
                .priority(Priority.IMMEDIATE)
                .transform(new CenterCrop())
                .into(imageView);
    }
}
