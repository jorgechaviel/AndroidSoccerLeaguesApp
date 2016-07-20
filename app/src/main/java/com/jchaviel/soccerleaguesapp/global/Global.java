package com.jchaviel.soccerleaguesapp.global;

import android.content.Context;
import android.widget.Toast;

import com.jchaviel.soccerleaguesapp.entities.League;
import com.jchaviel.soccerleaguesapp.entities.Team;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jchavielreyes On 12/07/2016.
 */
public class Global {

    public static String selectedLeagueName = null; //To select selected team

    //To populate spinner in schedule fragment
    public static List<String> teamNameList = new ArrayList<String>();

    public static List<String> sortedTeamNameList = new ArrayList<String>();

    public static List<Team> teamList = new ArrayList<Team>();

    public static List<League> leagues() {
        List<League> leagues = new ArrayList<League>();
        for (int index = 0; index < Constants.LEAGUE_NAMES.length; index++) {
            League league = new League();
            league.setName(Constants.LEAGUE_NAMES[index]);
            league.setLogoId(Constants.LEAGUE_LOGOS[index]);

            leagues.add(league);
        }
        return leagues;
    }

    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

}
