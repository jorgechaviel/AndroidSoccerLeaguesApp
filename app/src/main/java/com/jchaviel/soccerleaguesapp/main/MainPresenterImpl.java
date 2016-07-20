package com.jchaviel.soccerleaguesapp.main;

import com.jchaviel.soccerleaguesapp.lib.base.EventBus;
import com.jchaviel.soccerleaguesapp.main.events.MainEvent;
import com.jchaviel.soccerleaguesapp.main.ui.MainView;

import org.greenrobot.eventbus.Subscribe;

/**
 * Created by jchavielreyes on 7/15/16.
 */
public class MainPresenterImpl implements MainPresenter {

    private MainView view;
    private EventBus eventBus;
    private SessionInteractor sessionInteractor;

    public MainPresenterImpl(MainView view, EventBus eventBus, SessionInteractor sessionInteractor) {
        this.view = view;
        this.eventBus = eventBus;
        this.sessionInteractor = sessionInteractor;
    }

    @Override
    public void onCreate() {
        eventBus.register(this);
    }

    @Override
    public void onResume() {

    }

    @Override
    public void onDestroy() {
        view = null;
        eventBus.unregister(this);
    }

    @Override
    public void logout() {
        sessionInteractor.logout();
    }

    @Override
    @Subscribe
    public void onEventMainThread(MainEvent event) {

    }
}
