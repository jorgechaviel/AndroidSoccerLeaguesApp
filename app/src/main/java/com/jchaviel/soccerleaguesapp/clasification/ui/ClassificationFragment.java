package com.jchaviel.soccerleaguesapp.clasification.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.jchaviel.soccerleaguesapp.R;
import com.jchaviel.soccerleaguesapp.SoccerLeaguesApp;
import com.jchaviel.soccerleaguesapp.clasification.ClassificationPresenter;
import com.jchaviel.soccerleaguesapp.clasification.ui.adapter.ClassificationAdapter;
import com.jchaviel.soccerleaguesapp.entities.Team;
import com.jchaviel.soccerleaguesapp.global.Constants;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by jchavielreyes On 12/07/2016.
 */
public class ClassificationFragment extends Fragment implements ClassificationView{


    @Bind(R.id.clubs_list)
    RecyclerView recyclerView;
    @Bind(R.id.progress_bar_table)
    ProgressBar progressBarTable;
    @Bind(R.id.container)
    RelativeLayout container;
    @Bind(R.id.table_heading)
    LinearLayout linearLayoutHeading;

    private BroadcastReceiver mReceiver;
    private LocalBroadcastManager mLocalBroadcastManager;
    private View mTableView;

    private ArrayList<Team> teamList;
    @Inject
    ClassificationAdapter adapter;
    @Inject
    ClassificationPresenter presenter;
    /**
     * On create, et broadcast receiver
     *
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupInjection();
        presenter.onCreate();
        setupBroadcastReceiver();
    }

    private void setupInjection() {
        SoccerLeaguesApp app = (SoccerLeaguesApp) getActivity().getApplication();
        app.getClassificationComponent(this, this).inject(this);
    }

    /**
     * Set broadcast receiver
     */
    private void setupBroadcastReceiver() {
        mReceiver = new BroadcastReceiver() {
            /**
             * On receive get new table data from web
             * @param context
             * @param intent
             */
            @Override
            public void onReceive(Context context, Intent intent) {
                adapter.clearTeams();
                teamList.clear();
                presenter.subscribe(teamList);
                adapter.notifyDataSetChanged();
            }
        };
        mLocalBroadcastManager = LocalBroadcastManager.getInstance
                (getActivity().getApplicationContext());
        mLocalBroadcastManager.registerReceiver(mReceiver, Constants.INTENT_FILTER);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mTableView = inflater.inflate(R.layout.clasification_fragment, container, false);
        ButterKnife.bind(this, mTableView);

        setupRecyclerView();

        adapter.clearTeams();
        teamList = new ArrayList<>();
        presenter.subscribe(teamList);
        return mTableView;
    }

    private void setupRecyclerView() {
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
    }

    /**
     * Unregister broadcast receiver
     */
    @Override
    public void onDestroy() {
        adapter.clearTeams();
        presenter.unsubscribe();
        presenter.onDestroy();
        super.onDestroy();
        mLocalBroadcastManager.unregisterReceiver(mReceiver);
    }

    @Override
    public void addTeam(Team team) {
        adapter.addTeam(team);
    }

    @Override
    public void onClassificationError(String error) {
        Snackbar.make(container, error, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showTeamList() {
        recyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBarTable.setVisibility(View.GONE);
    }

    @Override
    public void showProgress() {
        if(progressBarTable != null)
            progressBarTable.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideHeading() {
        if(linearLayoutHeading != null)
            linearLayoutHeading.setVisibility(View.GONE);
    }

    @Override
    public void showHeading() {
        linearLayoutHeading.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideTeamList() {
        if(recyclerView != null)
            recyclerView.setVisibility(View.GONE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
