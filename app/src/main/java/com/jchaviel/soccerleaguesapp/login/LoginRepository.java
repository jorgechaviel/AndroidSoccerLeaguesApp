package com.jchaviel.soccerleaguesapp.login;

/**
 * Created by jchavielreyes on 7/4/16.
 */
//repositorio que va a tener interacción con el backend
//la unica clase que sabe que estamos usando Firebase
public interface LoginRepository {
    void signUp(String email, String password);
    void signIn(String email, String password);
}
