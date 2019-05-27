package com.fanyiran.mygod.utils.image;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory;
import com.fanyiran.mygod.utils.GlideApp;
import com.fanyiran.mygod.utils.MyGlideApp;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

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
        DrawableCrossFadeFactory factory =
                new DrawableCrossFadeFactory.Builder(1000).setCrossFadeEnabled(true).build();
        Glide.with(context).load(url).placeholder(resourceId).optionalFitCenter().transition(withCrossFade(factory)).dontAnimate().priority(Priority.IMMEDIATE).into(imageView);
    }
}
