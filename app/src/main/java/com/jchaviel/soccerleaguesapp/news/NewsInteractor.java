package com.jchaviel.soccerleaguesapp.news;

import com.jchaviel.soccerleaguesapp.entities.New;

import java.util.ArrayList;

/**
 * Created by jchavielreyes on 7/14/16.
 */
public interface NewsInteractor {
    void unsubscribe();

    void subscribe(String leagueName, ArrayList<New> mNews, String teamName);
}
