package com.jchaviel.soccerleaguesapp.lib.base;

/**
 * Created by jchavielreyes on 7/4/16.
 */
public interface EventBus {
    void register(Object subscriber);
    void unregister(Object subscriber);
    void post(Object event);
}
