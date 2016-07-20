package com.jchaviel.soccerleaguesapp.news;

import com.jchaviel.soccerleaguesapp.entities.New;
import com.jchaviel.soccerleaguesapp.news.events.NewsListEvent;

import java.util.ArrayList;

/**
 * Created by jchavielreyes on 7/13/16.
 */
public interface NewsPresenter {
    void onCreate();
    void onDestroy();

    void subscribe(String leagueName, ArrayList<New> mNews, String teamName);
    void unsubscribe();

    void onEventMainThread(NewsListEvent event); //cuando recibimos respuesta
}
