package com.jchaviel.soccerleaguesapp.login;

import com.jchaviel.soccerleaguesapp.login.events.LoginEvent;

/**
 * Created by jchavielreyes on 7/4/16.
 */

public interface LoginPresenter {
    void onCreated();
    void onDestroy();
    void validateLogin(String email, String password);
    void registerNewUser(String email, String password);

    //para recibir los eventos
    void onEventMainThread(LoginEvent event);
}
