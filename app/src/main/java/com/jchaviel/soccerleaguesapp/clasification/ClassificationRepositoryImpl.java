package com.jchaviel.soccerleaguesapp.clasification;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import com.jchaviel.soccerleaguesapp.clasification.events.ClassificationListEvent;
import com.jchaviel.soccerleaguesapp.domain.FirebaseAPI;
import com.jchaviel.soccerleaguesapp.entities.Team;
import com.jchaviel.soccerleaguesapp.global.Global;
import com.jchaviel.soccerleaguesapp.lib.base.EventBus;
import java.util.ArrayList;
import java.util.List;

public class ClassificationRepositoryImpl implements ClassificationRepository{

    private FirebaseAPI firebase;
    private EventBus eventBus;
    private List<Team> teamList;

    public ClassificationRepositoryImpl(FirebaseAPI firebase, EventBus eventBus) {
        this.firebase = firebase;
        this.eventBus = eventBus;
    }

    @Override
    public void subscribe(ArrayList<Team> teamList, boolean isConnected) {
        if(isConnected) {
            loadTeams().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,
                    teamList);
        }
    }

    @Override
    public void unsubscribe() {
        //TODO:
    }

    private void post(int type, String error) {
        post(type, error, null);
    }

    private void post(int type, Team team) {
        post(type, null, team);
    }

    private void post(int type, String error, Team team) {
        ClassificationListEvent event = new ClassificationListEvent();
        event.setType(type);
        event.setError(error);
        event.setTeam(team);
        eventBus.post(event);
    }

    @SuppressLint("StaticFieldLeak")
    public AsyncTask<Object, Void, Void> loadTeams() {
        return new AsyncTask<Object, Void, Void>(){

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                teamList = new ArrayList<>();
            }

            @Override
            protected Void doInBackground(Object... objects) {
                while (true) {
                    if (!Global.teamList.isEmpty()) break;
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                for (Team team: Global.teamList) {
                    post(ClassificationListEvent.READ_EVENT, team);
                }
            }
        };
    }
}
