package com.jchaviel.soccerleaguesapp.login;

/**
 * Created by jchavielreyes on 7/4/16.
 */

public interface LoginInteractor {
    void doSignUp(String email, String password);
    void doSignIn(String email, String password);
}
