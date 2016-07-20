package com.jchaviel.soccerleaguesapp.lib;

import android.widget.ImageView;

import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestListener;
import com.jchaviel.soccerleaguesapp.lib.base.ImageLoader;


/**
 * Created by jchavielreyes on 7/4/16.
 */
public class GlideImageLoader implements ImageLoader {
    private RequestManager glideRequestManager;
    private RequestListener onFinishedLoadingListener;

    public GlideImageLoader(RequestManager requestManager) {
        glideRequestManager = requestManager;
    }

    @Override
    public void load(ImageView imageView, String URL) {
        if(this.onFinishedLoadingListener != null){
            glideRequestManager
                    .load(URL)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop()
                    .listener(this.onFinishedLoadingListener)
                    .into(imageView);
        }
        else{
            glideRequestManager
                    .load(URL)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .override(700,700) //para restringir la imagen a maximo de 700x700
                    .centerCrop()
                    .into(imageView);
        }
    }
}
