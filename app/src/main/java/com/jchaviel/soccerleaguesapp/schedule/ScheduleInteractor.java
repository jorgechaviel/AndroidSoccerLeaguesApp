package com.jchaviel.soccerleaguesapp.schedule;

import com.jchaviel.soccerleaguesapp.entities.Fixture;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jchavielreyes on 7/14/16.
 */
public interface ScheduleInteractor {
    void unsubscribe();

    void subscribe(String leagueName, ArrayList<Fixture> fixtures, String month, String teamName);
}
