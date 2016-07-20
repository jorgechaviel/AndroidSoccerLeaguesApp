package com.jchaviel.soccerleaguesapp.schedule.di;

import com.jchaviel.soccerleaguesapp.domain.FirebaseAPI;
import com.jchaviel.soccerleaguesapp.domain.Utils;
import com.jchaviel.soccerleaguesapp.entities.Fixture;
import com.jchaviel.soccerleaguesapp.lib.base.EventBus;
import com.jchaviel.soccerleaguesapp.lib.base.ImageLoader;
import com.jchaviel.soccerleaguesapp.schedule.ScheduleInteractor;
import com.jchaviel.soccerleaguesapp.schedule.ScheduleInteractorImpl;
import com.jchaviel.soccerleaguesapp.schedule.SchedulePresenter;
import com.jchaviel.soccerleaguesapp.schedule.SchedulePresenterImpl;
import com.jchaviel.soccerleaguesapp.schedule.ScheduleRepository;
import com.jchaviel.soccerleaguesapp.schedule.ScheduleRepositoryImpl;
import com.jchaviel.soccerleaguesapp.schedule.ui.ScheduleView;
import com.jchaviel.soccerleaguesapp.schedule.ui.adapter.ScheduleAdapter;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by jchavielreyes on 7/6/2016.
 */
@Module
public class ScheduleModule {
    private ScheduleView view;

    public ScheduleModule(ScheduleView view) {
        this.view = view;
    }

    @Provides @Singleton
    ScheduleView providesScheduleView() {
        return this.view;
    }

    @Provides @Singleton
    SchedulePresenter providesSchedulePresenter(EventBus eventBus, ScheduleView view, ScheduleInteractor interactor){
        return new SchedulePresenterImpl(eventBus, view, interactor);
    }

    @Provides @Singleton
    ScheduleInteractor providesScheduleInteractor(ScheduleRepository repository){
        return new ScheduleInteractorImpl(repository);
    }

    @Provides @Singleton
    ScheduleRepository providesScheduleRepository(EventBus eventBus, FirebaseAPI firebaseAPI){
        return new ScheduleRepositoryImpl(eventBus, firebaseAPI);
    }

    @Provides @Singleton
    ScheduleAdapter providesScheduleAdapter(Utils utils, List<Fixture> fixtures, ImageLoader imageLoader){
        return new ScheduleAdapter(utils, fixtures, imageLoader);
    }

    @Provides @Singleton
    List<Fixture> providesFixtureList(){
        return new ArrayList<Fixture>();
    }
}
