package com.jchaviel.soccerleaguesapp.domain.di;

import com.jchaviel.soccerleaguesapp.SoccerLeaguesAppModule;

import javax.inject.Singleton;

import dagger.Component;
/**
 * Created by jchavielreyes on 7/4/16.
 */
@Singleton
@Component(modules = {DomainModule.class, SoccerLeaguesAppModule.class})
public interface DomainComponent {

}
