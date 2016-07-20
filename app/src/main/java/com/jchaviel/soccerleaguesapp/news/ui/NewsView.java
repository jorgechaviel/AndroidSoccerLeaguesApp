package com.jchaviel.soccerleaguesapp.news.ui;

import com.jchaviel.soccerleaguesapp.entities.New;

/**
 * Created by jchavielreyes on 7/14/16.
 */
public interface NewsView {
    void onItemClick(New objNews);

    void addNew(New objNew);

    void onNewsError(String error);

    void showList();

    void hideProgress();

    void showProgress();

    void hideList();
}
