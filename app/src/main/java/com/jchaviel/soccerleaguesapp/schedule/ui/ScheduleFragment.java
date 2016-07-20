package com.jchaviel.soccerleaguesapp.schedule.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.jchaviel.soccerleaguesapp.R;
import com.jchaviel.soccerleaguesapp.SoccerLeaguesApp;
import com.jchaviel.soccerleaguesapp.entities.Fixture;
import com.jchaviel.soccerleaguesapp.global.Constants;
import com.jchaviel.soccerleaguesapp.global.Global;
import com.jchaviel.soccerleaguesapp.schedule.SchedulePresenter;
import com.jchaviel.soccerleaguesapp.schedule.ui.adapter.ScheduleAdapter;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ScheduleFragment extends Fragment implements ScheduleView {

    private final String TEAM_ALL = "all";
    private final String KET_TIME = "time";
    @Bind(R.id.fixture_list)
    RecyclerView recyclerView;
    @Bind(R.id.progress_bar_schedule)
    ProgressBar progressBarSchedule;
    @Bind(R.id.container)
    RelativeLayout container;
    @Bind(R.id.teams_spinner)
    Spinner teamsSpinner;
    @Bind(R.id.months_spinner)
    Spinner monthsSpinner;
    @Bind(R.id.no_fixture_data)
    TextView noFixtureData;

    private BroadcastReceiver mReceiver; // To detect league name change broadcast 
    private LocalBroadcastManager mLocalBroadcastManager;

    private View mScheduleView; // Schedule page

    private final String KEY_FIXTURES = "fixtures";
    private ArrayAdapter mTeamsSpinnerAdapter;
    private ArrayAdapter mMonthsSpinnerAdapter;

    private final int INDEX_ZERO = 0;
    private ArrayList<Fixture> fixtureList;

    @Inject
    ScheduleAdapter scheduleAdapter;
    @Inject
    SchedulePresenter presenter;

    /**
     * on create, set up broadcast receiver
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
        app.getScheduleComponent(this, this).inject(this);
    }

    /**
     * Get action bar title
     *
     * @return
     */
    private String getActionBarTitle() {
        return ((AppCompatActivity) getActivity()).getSupportActionBar().getTitle().toString();
    }

    /**
     * Set up schedule view
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mScheduleView = inflater.inflate(R.layout.schedule_fragment, container, false);
        ButterKnife.bind(this, mScheduleView);

        setupRecyclerView();

        setMonthsSpinner(savedInstanceState);
        setTeamsSpinner(savedInstanceState);

        //If fragment has been created before and league hasnt changed since, load saved data from bundle
        if (savedInstanceState != null
                && savedInstanceState.containsKey(KEY_FIXTURES)
                && ((String) savedInstanceState.getString(Constants.KEY_ACTION_BAR_TITLE))
                .equals(getActionBarTitle())) {
            fixtureList = (ArrayList<Fixture>) savedInstanceState.getSerializable(KEY_FIXTURES);

        } else { //Fetch new data from web
            fixtureList = new ArrayList<Fixture>();
        }

        fetchData(); //Fetch data for teams

        return mScheduleView;
    }

    private void setupRecyclerView() {
        recyclerView.setClickable(true);
        recyclerView.setAdapter(scheduleAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
    }

    /**
     * Sets teams spinner
     *
     * @param savedInstanceState
     */
    private void setTeamsSpinner(Bundle savedInstanceState) {
        // Create an ArrayAdapter for to store teams for spinner
        mTeamsSpinnerAdapter = new ArrayAdapter(getActivity(),
                android.R.layout.simple_spinner_item, Global.sortedTeamNameList);
        mTeamsSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        teamsSpinner.setAdapter(mTeamsSpinnerAdapter);

        //If league name hasnt changed, load saved data from bundle
        if (savedInstanceState != null)
            teamsSpinner.setSelection(savedInstanceState.getInt(Constants.KEY_TEAMS_SPINNER), false);
        else teamsSpinner.setSelection(INDEX_ZERO, false);

        teamsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            /**Handles item touch events
             *
             * @param parent
             * @param view
             * @param position
             * @param id
             */
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                fixtureList.clear();
                fetchData();
                scheduleAdapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    /**
     * Sets up months spinner
     *
     * @param savedInstanceState
     */
    private void setMonthsSpinner(Bundle savedInstanceState) {

        mMonthsSpinnerAdapter = new ArrayAdapter(getActivity(),
                android.R.layout.simple_spinner_item, Constants.MONTHS);

        mMonthsSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        monthsSpinner.setAdapter(mMonthsSpinnerAdapter);

        //If fragment is has been created before and month hasnt changed since, load saved data from bundle
        if (savedInstanceState != null)
            monthsSpinner.setSelection(savedInstanceState.getInt(Constants.KEY_MONTHS_SPINNER), false);
        else monthsSpinner.setSelection(INDEX_ZERO, false);

        monthsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            /**Handle item selection events
             *
             * @param parent
             * @param view
             * @param position
             * @param id
             */
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                fixtureList.clear();
                fetchData();
                scheduleAdapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    /**
     * Set broadcasr receiver
     */
    private void setupBroadcastReceiver() {
        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                fixtureList.clear();
                fetchData();
                scheduleAdapter.notifyDataSetChanged();
            }
        };
        mLocalBroadcastManager = LocalBroadcastManager.getInstance
                (getActivity().getApplicationContext());
        mLocalBroadcastManager.registerReceiver(mReceiver, Constants.INTENT_FILTER);
    }

    /**
     * Saves desired data to spinners
     *
     * @param outState
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (!fixtureList.isEmpty()) outState.putSerializable(KEY_FIXTURES, fixtureList);
        String title = getActionBarTitle();
        outState.putString(Constants.KEY_ACTION_BAR_TITLE, title);
        outState.putInt(Constants.KEY_MONTHS_SPINNER, monthsSpinner.getSelectedItemPosition());
        outState.putInt(Constants.KEY_TEAMS_SPINNER, teamsSpinner.getSelectedItemPosition());
        super.onSaveInstanceState(outState);
    }

    /**
     * Starts async task with appropriate parameters to get new data from web
     */
    private void fetchData() {
        String leagueName = ((AppCompatActivity) getActivity())
                .getSupportActionBar().getTitle().toString();
        String month = monthsSpinner.getSelectedItem().toString();
        String team = "";
        //On creation of fragment, teams spinner will be empty. So select team to 'all'
        try {
            team = teamsSpinner.getSelectedItem().toString().trim();
        } catch (NullPointerException e) {
            team = TEAM_ALL;
        }
        noFixtureData.setVisibility(ListView.GONE);
        scheduleAdapter.clearFixtures();
        presenter.subscribe(leagueName, fixtureList, month, team);
    }

    /**
     * Unregister broadcast receiver
     */
    @Override
    public void onDestroy() {
        scheduleAdapter.clearFixtures();
        presenter.unsubscribe();
        presenter.onDestroy();
        super.onDestroy();

        mLocalBroadcastManager.unregisterReceiver(mReceiver);
    }

    @Override
    public void addFixture(Fixture fixture) {
        scheduleAdapter.addFixture(fixture);
    }

    @Override
    public void onFixtureError(String error) {
        Snackbar.make(container, error, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showList() {
        recyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBarSchedule.setVisibility(View.GONE);
    }

    @Override
    public void showProgress() {
        progressBarSchedule.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideList() {
        recyclerView.setVisibility(View.GONE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

}
