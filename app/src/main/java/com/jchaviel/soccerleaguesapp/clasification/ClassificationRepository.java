package com.jchaviel.soccerleaguesapp.clasification;

import com.jchaviel.soccerleaguesapp.entities.Team;

import java.util.ArrayList;

public interface ClassificationRepository {
    void subscribe(ArrayList<Team> teamList, boolean isConnected);
    void unsubscribe();
}
