package com.jchaviel.soccerleaguesapp.clasification;

import com.jchaviel.soccerleaguesapp.clasification.events.ClassificationListEvent;
import com.jchaviel.soccerleaguesapp.clasification.ui.ClassificationView;
import com.jchaviel.soccerleaguesapp.entities.Team;
import com.jchaviel.soccerleaguesapp.lib.base.EventBus;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

public class ClassificationPresenterImpl implements ClassificationPresenter {

    private EventBus eventBus;
    private ClassificationView view;
    private ClassificationInteractor interactor;

    public ClassificationPresenterImpl(EventBus eventBus, ClassificationView view, ClassificationInteractor interactor) {
        this.eventBus = eventBus;
        this.view = view;
        this.interactor = interactor;
    }

    @Override
    public void onCreate() {
        this.eventBus.register(this);
    }

    @Override
    public void onDestroy() {
        view = null;
        eventBus.unregister(this);
    }

    @Override
    public void subscribe(ArrayList<Team> teamList) {
        if(this.view != null){
            view.hideTeamList();
            view.hideHeading();
            view.showProgress();
        }
        this.interactor.subscribe(teamList);
    }

    @Override
    public void unsubscribe() {
        this.interactor.unsubscribe();
    }

    @Override
    @Subscribe
    public void onEventMainThread(ClassificationListEvent event) {
        if(this.view != null){
            view.hideProgress();
            view.showHeading();
            view.showTeamList();
        }
        String error = event.getError();
        if(error != null){
            view.onClassificationError(error);
        } else {
            if(event.getType() == ClassificationListEvent.READ_EVENT)
                view.addTeam(event.getTeam());
        }
    }
}
