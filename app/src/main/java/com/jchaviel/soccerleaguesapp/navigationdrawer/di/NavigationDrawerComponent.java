package com.jchaviel.soccerleaguesapp.navigationdrawer.di;

import com.jchaviel.soccerleaguesapp.SoccerLeaguesAppModule;
import com.jchaviel.soccerleaguesapp.domain.di.DomainModule;
import com.jchaviel.soccerleaguesapp.lib.di.LibsModule;
import com.jchaviel.soccerleaguesapp.navigationdrawer.ui.NavigationDrawerFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {NavigationDrawerModule.class, DomainModule.class, LibsModule.class, SoccerLeaguesAppModule.class})
public interface NavigationDrawerComponent {
    void inject(NavigationDrawerFragment fragment);
}
