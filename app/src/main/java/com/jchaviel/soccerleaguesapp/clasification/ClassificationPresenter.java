package com.jchaviel.soccerleaguesapp.clasification;

import com.jchaviel.soccerleaguesapp.clasification.events.ClassificationListEvent;
import com.jchaviel.soccerleaguesapp.entities.Team;
import java.util.ArrayList;

/**
 * Created by jchavielreyes on 8/17/18.
 */
public interface ClassificationPresenter {
    void onCreate();
    void onDestroy();

    void subscribe(ArrayList<Team> teamList, boolean isConnected);
    void unsubscribe();

    void onEventMainThread(ClassificationListEvent event); //cuando recibimos respuesta
}
