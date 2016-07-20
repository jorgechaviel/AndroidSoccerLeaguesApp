package com.jchaviel.soccerleaguesapp.main;

/**
 * Created by jchavielreyes on 7/15/16.
 */
public class SessionInteractorImpl implements SessionInteractor {
    private MainRepository repository;

    public SessionInteractorImpl(MainRepository repository) {
        this.repository = repository;
    }

    @Override
    public void logout() {
        repository.logout();
    }
}
