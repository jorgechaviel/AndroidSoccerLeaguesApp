package com.jchaviel.soccerleaguesapp.news.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.jchaviel.soccerleaguesapp.R;
import com.jchaviel.soccerleaguesapp.SoccerLeaguesApp;
import com.jchaviel.soccerleaguesapp.entities.New;
import com.jchaviel.soccerleaguesapp.global.Constants;
import com.jchaviel.soccerleaguesapp.global.Global;
import com.jchaviel.soccerleaguesapp.news.NewsPresenter;
import com.jchaviel.soccerleaguesapp.news.ui.adapter.NewsAdapter;
import com.jchaviel.soccerleaguesapp.news.ui.adapter.OnItemClickListener;
import java.util.ArrayList;
import javax.inject.Inject;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by jchavielreyes On 12/07/2016.
 */
public class NewsFragment extends Fragment implements NewsView, OnItemClickListener {

    @Bind(R.id.teams_spinner_news)
    Spinner teamsSpinnerNews;
    @Bind(R.id.news_list)
    RecyclerView recyclerView;
    @Bind(R.id.progress_bar_news)
    ProgressBar progressBarNews;
    @Bind(R.id.container)
    RelativeLayout container;

    private BroadcastReceiver mReceiver;
    private LocalBroadcastManager mLocalBroadcastManager;
    private ArrayList<New> mNews;
    private View mNewsView;
    private final String KEY_NEWS = "new";
    private ArrayAdapter mTeamsSpinnerAdapter;

    @Inject
    NewsAdapter newsAdapter;
    @Inject
    NewsPresenter presenter;

    /**
     * Setup image viewer and broadcast receiver
     *
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupInjection();
        presenter.onCreate();
        setupBroadcastReceiver();
        setRetainInstance(true);
    }

    private void setupInjection() {
        SoccerLeaguesApp app = (SoccerLeaguesApp) getActivity().getApplication();
        app.getNewsComponent(this, this, this).inject(this);
    }

    private String getActionBarTitle() {
        return ((AppCompatActivity) getActivity()).getSupportActionBar().getTitle().toString();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mNewsView = inflater.inflate(R.layout.news_fragment, container, false);
        ButterKnife.bind(this, mNewsView);
        setupRecyclerView();
        setTeamsSpinner(savedInstanceState);

        //Set data from bundle if league hasnt changed
        if (savedInstanceState != null
                && savedInstanceState.containsKey(KEY_NEWS)
                && ((String) savedInstanceState.getString(Constants.KEY_ACTION_BAR_TITLE))
                .equals(getActionBarTitle())) {
            mNews = (ArrayList<New>) savedInstanceState.getSerializable(KEY_NEWS);

        } else {
            mNews = new ArrayList<New>();
        }
        fetchData();
        return mNewsView;
    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(newsAdapter);
        recyclerView.setClickable(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
    }

    /**
     * Set teams spinner
     *
     * @param savedInstanceState
     */
    private void setTeamsSpinner(Bundle savedInstanceState) {
        // Create an ArrayAdapter for to store teams for spinner

        mTeamsSpinnerAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, Global.sortedTeamNameList);
        mTeamsSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        teamsSpinnerNews.setAdapter(mTeamsSpinnerAdapter);

        //Set data from bundle if league hasnt changed
        if (savedInstanceState != null)
            teamsSpinnerNews.setSelection(savedInstanceState.getInt(Constants.KEY_TEAMS_SPINNER), false);
        else teamsSpinnerNews.setSelection(0, false);

        teamsSpinnerNews.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            /**Handle spinner item selection events
             *
             * @param parent
             * @param view
             * @param position
             * @param id
             */
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mNews.clear();
                fetchData();
                newsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    /**
     * Set broadcast receiver
     */
    private void setupBroadcastReceiver() {
        mReceiver = new BroadcastReceiver() {
            /**On event received, get new data from web
             *
             * @param context
             * @param intent
             */
            @Override
            public void onReceive(Context context, Intent intent) {
                mNews.clear();
                fetchData();
                newsAdapter.notifyDataSetChanged();
            }
        };
        mLocalBroadcastManager = LocalBroadcastManager.getInstance
                (getActivity().getApplicationContext());
        mLocalBroadcastManager.registerReceiver(mReceiver, Constants.INTENT_FILTER);
    }

    /**
     * Save data in bundle
     *
     * @param outState
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (!mNews.isEmpty()) outState.putSerializable(KEY_NEWS, mNews);
        String title = getActionBarTitle();
        outState.putString(Constants.KEY_ACTION_BAR_TITLE, title);
        outState.putInt(Constants.KEY_TEAMS_SPINNER, teamsSpinnerNews.getSelectedItemPosition());
        super.onSaveInstanceState(outState);
    }

    /**
     * Starts async task to get new news data using appropriate parameters
     */
    private void fetchData() {
        String leagueName = ((AppCompatActivity) getActivity())
                .getSupportActionBar().getTitle().toString();

        String team = "";
        try {
            team = teamsSpinnerNews.getSelectedItem().toString().trim();
        } catch (Exception e) {
            team = "all";
        }

        newsAdapter.clearNews();
        presenter.subscribe(leagueName, mNews, team);
    }

    /**
     * Unregister broadcast receiver
     */
    @Override
    public void onDestroy() {
        teamsSpinnerNews.setSelection(mTeamsSpinnerAdapter.getPosition("All"));
        newsAdapter.clearNews();
        presenter.unsubscribe();
        presenter.onDestroy();
        super.onDestroy();
        mLocalBroadcastManager.unregisterReceiver(mReceiver);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void addNew(New objNew) {
        newsAdapter.addNew(objNew);
    }

    @Override
    public void onNewsError(String error) {
        Snackbar.make(container, error, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showList() {
        if(recyclerView != null)
            recyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        if(progressBarNews != null)
            progressBarNews.setVisibility(ProgressBar.GONE);
    }

    @Override
    public void showProgress() {
        if(progressBarNews != null)
            progressBarNews.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideList() {
        if(recyclerView != null)
            recyclerView.setVisibility(View.GONE);
    }

    @Override
    public void onItemClick(New objNews) {
        String url = objNews.getLink();
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        //es posible que no haya forma de resolver esto
        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}
