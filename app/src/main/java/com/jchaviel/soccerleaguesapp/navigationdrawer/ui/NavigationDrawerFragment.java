package com.jchaviel.soccerleaguesapp.navigationdrawer.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import com.jchaviel.soccerleaguesapp.R;
import com.jchaviel.soccerleaguesapp.SoccerLeaguesApp;
import com.jchaviel.soccerleaguesapp.global.Constants;
import com.jchaviel.soccerleaguesapp.global.Global;
import com.jchaviel.soccerleaguesapp.navigationdrawer.ui.adapter.LeagueAdapter;
import com.jchaviel.soccerleaguesapp.navigationdrawer.ui.adapter.OnItemTouchListener;
import javax.inject.Inject;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by jchavielreyes On 12/07/2016.
 */
public class NavigationDrawerFragment extends Fragment implements OnItemTouchListener {

    @Bind(R.id.drawer_list)
    RecyclerView mRecyclerView;

    private DrawerLayout mDrawLayout;

    @Inject
    LeagueAdapter mLeagueAdapter;

    /**
     * Constructor
     */
    public NavigationDrawerFragment() {
        // Required empty public constructor
    }

    /**
     * On create
     *
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        setupInjection();
        super.onCreate(savedInstanceState);
    }

    private void setupInjection() {
        SoccerLeaguesApp app = (SoccerLeaguesApp) getActivity().getApplication();
        app.getNavigationDrawerComponent(this, this).inject(this);
    }

    /**
     * On create view
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View drawer = inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
        ButterKnife.bind(this, drawer);
        setupRecyclerView();
        return drawer;
    }

    private void setupRecyclerView() {
        mRecyclerView.setAdapter(mLeagueAdapter);
        // Show items in list one below another
        //Setup recycler view click detection mechanism
        setupClickDetection();

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setHasFixedSize(true);
    }

    /**
     * Set click detection using gesture detector
     */
    private void setupClickDetection() {
        //Detect single tap on screen
        final GestureDetector gestureDetector = new GestureDetector(getActivity(),
                new GestureDetector.SimpleOnGestureListener() {

                    /**Manage tap up events
                     *
                     * @param e
                     * @return
                     */
                    @Override
                    public boolean onSingleTapUp(MotionEvent e) {
                        return true;
                    }
                });
        setupOnItemTouchListener(gestureDetector);
    }

    /**
     * Setup item click listener
     *
     * @param gestureDetector
     */
    private void setupOnItemTouchListener(final GestureDetector gestureDetector) {
        mRecyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
            }

            /**Catch interception touch events
             *
             * @param recyclerView
             * @param motionEvent
             * @return
             */
            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {
                //Gey view under touch using coordinates
                View league = recyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());

                //If touch event, then set actionbar title and close navigation drawer
                if (league != null && gestureDetector.onTouchEvent(motionEvent)) {
                    String message = Constants.LEAGUE_SELECTED_MESSAGE;
                    Global.selectedLeagueName = ((TextView) league.
                            findViewById(R.id.league_name)).getText().toString();

                    Spinner spinner = getActivity().findViewById(R.id.teams_spinner_news);
                    if(spinner != null){
                        spinner.setSelection(((ArrayAdapter)spinner.getAdapter()).getPosition("All"));
                    }    

//                    message = String.format(message, Global.selectedLeagueName);
//                    Global.showToast(getActivity(), message);
                    mDrawLayout.closeDrawers();

                    return true;
                }
                return false;
            }

            /**Catch touch events
             *
             * @param recyclerView
             * @param motionEvent
             */
            @Override
            public void onTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {
            }
        });
    }

    /**
     * Set up drawer layout
     *
     * @param drawerLayout
     */
    public void setup(DrawerLayout drawerLayout) {
        mDrawLayout = drawerLayout;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent motionEvent, GestureDetector gestureDetector) {
        //Gey view under touch using coordinates
        View league = mRecyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());

        //If touch event, then set actionbar title and close navigation drawer
        if (league != null && gestureDetector.onTouchEvent(motionEvent)) {
            String message = Constants.LEAGUE_SELECTED_MESSAGE;
            Global.selectedLeagueName = ((TextView) league.
                    findViewById(R.id.league_name)).getText().toString();

            Spinner spinner = getActivity().findViewById(R.id.teams_spinner_news);
            if(spinner != null){
                spinner.setSelection(((ArrayAdapter)spinner.getAdapter()).getPosition("All"));
            }
            message = String.format(message, Global.selectedLeagueName);
            Global.showToast(getActivity(), message);
            mDrawLayout.closeDrawers();

            return true;
        }
        return false;
    }
}
