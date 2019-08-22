package com.jchaviel.soccerleaguesapp.entities;

import com.google.firebase.database.Exclude;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jchavielreyes On 12/07/2016.
 */
public class Team implements Serializable {

    private  String uid;
    private String mName;
    private String mMatchesPlayed;
    private String mWins;
    private String mDraws;
    private String mLoss;
    private String mGoalDiff;
    private String mPoints;
    private String mRank;
    private String mHomeLink;
    private String mLeague;

    public Team(String name, String rank, String matchesPlayed, String wins, String draws, String loss, String goalDiff, String points, String teamHomeLink, String league) {
        mName = name;
        mRank = rank;
        mMatchesPlayed = matchesPlayed;
        mWins = wins;
        mDraws = draws;
        mLoss = loss;
        mGoalDiff = goalDiff;
        mPoints = points;
        mHomeLink = teamHomeLink;
        mLeague = league;
    }

    public String getUid() {
        return uid;
    }

    public String getMatchesPlayed() {
        return mMatchesPlayed;
    }

    public String getName() {
        return mName;
    }

    public String getWins() {
        return mWins;
    }

    public String getLoss() {
        return mLoss;
    }

    public String getDraws() {
        return mDraws;
    }

    public String getGoalDiff() {
        return mGoalDiff;
    }

    public String getRank() {
        return mRank;
    }

    public String getPoints() {
        return mPoints;
    }

    public String getHomeLink() {
        return mHomeLink;
    }

    public String getmLeague() {
        return mLeague;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("uid", uid);
        result.put("mName", mName);
        result.put("mRank", mRank);
        result.put("mMatchesPlayed", mMatchesPlayed);
        result.put("mWins", mWins);
        result.put("mDraws", mDraws);
        result.put("mLoss", mLoss);
        result.put("mGoalDiff", mGoalDiff);
        result.put("mPoints", mPoints);
        result.put("mHomeLink", mHomeLink);
        result.put("mLeague", mLeague);
        return result;
    }
}
