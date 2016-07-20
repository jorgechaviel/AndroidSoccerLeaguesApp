package com.jchaviel.soccerleaguesapp.main.ui.adapter;

import android.os.AsyncTask;

/**
 * Created by jchavielreyes on 7/13/16.
 */
public interface DataStorage {

    AsyncTask<Object, Object, Void> load();
    String getTableLink(int itemPosition);
    void getTeamsData(int itemPosition);
}
