package com.jchaviel.soccerleaguesapp.news.di;

import com.jchaviel.soccerleaguesapp.SoccerLeaguesAppModule;
import com.jchaviel.soccerleaguesapp.domain.di.DomainModule;
import com.jchaviel.soccerleaguesapp.lib.di.LibsModule;
import com.jchaviel.soccerleaguesapp.news.ui.NewsFragment;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by jchavielreyes on 7/6/2016.
 */
@Singleton
@Component (modules = {NewsModule.class, DomainModule.class, LibsModule.class, SoccerLeaguesAppModule.class})
public interface NewsComponent {
    void inject(NewsFragment fragment);
}
