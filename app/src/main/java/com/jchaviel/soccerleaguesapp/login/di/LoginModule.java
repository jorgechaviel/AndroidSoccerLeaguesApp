package com.jchaviel.soccerleaguesapp.login.di;

import com.jchaviel.soccerleaguesapp.domain.FirebaseAPI;
import com.jchaviel.soccerleaguesapp.lib.base.EventBus;
import com.jchaviel.soccerleaguesapp.login.LoginInteractor;
import com.jchaviel.soccerleaguesapp.login.LoginInteractorImpl;
import com.jchaviel.soccerleaguesapp.login.LoginPresenter;
import com.jchaviel.soccerleaguesapp.login.LoginPresenterImpl;
import com.jchaviel.soccerleaguesapp.login.LoginRepository;
import com.jchaviel.soccerleaguesapp.login.LoginRepositoryImpl;
import com.jchaviel.soccerleaguesapp.login.ui.LoginView;

import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;

/**
 * Created by jchavielreyes on 7/4/16.
 */
@Module
public class LoginModule {

    LoginView view;

    public LoginModule(LoginView view) {
        this.view = view;
    }

    @Provides
    @Singleton
    LoginView providesLoginView(){
        return this.view;
    }

    @Provides
    @Singleton
    LoginPresenter providesLoginPresenter(EventBus eventBus, LoginView loginView, LoginInteractor loginInteractor){
        return new LoginPresenterImpl(eventBus, loginView, loginInteractor);
    }

    @Provides
    @Singleton
    LoginInteractor providesLoginInteractor(LoginRepository repository){
        return new LoginInteractorImpl(repository);
    }

    @Provides
    @Singleton
    LoginRepository providesLoginRepository(EventBus eventBus, FirebaseAPI firebaseAPI){
        return new LoginRepositoryImpl(eventBus, firebaseAPI);
    }
}
