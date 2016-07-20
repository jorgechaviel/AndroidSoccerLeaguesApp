package com.jchaviel.soccerleaguesapp.main;

import com.jchaviel.soccerleaguesapp.domain.FirebaseAPI;

/**
 * Created by jchavielreyes on 7/15/16.
 */
public class MainRepositoryImpl implements MainRepository {

    private FirebaseAPI firebaseAPI;

    public MainRepositoryImpl(FirebaseAPI firebaseAPI) {
        this.firebaseAPI = firebaseAPI;
    }

    @Override
    public void logout() {
        firebaseAPI.logout();
    }
}
