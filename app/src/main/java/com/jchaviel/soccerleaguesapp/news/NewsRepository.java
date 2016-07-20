package com.jchaviel.soccerleaguesapp.news;

import com.jchaviel.soccerleaguesapp.entities.New;

import java.util.ArrayList;

/**
 * Created by jchavielreyes on 7/14/16.
 */
public interface NewsRepository {
    void subscribe(String leagueName, ArrayList<New> mNews, String teamName);

    void unsubscribe();
}
