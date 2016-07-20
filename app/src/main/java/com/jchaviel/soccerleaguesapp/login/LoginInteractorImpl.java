package com.jchaviel.soccerleaguesapp.login;

/**
 * Created by jchavielreyes on 7/4/16.
 */

public class LoginInteractorImpl implements LoginInteractor{

    private LoginRepository loginRepository;

    public LoginInteractorImpl(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    @Override
    public void doSignUp(String email, String password) {
        loginRepository.signUp(email,password);
    }

    @Override
    public void doSignIn(String email, String password) {
        loginRepository.signIn(email,password);
    }
}
