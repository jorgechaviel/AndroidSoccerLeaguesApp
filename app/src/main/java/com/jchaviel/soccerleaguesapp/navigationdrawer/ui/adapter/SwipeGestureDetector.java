package com.jchaviel.soccerleaguesapp.navigationdrawer.ui.adapter;

import android.view.GestureDetector;
import android.view.MotionEvent;

public class SwipeGestureDetector extends GestureDetector.SimpleOnGestureListener {

    private SwipeGestureListener listener;

    public SwipeGestureDetector(SwipeGestureListener listener) {
        this.listener = listener;
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return true;
    }
}
