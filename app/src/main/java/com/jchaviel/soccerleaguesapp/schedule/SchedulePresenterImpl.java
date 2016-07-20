package com.jchaviel.soccerleaguesapp.schedule;

import com.jchaviel.soccerleaguesapp.entities.Fixture;
import com.jchaviel.soccerleaguesapp.lib.base.EventBus;
import com.jchaviel.soccerleaguesapp.schedule.events.ScheduleListEvent;
import com.jchaviel.soccerleaguesapp.schedule.ui.ScheduleView;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jchavielreyes on 7/14/16.
 */
public class SchedulePresenterImpl implements SchedulePresenter {

    private EventBus eventBus;
    private ScheduleView view;
    private ScheduleInteractor interactor;
    private static final String EMPTY_LIST = "Listado vacío";

    public SchedulePresenterImpl(EventBus eventBus, ScheduleView view, ScheduleInteractor interactor) {
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
    public void subscribe(String leagueName, ArrayList<Fixture> fixtureList, String month, String teamName) {
        if (this.view != null) {
            view.hideList();
            view.showProgress();
        }
        interactor.subscribe(leagueName, fixtureList, month, teamName);
    }

    @Override
    public void unsubscribe() {
        interactor.unsubscribe();
    }

    @Override
    @Subscribe
    public void onEventMainThread(ScheduleListEvent event) {
        if(this.view != null){
            view.hideProgress();
            view.showList();
        }
        String error = event.getError();
        if(error != null){
            if(error.isEmpty()){
                view.onFixtureError(EMPTY_LIST);
            }
            else {
                view.onFixtureError(error);
            }
        } else {
            if(event.getType() == ScheduleListEvent.READ_EVENT){
                view.addFixture(event.getFixture());
            }
        }
    }
}
