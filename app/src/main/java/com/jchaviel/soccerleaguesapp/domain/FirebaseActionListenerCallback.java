package com.jchaviel.soccerleaguesapp.domain;

import com.firebase.client.FirebaseError;

/**
 * Created by jchavielreyes on 7/4/16.
 */
public interface FirebaseActionListenerCallback {
    void onSuccess();
    void onError(FirebaseError error);
}
