package com.jchaviel.soccerleaguesapp.entities;

import java.io.Serializable;

/**
 * Created by jchavielreyes On 12/07/2016.
 */
public class Fixture implements Serializable {

    private String mHomeTeam;
    private String mAwayTeam;
    private String mHomeTeamLogo;
    private String mAwayTeamLogo;
    private String mHomeTeamScore;
    private String mAwayTeamScore;
    private String mDate;
    private String mStatus;
    private String mTime;
    private String mSeparator;

    public Fixture(String date, String status, String time, String homeTeam, String homeTeamLogo, String homeScore, String awayTeam, String awayTeamLogo, String awayScore, String separator) {
        mHomeTeam = homeTeam;
        mAwayTeam = awayTeam;
        mHomeTeamLogo = homeTeamLogo;
        mAwayTeamLogo = awayTeamLogo;
        mHomeTeamScore = homeScore;
        mAwayTeamScore = awayScore;
        mDate = date;
        mStatus = status;
        mTime = time;
        mSeparator = separator;
    }

    public String getHomeTeam() {
        return mHomeTeam;
    }

    public String getAwayTeam() {
        return mAwayTeam;
    }

    public String getHomeTeamLogo() {
        return mHomeTeamLogo;
    }

    public String getAwayTeamLogo() {
        return mAwayTeamLogo;
    }

    public String getHomeTeamScore() {
        return mHomeTeamScore;
    }

    public String getAwayTeamScore() {
        return mAwayTeamScore;
    }

    public String getDate() {
        return mDate;
    }

    public String getStatus() {
        return mStatus;
    }

    public String getTime() {
        return mTime;
    }

    public String getSeparator() {
        return mSeparator;
    }
}
