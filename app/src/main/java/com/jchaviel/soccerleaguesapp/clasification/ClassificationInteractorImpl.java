package com.jchaviel.soccerleaguesapp.clasification;

import com.jchaviel.soccerleaguesapp.entities.Team;

import java.util.ArrayList;

public class ClassificationInteractorImpl implements ClassificationInteractor{

    ClassificationRepository repository;

    public ClassificationInteractorImpl(ClassificationRepository repository) {
        this.repository = repository;
    }

    @Override
    public void subscribe(ArrayList<Team> teamList) {
        this.repository.subscribe(teamList);
    }

    @Override
    public void unsubscribe() {
        this.repository.unsubscribe();
    }
}
