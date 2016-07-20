package com.jchaviel.soccerleaguesapp.main.di;

import com.jchaviel.soccerleaguesapp.SoccerLeaguesAppModule;
import com.jchaviel.soccerleaguesapp.domain.di.DomainModule;
import com.jchaviel.soccerleaguesapp.lib.di.LibsModule;
import com.jchaviel.soccerleaguesapp.main.ui.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by jchavielreyes on 7/6/2016.
 */
@Singleton
@Component (modules = {MainModule.class, DomainModule.class, LibsModule.class, SoccerLeaguesAppModule.class})
public interface MainComponent {
    void inject(MainActivity activity);
}
