package com.jchaviel.soccerleaguesapp.clasification.di;

import com.jchaviel.soccerleaguesapp.SoccerLeaguesAppModule;
import com.jchaviel.soccerleaguesapp.clasification.ui.ClassificationFragment;
import com.jchaviel.soccerleaguesapp.domain.di.DomainModule;
import com.jchaviel.soccerleaguesapp.lib.di.LibsModule;
import javax.inject.Singleton;
import dagger.Component;

@Singleton
@Component(modules = {ClassificationModule.class, DomainModule.class, LibsModule.class, SoccerLeaguesAppModule.class})
public interface ClassificationComponent {
    void inject(ClassificationFragment fragment);
}
