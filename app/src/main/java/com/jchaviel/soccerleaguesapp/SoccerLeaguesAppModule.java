package com.jchaviel.soccerleaguesapp;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by jchavielreyes on 7/4/16.
 */
@Module
public class SoccerLeaguesAppModule {

    SoccerLeaguesApp app;

    public SoccerLeaguesAppModule(SoccerLeaguesApp soccerLeaguesApp) {
        this.app = soccerLeaguesApp;
    }

    @Provides
    @Singleton
    Context providesApplicationContext(){
        return this.app.getApplicationContext();
    }

    @Provides
    @Singleton
    SharedPreferences providesSharedPreferences(Application application){
        return application.getSharedPreferences(this.app.getSharedPreferencesName(), Context.MODE_PRIVATE);
    }

    @Provides
    @Singleton
    Application providesApplication(){
        return this.app;
    }
}
