package com.jchaviel.soccerleaguesapp.login.di;

import com.jchaviel.soccerleaguesapp.SoccerLeaguesAppModule;
import com.jchaviel.soccerleaguesapp.domain.di.DomainModule;
import com.jchaviel.soccerleaguesapp.lib.di.LibsModule;
import com.jchaviel.soccerleaguesapp.login.ui.LoginActivity;
import javax.inject.Singleton;
import dagger.Component;

/**
 * Created by jchavielreyes on 7/4/16.
 */

//DomainModule como tengo un FirebaseAPI
//LibsModule porque tengo un EventBus
@Singleton
@Component(modules = {LoginModule.class, DomainModule.class, LibsModule.class, SoccerLeaguesAppModule.class})
public interface LoginComponent {
    void inject(LoginActivity activity);
}
