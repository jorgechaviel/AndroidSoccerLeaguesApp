package com.jchaviel.soccerleaguesapp.domain;

import com.firebase.client.AuthData;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.Map;

/**
 * Created by jchavielreyes on 7/4/16.
 */
public class FirebaseAPI {

    private Firebase firebase;
    private ChildEventListener photosEventListener;

    private final static String SEPARATOR = "___";

    public FirebaseAPI(Firebase firebase) {
        this.firebase = firebase;
    }

    /**
     * Metodo para verificar si hay o no hay datos
     */
    public void checkForData(final FirebaseActionListenerCallback listenerCallback){
        firebase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.getChildrenCount() > 0){
                    listenerCallback.onSuccess();
                } else {
                    listenerCallback.onError(null);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                listenerCallback.onError(firebaseError);
            }
        });
    }

    public void subscribe(final FirebaseEventListenerCallback listenerCallback){
        if (photosEventListener == null){
            photosEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    listenerCallback.onChildAdded(dataSnapshot);
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {}

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {
                    listenerCallback.onChildRemoved(dataSnapshot);
                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {}

                @Override
                public void onCancelled(FirebaseError firebaseError) {
                    listenerCallback.onCanceller(firebaseError);
                }
            };
            firebase.addChildEventListener(photosEventListener);
        }
    }

    /**
     * Metodo para desuscribir el evento de firebase database
     */

    public void unsubscribe(){
        if(photosEventListener != null){
            firebase.removeEventListener(photosEventListener);
        }
    }

    /**
     * Metodos para guardar, eliminar o actualizar una fotografia
     */

    public String create(){
        return firebase.push().getKey();
    }

    /**
     * Metodos para el manejo de la sesión
     */

    public String getAuthEmail(){
        String email = null;
        if(firebase.getAuth() != null){
            Map<String, Object> providerData = firebase.getAuth().getProviderData();
            email = providerData.get("email").toString();
        }
        return email;
    }

    public void logout(){
        firebase.unauth();
    }

    public void login(String email, String password, final FirebaseActionListenerCallback listenerCallback){
        firebase.authWithPassword(email, password, new Firebase.AuthResultHandler() {
            @Override
            public void onAuthenticated(AuthData authData) {
                listenerCallback.onSuccess();
            }

            @Override
            public void onAuthenticationError(FirebaseError firebaseError) {
                listenerCallback.onError(firebaseError);
            }
        });
    }

    public void signup(String email, String password, final FirebaseActionListenerCallback listenerCallback){
        //Map<String, Object> se le coloca porque debe ser del mismo tipo que el ProviderData
        firebase.createUser(email, password, new Firebase.ValueResultHandler<Map<String, Object>>() {

            @Override
            public void onSuccess(Map<String, Object> o) {
                listenerCallback.onSuccess();
            }

            @Override
            public void onError(FirebaseError firebaseError) {
                listenerCallback.onError(firebaseError);
            }
        });
    }

    public void checkForSession(FirebaseActionListenerCallback listenerCallback){
        if(firebase.getAuth() != null){
            listenerCallback.onSuccess();
        } else {
            listenerCallback.onError(null);
        }
    }
}