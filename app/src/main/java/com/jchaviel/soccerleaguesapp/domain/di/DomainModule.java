package com.jchaviel.soccerleaguesapp.domain.di;

import android.content.Context;
import android.location.Geocoder;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
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

    @Provides
    @Singleton
    FirebaseAPI providesFirebaseAPI(DatabaseReference firebase, FirebaseAuth firebaseAuth){
        return new FirebaseAPI(firebase, firebaseAuth);
    }

    @Provides
    @Singleton
    DatabaseReference providesFirebase(){
        return FirebaseDatabase.getInstance().getReference();
    }

    @Provides
    @Singleton
    FirebaseAuth providesFirebaseAuth(){
        return FirebaseAuth.getInstance();
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
