package com.jchaviel.soccerleaguesapp.news;

import com.jchaviel.soccerleaguesapp.entities.New;
import com.jchaviel.soccerleaguesapp.lib.base.EventBus;
import com.jchaviel.soccerleaguesapp.news.events.NewsListEvent;
import com.jchaviel.soccerleaguesapp.news.ui.NewsView;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

/**
 * Created by jchavielreyes on 7/14/16.
 */
public class NewsPresenterImpl implements NewsPresenter {

    private EventBus eventBus;
    private NewsView view;
    private NewsInteractor interactor;
    private static final String EMPTY_LIST = "Listado vacío";

    public NewsPresenterImpl(EventBus eventBus, NewsView view, NewsInteractor interactor) {
        this.eventBus = eventBus;
        this.view = view;
        this.interactor = interactor;
    }

    @Override
    public void onCreate() {
        eventBus.register(this);
    }

    @Override
    public void onDestroy() {
        view = null;
        eventBus.unregister(this);
    }

    @Override
    public void subscribe(String leagueName, ArrayList<New> mNews, String teamName) {
        if (this.view != null) {
            view.hideList();
            view.showProgress();
        }
        interactor.subscribe(leagueName, mNews, teamName);
    }

    @Override
    public void unsubscribe() {
        interactor.unsubscribe();
    }

    @Override
    @Subscribe
    public void onEventMainThread(NewsListEvent event) {
        if(this.view != null){
            view.hideProgress();
            view.showList();
        }
        String error = event.getError();
        if(error != null){
            if(error.isEmpty()){
                view.onNewsError(EMPTY_LIST);
            }
            else {
                view.onNewsError(error);
            }
        } else {
            if(event.getType() == NewsListEvent.READ_EVENT){
                view.addNew(event.getObjNew());
            }
        }
    }
}
