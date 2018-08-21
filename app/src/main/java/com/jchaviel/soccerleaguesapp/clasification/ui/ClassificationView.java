package com.jchaviel.soccerleaguesapp.clasification.ui;

import com.jchaviel.soccerleaguesapp.entities.Team;

/**
 * Created by jchavielreyes on 8/17/18.
 */
public interface ClassificationView {

    void addTeam(Team team);

    void onClassificationError(String error);

    void showTeamList();

    void hideProgress();

    void showProgress();

    void hideHeading();

    void showHeading();

    void hideTeamList();
}
