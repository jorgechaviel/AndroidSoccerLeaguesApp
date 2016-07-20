package com.jchaviel.soccerleaguesapp.entities;

import java.io.Serializable;

/**
 * Created by jchavielreyes On 12/07/2016.
 */
public class Team implements Serializable {
    private String mName;
    private String mMatchesPlayed;
    private String mWins;
    private String mDraws;
    private String mLoss;
    private String mGoalDiff;
    private String mPoints;
    private String mRank;
    private String mHomeLink;

    public Team(String name, String rank, String matchesPlayed, String wins, String draws, String loss, String goalDiff, String points, String teamHomeLink) {
        mName = name;
        mRank = rank;
        mMatchesPlayed = matchesPlayed;
        mWins = wins;
        mDraws = draws;
        mLoss = loss;
        mGoalDiff = goalDiff;
        mPoints = points;
        mHomeLink = teamHomeLink;
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
}
