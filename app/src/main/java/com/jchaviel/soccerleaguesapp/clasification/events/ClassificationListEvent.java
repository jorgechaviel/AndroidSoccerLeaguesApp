package com.jchaviel.soccerleaguesapp.clasification.events;

import com.jchaviel.soccerleaguesapp.entities.Team;

/**
 * Created by jchavielreyes on 8/17/18.
 */
public class ClassificationListEvent {
    private int type;
    private Team team;
    private String error;

    public final static int READ_EVENT = 0;
    public final static int DELETE_EVENT = 1;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
