package com.jchaviel.soccerleaguesapp.schedule.events;

import com.jchaviel.soccerleaguesapp.entities.Fixture;

/**
 * Created by jchavielreyes on 7/18/16.
 */
public class ScheduleListEvent {
    private int type;
    private Fixture fixture;
    private String error;

    public final static int READ_EVENT = 0;
    public final static int DELETE_EVENT = 1;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Fixture getFixture() {
        return fixture;
    }

    public void setFixture(Fixture fixture) {
        this.fixture = fixture;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
