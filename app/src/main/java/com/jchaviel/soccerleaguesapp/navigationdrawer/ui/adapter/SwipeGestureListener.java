package com.jchaviel.soccerleaguesapp.navigationdrawer.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import com.jchaviel.soccerleaguesapp.R;
import com.jchaviel.soccerleaguesapp.global.Global;

class SwipeGestureListener implements RecyclerView.OnItemTouchListener {

    private GestureDetector gestureDetector;
    private FragmentActivity activity;

    public SwipeGestureListener (Context ctx, FragmentActivity fragmentActivity){
        gestureDetector = new GestureDetector(ctx, new GestureListener());
        activity = fragmentActivity;
    }

    @Override
    public boolean onInterceptTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {
        //Gey view under touch using coordinates
        View league = recyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());

        //If touch event, then set actionbar title and close navigation drawer
        if (league != null && gestureDetector.onTouchEvent(motionEvent)) {
            Global.selectedLeagueName = ((TextView) league.
                    findViewById(R.id.league_name)).getText().toString();

            Spinner spinner = activity.findViewById(R.id.teams_spinner_news);
            if(spinner != null){
                spinner.setSelection(((ArrayAdapter)spinner.getAdapter()).getPosition("All"));
            }
            //mDrawLayout.closeDrawers();

            return true;
        }
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }


    private final class GestureListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            return true;
        }
    }
}
