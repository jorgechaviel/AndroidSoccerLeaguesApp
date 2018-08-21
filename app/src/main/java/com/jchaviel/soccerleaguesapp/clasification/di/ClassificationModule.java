package com.jchaviel.soccerleaguesapp.clasification.di;

import com.jchaviel.soccerleaguesapp.clasification.ClassificationInteractor;
import com.jchaviel.soccerleaguesapp.clasification.ClassificationInteractorImpl;
import com.jchaviel.soccerleaguesapp.clasification.ClassificationPresenter;
import com.jchaviel.soccerleaguesapp.clasification.ClassificationPresenterImpl;
import com.jchaviel.soccerleaguesapp.clasification.ClassificationRepository;
import com.jchaviel.soccerleaguesapp.clasification.ClassificationRepositoryImpl;
import com.jchaviel.soccerleaguesapp.clasification.ui.ClassificationView;
import com.jchaviel.soccerleaguesapp.clasification.ui.adapter.ClassificationAdapter;
import com.jchaviel.soccerleaguesapp.entities.Team;
import com.jchaviel.soccerleaguesapp.lib.base.EventBus;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ClassificationModule {

    private ClassificationView view;

    public ClassificationModule(ClassificationView view) {
        this.view = view;
    }

    @Provides
    @Singleton
    public ClassificationView providesClassificationView(){
        return this.view;
    }

    @Provides @Singleton
    public ClassificationPresenter providesClassificationPresenter(EventBus eventBus, ClassificationView view, ClassificationInteractor interactor){
        return new ClassificationPresenterImpl(eventBus, view, interactor);
    }

    @Provides @Singleton
    public ClassificationInteractor providesClassificationInteractor(ClassificationRepository repository){
        return new ClassificationInteractorImpl(repository);
    }

    @Provides @Singleton
    public ClassificationRepository providesClassificationRepository(EventBus eventBus){
        return new ClassificationRepositoryImpl(eventBus);
    }

    @Provides @Singleton
    public ClassificationAdapter providesClassificationAdapter(List<Team> teamList){
        return new ClassificationAdapter(teamList);
    }

    @Provides @Singleton
    public List<Team> providesTeamList(){
        return new ArrayList<Team>();
    }
}
