package com.jchaviel.soccerleaguesapp.schedule;

import com.jchaviel.soccerleaguesapp.entities.Fixture;
import com.jchaviel.soccerleaguesapp.schedule.events.ScheduleListEvent;

import java.util.ArrayList;

/**
 * Created by jchavielreyes on 7/13/16.
 */
public interface SchedulePresenter {
    void onCreate();
    void onDestroy();

    void subscribe(String leagueName, ArrayList<Fixture> fixtureList, String month, String teamName);
    void unsubscribe();

    void onEventMainThread(ScheduleListEvent event); //cuando recibimos respuesta
}
