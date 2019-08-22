package com.jchaviel.soccerleaguesapp.navigationdrawer.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;

public class OnItemTouchDetector implements RecyclerView.OnItemTouchListener {

    private SwipeGestureListener listener;

    public OnItemTouchDetector(SwipeGestureListener listener) {
        this.listener = listener; 
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }
}
