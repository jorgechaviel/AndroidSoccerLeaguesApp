package com.jchaviel.soccerleaguesapp.schedule.ui;

import com.jchaviel.soccerleaguesapp.entities.Fixture;

/**
 * Created by jchavielreyes on 7/18/16.
 */
public interface ScheduleView {

    void addFixture(Fixture fixture);

    void onFixtureError(String error);

    void showList();

    void hideProgress();

    void showProgress();

    void hideList();
}
