package com.jchaviel.soccerleaguesapp.lib.di;

import com.jchaviel.soccerleaguesapp.SoccerLeaguesAppModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by jchavielreyes on 7/4/16.
 */
@Singleton
@Component(modules = {LibsModule.class, SoccerLeaguesAppModule.class})
public interface LibsComponent {

}
