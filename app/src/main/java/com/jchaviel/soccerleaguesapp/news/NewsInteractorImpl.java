package com.jchaviel.soccerleaguesapp.news;

import com.jchaviel.soccerleaguesapp.entities.New;

import java.util.ArrayList;

/**
 * Created by jchavielreyes on 7/14/16.
 */
public class NewsInteractorImpl implements NewsInteractor {

    private NewsRepository repository;

    public NewsInteractorImpl(NewsRepository repository) {
        this.repository = repository;
    }

    @Override
    public void unsubscribe() {
        repository.unsubscribe();
    }

    @Override
    public void subscribe(String leagueName, ArrayList<New> mNews, String teamName) {
        repository.subscribe(leagueName, mNews, teamName);
    }
}
