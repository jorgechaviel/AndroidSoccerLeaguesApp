package com.jchaviel.soccerleaguesapp.schedule.di;

import com.jchaviel.soccerleaguesapp.SoccerLeaguesAppModule;
import com.jchaviel.soccerleaguesapp.domain.di.DomainModule;
import com.jchaviel.soccerleaguesapp.lib.di.LibsModule;
import com.jchaviel.soccerleaguesapp.schedule.ui.ScheduleFragment;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by jchavielreyes on 7/6/2016.
 */
@Singleton
@Component (modules = {ScheduleModule.class, DomainModule.class, LibsModule.class, SoccerLeaguesAppModule.class})
public interface ScheduleComponent {
    void inject(ScheduleFragment fragment);
}
