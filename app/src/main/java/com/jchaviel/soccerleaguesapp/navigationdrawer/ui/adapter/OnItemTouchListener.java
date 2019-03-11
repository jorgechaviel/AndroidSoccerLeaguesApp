package com.jchaviel.soccerleaguesapp.navigationdrawer.ui.adapter;

import android.view.GestureDetector;
import android.view.MotionEvent;

/**
 * Created by jchavielreyes on 7/6/16.
 */
public interface OnItemTouchListener {
    boolean onInterceptTouchEvent(MotionEvent motionEvent, GestureDetector gestureDetector); //cuando le hagan click a la dirección
}

