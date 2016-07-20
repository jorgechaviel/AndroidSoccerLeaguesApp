package com.jchaviel.soccerleaguesapp.main;

import com.jchaviel.soccerleaguesapp.main.events.MainEvent;

/**
 * Created by jchavielreyes on 7/13/16.
 */
public interface MainPresenter {
    void onCreate();
    void onResume();
    void onDestroy();

    void logout();
    void onEventMainThread(MainEvent event);
}
