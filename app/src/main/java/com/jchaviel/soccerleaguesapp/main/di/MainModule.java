package com.jchaviel.soccerleaguesapp.main.di;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.jchaviel.soccerleaguesapp.domain.FirebaseAPI;
import com.jchaviel.soccerleaguesapp.lib.base.EventBus;
import com.jchaviel.soccerleaguesapp.main.MainPresenter;
import com.jchaviel.soccerleaguesapp.main.MainPresenterImpl;
import com.jchaviel.soccerleaguesapp.main.MainRepository;
import com.jchaviel.soccerleaguesapp.main.MainRepositoryImpl;
import com.jchaviel.soccerleaguesapp.main.SessionInteractor;
import com.jchaviel.soccerleaguesapp.main.SessionInteractorImpl;
import com.jchaviel.soccerleaguesapp.main.ui.MainView;
import com.jchaviel.soccerleaguesapp.main.ui.adapter.FragmentsPagerAdapter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by jchavielreyes on 7/6/2016.
 */
@Module
public class MainModule {
    private MainView view;
    private String[] titles;
    private Fragment[] fragments;
    private FragmentManager fragmentManager;

    public MainModule(MainView view, String[] titles, Fragment[] fragments, FragmentManager fragmentManager) {
        this.view = view;
        this.titles = titles;
        this.fragments = fragments;
        this.fragmentManager = fragmentManager;
    }

    @Provides
    @Singleton
    MainView providesMainView() {
        return this.view;
    }

    @Provides @Singleton
    MainPresenter providesMainPresenter(MainView view, EventBus eventBus, SessionInteractor sessionInteractor) {
        return new MainPresenterImpl(view, eventBus, sessionInteractor);
    }

    @Provides @Singleton
    SessionInteractor providesSessionInteractor(MainRepository repository) {
        return new SessionInteractorImpl(repository);
    }

    @Provides @Singleton
    MainRepository providesMainRepository(FirebaseAPI firebase) {
        return new MainRepositoryImpl(firebase);
    }

    @Provides @Singleton
    FragmentsPagerAdapter providesAdapter(FragmentManager fm, String[] titles, Fragment[] fragments){
        return new FragmentsPagerAdapter(fm, titles, fragments);
    }

    @Provides @Singleton
    FragmentManager providesAdapterForFragmentManager(){
        return this.fragmentManager;
    }

    @Provides @Singleton
    Fragment[] providesFragmentArrayForAdapter(){
        return this.fragments;
    }

    @Provides @Singleton
    String[] providesStringArrayForAdapter(){
        return this.titles;
    }
}
