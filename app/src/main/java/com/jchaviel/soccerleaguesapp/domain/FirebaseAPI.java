package com.jchaviel.soccerleaguesapp.domain;

import android.support.annotation.NonNull;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by jchavielreyes on 7/4/16.
 */
public class FirebaseAPI {

    private DatabaseReference firebase;
    private FirebaseAuth firebaseAuth;
    private ChildEventListener teamsEventListener;

    private final static String SEPARATOR = "___";

    public FirebaseAPI(DatabaseReference firebase, FirebaseAuth firebaseAuth) {
        this.firebase = firebase;
        this.firebaseAuth = firebaseAuth;
    }

    /**
     * Metodo para verificar si hay o no hay datos
     */
    public void checkForData(final FirebaseActionListenerCallback listenerCallback){
        firebase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getChildrenCount() > 0){
                    listenerCallback.onSuccess();
                } else {
                    listenerCallback.onError(null);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                listenerCallback.onError(databaseError);
            }
        });
    }

    public void subscribe(final FirebaseEventListenerCallback listenerCallback){
        if (teamsEventListener == null){
            teamsEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, String s) {
                    listenerCallback.onChildAdded(dataSnapshot);
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, String s) {}

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                    listenerCallback.onChildRemoved(dataSnapshot);
                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, String s) {}

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    listenerCallback.onCanceller(databaseError);
                }
            };
            firebase.addChildEventListener(teamsEventListener);
        }
    }

    /**
     * Metodo para desuscribir el evento de firebase database
     */

    public void unsubscribe(){
        if(teamsEventListener != null){
            firebase.removeEventListener(teamsEventListener);
        }
    }

    /**
     * Metodos para guardar, eliminar o actualizar una equipo
     */

    public String create(){
        return firebase.push().getKey();
    }

    /**
     * Metodos para el manejo de la sesión
     */

    public String getAuthEmail(){
        String email = null;
        if(firebaseAuth != null){
            email = firebaseAuth.getCurrentUser().getEmail();
        }
        return email;
    }

    public void logout(){
        firebaseAuth.signOut();
    }

    public void login(String email, String password, final FirebaseActionListenerCallback listenerCallback){
        firebaseAuth.signInWithEmailAndPassword(email, password)
        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    listenerCallback.onSuccess();
                } else {
                    listenerCallback.onError(DatabaseError.fromException(task.getException()));
                }
            }
        });
    }

    public void signup(String email, String password, final FirebaseActionListenerCallback listenerCallback){
        firebaseAuth.createUserWithEmailAndPassword(email, password)
        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    listenerCallback.onSuccess();
                } else {
                    listenerCallback.onError(DatabaseError.fromException(task.getException()));
                }
            }
        });
    }

    public void checkForSession(FirebaseActionListenerCallback listenerCallback){
        if(firebaseAuth.getCurrentUser() != null){
            listenerCallback.onSuccess();
        } else {
            listenerCallback.onError(null);
        }
    }
}