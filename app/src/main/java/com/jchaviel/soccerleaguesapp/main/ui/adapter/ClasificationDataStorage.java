package com.jchaviel.soccerleaguesapp.main.ui.adapter;

import android.os.AsyncTask;
import android.util.Log;

import com.jchaviel.soccerleaguesapp.entities.Team;
import com.jchaviel.soccerleaguesapp.global.Constants;
import com.jchaviel.soccerleaguesapp.global.Global;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by jchavielreyes on 7/13/16.
 */
public class ClasificationDataStorage implements DataStorage {

    private static final String HREF_ATTR = "href";
    private final String FIRST_ENTRY = "All";
    private final int ZERO_INDEX = 0;
    private final int INDEX_ONE = 1;
    private final int INDEX_THREE = 3;
    private final int INDEX_FOUR = 4;
    private final int INDEX_FIVE = 5;
    private final int INDEX_SIX = 6;
    private final int INDEX_TWENTY_TWO = 22;
    private final int INDEX_TWENTY_THREE = 23;

    @Override
    public AsyncTask<Object, Object, Void> load() {
        return new AsyncTask<Object, Object, Void>(){

            /**
             * On pre execute
             */
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onProgressUpdate(Object... params) {
                super.onProgressUpdate(params);
            }

            /**
             * Get data in background from web
             *
             * @param params
             * @return
             */
            @Override
            protected Void doInBackground(Object... params) {
                int itemPosition = (int) params[ZERO_INDEX]; //Position of league in pre-defined list
                getTeamsData(itemPosition);
                return null;
            }

            /**
             * On post execute
             *
             * @param aVoid
             */
            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
            }
        };
    }



    /**
     * Given the postion, return league table link
     *
     * @param itemPosition
     * @return
     */
    @Override
    public String getTableLink(int itemPosition) {
        return String.format(Constants.TABLE_WEBLINK,
                Constants.LEAGUE_NAMES[itemPosition].replaceAll(" ", "-").toLowerCase(), Constants.TEAM_CODES[itemPosition]);
    }

    /**
     * Extracts data from retrieved html document
     *
     * @param itemPosition
     */
    @Override
    public void getTeamsData(int itemPosition) {

        Document doc = null;
        try {
            doc = Jsoup.connect(getTableLink(itemPosition))
                    .userAgent(Constants.USER_AGENT).get();
            // Select tables that contains team rows
            Elements teams = doc.select("table").first().children().get(INDEX_ONE).children().select("tr");

            int numberOfTeams = teams.size();
            List<String> teamNames = new ArrayList<String>(); //Stores team names only
            List<Team> teamList = new ArrayList<Team>(); //Stores team objects

            // INDEX_ONE to ignore table header
            for (int index = INDEX_ONE; index < numberOfTeams; index++) {

                /**********HTML parsing to get data************/

                Element team = teams.get(index);

                String rank = team.child(ZERO_INDEX).text();
                String teamName = team.child(INDEX_ONE).text();

                String teamHomeLink;
                if(team.child(INDEX_ONE).children().size()>0)
                    teamHomeLink = team.child(INDEX_ONE).child(ZERO_INDEX).attr(HREF_ATTR);
                else teamHomeLink = null;

                String matchesPlayed = team.child(INDEX_THREE).text();
                String wins = team.child(INDEX_FOUR).text();
                String draws = team.child(INDEX_FIVE).text();
                String loss = team.child(INDEX_SIX).text();
                String goalDiff = team.child(INDEX_TWENTY_TWO).text();
                String points = team.child(INDEX_TWENTY_THREE).text();

                /**********HTML parsing to get data************/

                teamList.add(new Team(teamName, rank, matchesPlayed, wins, draws, loss, goalDiff, points, teamHomeLink));
                teamNames.add(teamName);
            }

            Global.teamNameList.addAll(teamNames); // Populate global team names list
            Global.sortedTeamNameList.addAll(teamNames);
            Collections.sort(Global.sortedTeamNameList);
            Global.sortedTeamNameList.add(ZERO_INDEX, FIRST_ENTRY);
            Global.teamList.addAll(teamList); // Populate global teat list of team objects
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
