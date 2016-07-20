package com.jchaviel.soccerleaguesapp.lib.di;

import android.support.v4.app.Fragment;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.jchaviel.soccerleaguesapp.lib.GlideImageLoader;
import com.jchaviel.soccerleaguesapp.lib.GreenRobotEventBus;
import com.jchaviel.soccerleaguesapp.lib.base.EventBus;
import com.jchaviel.soccerleaguesapp.lib.base.ImageLoader;

import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;

/**
 * Created by jchavielreyes on 7/4/16.
 */
@Module
public class LibsModule {
    private Fragment fragment;

    public LibsModule(Fragment fragment) {
        this.fragment = fragment;
    }

    @Provides
    @Singleton
    ImageLoader providesImageLoader(RequestManager requestManager) {
        return new GlideImageLoader(requestManager);
    }

    @Provides
    @Singleton
    RequestManager providesRequestManager(Fragment fragment){
        return Glide.with(fragment);
    }

    @Provides
    @Singleton
    Fragment providesFragment() {
        return this.fragment;
    }

    @Provides
    @Singleton
    EventBus providesEventBus(org.greenrobot.eventbus.EventBus eventBus) {
        return new GreenRobotEventBus(eventBus);
    }

    @Provides
    @Singleton
    org.greenrobot.eventbus.EventBus providesLibraryEventBus() {
        return org.greenrobot.eventbus.EventBus.getDefault();
    }
}
