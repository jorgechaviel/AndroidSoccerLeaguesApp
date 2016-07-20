package com.jchaviel.soccerleaguesapp.news.events;

import com.jchaviel.soccerleaguesapp.entities.New;

/**
 * Created by jchavielreyes on 7/14/16.
 */
public class NewsListEvent {
    private int type;
    private New objNew;
    private String error;

    public final static int READ_EVENT = 0;
    public final static int DELETE_EVENT = 1;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public New getObjNew() {
        return objNew;
    }

    public void setObjNew(New objNew) {
        this.objNew = objNew;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
