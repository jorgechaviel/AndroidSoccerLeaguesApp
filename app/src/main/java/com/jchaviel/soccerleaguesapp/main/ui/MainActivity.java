package com.jchaviel.soccerleaguesapp.main.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.jchaviel.soccerleaguesapp.R;
import com.jchaviel.soccerleaguesapp.SoccerLeaguesApp;
import com.jchaviel.soccerleaguesapp.clasification.ui.ClasificationFragment;
import com.jchaviel.soccerleaguesapp.global.Constants;
import com.jchaviel.soccerleaguesapp.global.Global;
import com.jchaviel.soccerleaguesapp.global.SlidingTabLayout;
import com.jchaviel.soccerleaguesapp.login.ui.LoginActivity;
import com.jchaviel.soccerleaguesapp.main.MainPresenter;
import com.jchaviel.soccerleaguesapp.main.ui.adapter.ClasificationDataStorage;
import com.jchaviel.soccerleaguesapp.main.ui.adapter.FragmentsPagerAdapter;
import com.jchaviel.soccerleaguesapp.navigationdrawer.ui.NavigationDrawerFragment;
import com.jchaviel.soccerleaguesapp.news.ui.NewsFragment;
import com.jchaviel.soccerleaguesapp.schedule.ui.ScheduleFragment;

import java.util.Arrays;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by jchavielreyes On 12/07/2016.
 */
public class MainActivity extends AppCompatActivity implements MainView {

    private static final String CLEAR_MESSAGE = "teamlist cleared";
    private static final String FALSE = "false";
    private static final String PREMIER_LEAGUE = "Barclays Premier League";
    private static final String LOG_KEY = "test";
    @Bind(R.id.tabs) SlidingTabLayout mTabs;
    @Bind(R.id.landing_activity_pager) ViewPager mViewPager;
    @Bind(R.id.drawer_layout) DrawerLayout mDrawerLayout;

    private FragmentManager mFragmentManager;
    private ActionBarDrawerToggle mActionBarDrawerToggle;
    private NavigationDrawerFragment mNavigationDrawerFragment;
    // To manage broadcast messages
    private LocalBroadcastManager mLocalBroadcastManager;
    private SoccerLeaguesApp app;

    @Inject
    MainPresenter presenter;
    @Inject
    FragmentsPagerAdapter pagerAdapter;

    // Fields to check whether app is being launched for first time
    private boolean mUserAwareOfDrawer;
    private boolean mStartingFirstTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null)
            getSupportActionBar().setTitle(savedInstanceState.getString(Constants.KEY_ACTION_BAR_TITLE));
        else getSupportActionBar().setTitle(PREMIER_LEAGUE);

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setupToolbar();

        // Set broadcast manager
        mLocalBroadcastManager = LocalBroadcastManager.getInstance(getApplicationContext());

        // Setup side navigation
        setupNavigationDrawer(savedInstanceState);

        //Set home button to launch drawer;
        setHomeButton();

        app = (SoccerLeaguesApp) getApplication();
        setupInjection();
        setupNavitagion();
        presenter.onCreate();
    }

    private void setupInjection() {
        String[] titles = new String[]{ getString(R.string.tab_name_clasification),
                getString(R.string.tab_name_matches),
                getString(R.string.tab_name_news)};

        Fragment[] fragments = new Fragment[]{ new ClasificationFragment(),
                                               new ScheduleFragment(),
                                               new NewsFragment()};
        app.getMainComponent(this, getSupportFragmentManager(), fragments, titles).inject(this);
    }


    private void setupNavitagion() {
        // Setup view pager to manage tabs
        mFragmentManager = getSupportFragmentManager();
//        mViewPager.setAdapter(new FragmentsPagerAdapter(mFragmentManager));
        mViewPager.setAdapter(pagerAdapter);

        // Set view pager to tabs
        mTabs.setViewPager(mViewPager);

        // Set tabs to be displayed evenly
        mTabs.setDistributeEvenly(true);
    }

    private void setupToolbar() {
        //Set action bar
        Toolbar actionBarToolbar = (Toolbar) findViewById(R.id.action_bar);
        actionBarToolbar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.orange)));
        actionBarToolbar.setTitleTextColor(Color.WHITE);
    }

    /**
     * Sets home button to options button
     */
    private void setHomeButton() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    /**
     * On resume, fetchs table data
     */
    @Override
    protected void onResume() {
        super.onResume();
        new ClasificationDataStorage().load().execute(getLeagueIndex(getSupportActionBar().getTitle().toString()));
    }

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

    /**
     * Save objects to bundle
     *
     * @param outState
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString(Constants.KEY_ACTION_BAR_TITLE, getSupportActionBar().getTitle().toString());

        super.onSaveInstanceState(outState);
    }

    /**
     * Create options menu
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     * Setup oprions item select
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Let the drawer toggle to manage home button click
        if (item.getItemId() == android.R.id.home) {
            if (mActionBarDrawerToggle.onOptionsItemSelected(item)) {
                return true;
            }
        }

        if (item.getItemId() == R.id.action_logout) {
            logout();
        }

        return super.onOptionsItemSelected(item);
    }

    private void logout() {
        presenter.logout();
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    /**
     * Setup navigation drawer
     *
     * @param savedInstanceState
     */
    public void setupNavigationDrawer(Bundle savedInstanceState) {

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);

        // Check if user is running app for very first time
        mUserAwareOfDrawer = Boolean.valueOf(readFromPreferences(getApplicationContext(),
                Constants.KEY_USER_AWARE_OF_DRAWER, FALSE));

        // Check if activity is being created first time
        mStartingFirstTime = savedInstanceState != null;

        //Setup drawer toggle
        setupDrawerToggle();

        // Check if app is running first time and drawer has not been opened
        // before during life cycle of app
        if (!mUserAwareOfDrawer && !mStartingFirstTime) {
            mDrawerLayout.openDrawer(mNavigationDrawerFragment.getView());
        }

        // Set listener to drawer layout
        mDrawerLayout.setDrawerListener(mActionBarDrawerToggle);

        // Sync action bar with drawer
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mActionBarDrawerToggle.syncState();
            }
        });
        // Setup navigation fragment
        mNavigationDrawerFragment.setup(mDrawerLayout);
    }

    /**
     * Setup drawer toggle
     */
    private void setupDrawerToggle() {
        mActionBarDrawerToggle = new ActionBarDrawerToggle(MainActivity.this, mDrawerLayout,
                R.string.drawer_open, R.string.drawer_close) {

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if (!mUserAwareOfDrawer) { //Checks if student is launchig app for first time
                    mUserAwareOfDrawer = true;
                    saveToPreferences(getApplicationContext(),
                            Constants.KEY_USER_AWARE_OF_DRAWER, mUserAwareOfDrawer + "");
                }
            }

            /**Manage drawer close event
             *
             * @param drawerView
             */
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                if (Global.selectedLeagueName == null) return;

                String currentActionBarTitle = getSupportActionBar().getTitle().toString();

                // If same league that is displayed, is tapped, don't do anything
                if (currentActionBarTitle.equals(Global.selectedLeagueName)) return;
                else {
                    getSupportActionBar().setTitle(Global.selectedLeagueName);

                    //Reset global team list
                    Global.teamList.clear();
                    Log.d(LOG_KEY, CLEAR_MESSAGE);
                    Global.teamNameList.clear();
                    Global.sortedTeamNameList.clear();

                    //Fetch new data
                    new ClasificationDataStorage().load().execute(getLeagueIndex(Global.selectedLeagueName));

                    // Send broadcast to update data in fragments
                    mLocalBroadcastManager.sendBroadcast
                            (new Intent(Constants.LEAGUE_CHANGED_FILTER));
                }
            }
        };
    }

    /**
     * Given the league name, return index position of it in list of leagues
     *
     * @param currentActionBarTitle
     * @return
     */
    private int getLeagueIndex(String currentActionBarTitle) {
        return Arrays.asList(Constants.LEAGUE_NAMES).indexOf(currentActionBarTitle);
    }

    /**
     * Saves information about whether app is being run for first time
     *
     * @param context
     * @param preferenceKey
     * @param preferenceValue
     */
    private void saveToPreferences(Context context, String preferenceKey, String preferenceValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences
                (Constants.DRAWER_PREF_FILE_NAME, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(preferenceKey, preferenceValue);
        editor.apply();
    }

    /**
     * Reads information about whether app is being run for first time
     *
     * @param context
     * @param preferenceKey
     * @param defaultValue
     * @return
     */
    private String readFromPreferences(Context context, String preferenceKey, String defaultValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences
                (Constants.DRAWER_PREF_FILE_NAME, Context.MODE_PRIVATE);

        return sharedPreferences.getString(preferenceKey, defaultValue);
    }
}
