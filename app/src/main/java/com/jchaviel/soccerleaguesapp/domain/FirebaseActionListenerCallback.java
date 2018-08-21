package com.jchaviel.soccerleaguesapp.domain;

import com.google.firebase.database.DatabaseError;

/**
 * Created by jchavielreyes on 7/4/16.
 */
public interface FirebaseActionListenerCallback {
    void onSuccess();
    void onError(DatabaseError error);
}
