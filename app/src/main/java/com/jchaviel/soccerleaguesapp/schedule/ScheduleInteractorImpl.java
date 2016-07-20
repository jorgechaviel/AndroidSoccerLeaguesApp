package com.jchaviel.soccerleaguesapp.schedule;

import com.jchaviel.soccerleaguesapp.entities.Fixture;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jchavielreyes on 7/14/16.
 */
public class ScheduleInteractorImpl implements ScheduleInteractor {

    private ScheduleRepository repository;

    public ScheduleInteractorImpl(ScheduleRepository repository) {
        this.repository = repository;
    }

    @Override
    public void unsubscribe() {
        repository.unsubscribe();
    }

    @Override
    public void subscribe(String leagueName, ArrayList<Fixture> fixtures, String month, String teamName) {
        repository.subscribe(leagueName, fixtures, month, teamName);
    }
}
