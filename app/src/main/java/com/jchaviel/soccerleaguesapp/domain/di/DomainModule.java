package com.jchaviel.soccerleaguesapp.domain.di;

import android.content.Context;
import android.location.Geocoder;

import com.firebase.client.Firebase;
import com.jchaviel.soccerleaguesapp.domain.FirebaseAPI;
import com.jchaviel.soccerleaguesapp.domain.Utils;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by jchavielreyes on 7/4/16.
 */
@Module
public class DomainModule {

    String firebaseURL;

    public DomainModule(String firebaseURL) {
        this.firebaseURL = firebaseURL;
    }

    @Provides
    @Singleton
    FirebaseAPI providesFirebaseAPI(Firebase firebase){
        return new FirebaseAPI(firebase);
    }

    @Provides
    @Singleton
    Firebase providesFirebase(String firebaseURL){
        return new Firebase(firebaseURL);
    }


    @Provides
    @Singleton
    String providesFirebaseURL(){
        return this.firebaseURL;
    }

    @Provides
    @Singleton
    Utils providesUtil(Geocoder geocoder){
        return new Utils(geocoder);
    }

    @Provides
    @Singleton
    Geocoder providesGeocoder(Context context){
        return new Geocoder(context);
    }
}
