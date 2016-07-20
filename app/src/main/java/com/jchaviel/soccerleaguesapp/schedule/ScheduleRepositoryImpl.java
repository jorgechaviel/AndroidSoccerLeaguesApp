package com.jchaviel.soccerleaguesapp.schedule;

import android.os.AsyncTask;
import android.util.Log;
import com.jchaviel.soccerleaguesapp.domain.FirebaseAPI;
import com.jchaviel.soccerleaguesapp.entities.Fixture;
import com.jchaviel.soccerleaguesapp.global.Constants;
import com.jchaviel.soccerleaguesapp.global.Global;
import com.jchaviel.soccerleaguesapp.lib.base.EventBus;
import com.jchaviel.soccerleaguesapp.schedule.events.ScheduleListEvent;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by jchavielreyes on 7/14/16.
 */
public class ScheduleRepositoryImpl implements ScheduleRepository {

    private EventBus eventBus;
    private FirebaseAPI firebaseAPI;

    private final String MONTH_FORMAT = "MMMM";
    private final String PARSING_DONE_MESSAGE = "team parsing done";
    private final String KEY_HEADING_4 = "h4";
    private final String KEY_SEPARATOR = "separator";
    private final String KEY_AWAY_SCORE = "away-score";
    private final String KEY_HOME_SCORE = "home-score";
    private final String KEY_HOME_TEAM_SCORE = "score-home-team";
    private final String KEY_AWAY_TEAM_SCORE = "score-away-team";
    private final String KEY_TEAM_LOGO = "team-logo";
    private final String KEY_STATUS = "status";
    private final String KEY_DATE_CONTAINER = "date-container";
    private final String CLASS_TEAM_NAME = "team-name";
    private final String CLASS_TEAM_SCORE = "team-score";
    private final String SRC_ATTR = "src";

    private final String TEAM_ALL = "all";
    private final String KET_TIME = "time";
    private final int INDEX_ZERO = 0;
    private final int INDEX_ONE = 1;
    private final String KEY_LOG = "testSchedule";

    private String leagueNameSelected;
    private List<Fixture> fixturesList;

    public ScheduleRepositoryImpl(EventBus eventBus, FirebaseAPI firebaseAPI) {
        this.eventBus = eventBus;
        this.firebaseAPI = firebaseAPI;
    }

    @Override
    public void subscribe(String leagueName, ArrayList<Fixture> fixtureList, String month, String teamName) {
        leagueNameSelected = leagueName;

        loadFixtures().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,
                        leagueName,
                        fixtureList,
                        month,
                        teamName);

    }

    @Override
    public void unsubscribe() {
        firebaseAPI.unsubscribe();
    }

    private void post(int type, String error) {
        post(type, error, null);
    }

    private void post(int type, Fixture fixture) {
        post(type, null, fixture);
    }

    private void post(int type, String error, Fixture fixture) {
        ScheduleListEvent event = new ScheduleListEvent();
        event.setType(type);
        event.setError(error);
        event.setFixture(fixture);
        eventBus.post(event);
    }

    public AsyncTask<Object, Void, Void> loadFixtures() {

        return new AsyncTask<Object, Void, Void>(){

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                fixturesList = new ArrayList<>();
            }

            private String getTeamFixtureLink(String team) {
                while (true) {
                    if (!Global.teamList.isEmpty())
                        break; //team objects are stored in teamList
                }
                String homeLink = Global.teamList.get(Global.teamNameList.indexOf(team)).getHomeLink();
                return homeLink.replace("index", "fixtures");
            }


            /**
             * Given league name,get league code
             *
             * @param currentActionBarTitle
             * @return
             */
            private String getLeagueCode(String currentActionBarTitle) {
                return Constants.TEAM_CODES[Arrays.asList(Constants.LEAGUE_NAMES).indexOf(currentActionBarTitle)];
            }

            private String getLeagueFixtureLink(String leagueName, String month) {

                return String.format(Constants.LEAGUE_SCHEDULE_WEBLINK,
                        leagueName.toLowerCase().replaceAll(" ", "-").trim(),
                        getLeagueCode(leagueNameSelected),
                        getDate(month));
            }

            private String getDate(String month) {
                return getYear(month) + getMonthNum(month);
            }

            private int getYear(String month) {

                int year = Calendar.getInstance(TimeZone.getDefault()).get(Calendar.YEAR);
                try {
                    Date date = new SimpleDateFormat(MONTH_FORMAT).parse(month); //Get only name of month
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(date);

                    //League years exists between August and May
                    if (cal.get(Calendar.MONTH) < Calendar.AUGUST) return (year + INDEX_ONE);
                } catch (ParseException e) {
                    Log.e(KEY_LOG, e.getMessage());
                }

                return year;
            }

            private String getMonthNum(String month) {
                String monthNum = (Arrays.asList(Constants.MONTHS).indexOf(month) + INDEX_ONE) + "";
                monthNum = monthNum.length() < 2 ? INDEX_ZERO + monthNum : monthNum;
                return monthNum;
            }

            /**
             * Fetch news data from retrived news document
             *
             * @param
             * @return
             */
            @Override
            protected Void doInBackground(Object... params) {
                String leagueName = (String) params[INDEX_ZERO];
                List<Fixture> fixtureObjList = (List<Fixture>) params[INDEX_ONE];
                String month = ((String) params[2]).trim();
                String team = ((String) params[3]).trim();

                String fixtureLink = "";
                if (team.equalsIgnoreCase(TEAM_ALL)) { // 'All' means get schedule of entire league for specific month
                    fixtureLink = getLeagueFixtureLink(leagueName, month);
                } else {
                    fixtureLink = getTeamFixtureLink(team);
                }
//                Log.d(KEY_LOG, fixtureLink);

                try {
                    //Fetch document from web
                    Document doc = Jsoup.connect(fixtureLink).userAgent(Constants.USER_AGENT).get();
//                    Log.d(KEY_LOG, doc.html());

                    if (team.equalsIgnoreCase(TEAM_ALL)) {
                        Elements fixtureGroups = doc.getElementsByClass(Constants.LEAGUE_FIXTURES_GROUP);
                        getLeagueFixtureData(fixtureObjList, fixtureGroups);
                    } else {
                        Elements fixtures = doc.getElementsByClass(Constants.TEAM_FIXTURES_CLASS);
                        getTeamFixturesData(fixtureObjList, fixtures, month);
                    }

//                    Log.d(KEY_LOG, doc.html());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                while (true) // Wait for teams list to populate
                {
                    if (!Global.sortedTeamNameList.isEmpty()) {
                        break;
                    }
                }

                return null;
            }

            private void getLeagueFixtureData(List<Fixture> fixtureList, Elements fixtureGroups) {
                int numberOfFixtures = fixtureGroups.size();

                for (int index = INDEX_ZERO; index < numberOfFixtures; index++) {

                    /**********HTML parsing to get data************/

                    Element fixtureGroup = fixtureGroups.get(index); //Group represents date group

                    /**************************Html parsing********************************/

                    String fixtureDate = fixtureGroup.select(KEY_HEADING_4).last().text();
                    Elements fixtures = fixtureGroup.getElementsByClass(Constants.LEAGUE_FIXTURES_CLASS);

                    for (Element fixture : fixtures) {

                        String time = time = fixture.getElementsByClass(KET_TIME).get(INDEX_ZERO).text();

                        String homeTeam = fixture.getElementsByClass(CLASS_TEAM_NAME).get(INDEX_ZERO).child(INDEX_ZERO).text();
                        String homeTeamLogo = fixture.getElementsByClass(CLASS_TEAM_NAME).get(INDEX_ZERO).child(INDEX_ZERO).child(INDEX_ZERO).attr(SRC_ATTR);

                        String awayTeam = fixture.getElementsByClass(CLASS_TEAM_NAME).get(INDEX_ONE).child(INDEX_ZERO).text();
                        String awayTeamLogo = fixture.getElementsByClass(CLASS_TEAM_NAME).get(INDEX_ONE).child(INDEX_ZERO).child(INDEX_ZERO).attr(SRC_ATTR);

                        String homeScore = fixture.getElementsByClass(CLASS_TEAM_SCORE).get(INDEX_ZERO).child(INDEX_ZERO).text();
                        String awayScore = fixture.getElementsByClass(CLASS_TEAM_SCORE).get(INDEX_ONE).child(INDEX_ZERO).text();

                        /***************************HTML parsing*******************************/

                        //Object to store fixture data
                        Fixture fixtureObj = new Fixture(fixtureDate, null, time, homeTeam,
                                homeTeamLogo, homeScore, awayTeam,
                                awayTeamLogo, awayScore, null);

                        fixtureList.add(fixtureObj);
                    }
                    fixturesList.addAll(fixtureList);
                    Log.e(KEY_LOG, PARSING_DONE_MESSAGE);
                }
            }

            private void getTeamFixturesData(List<Fixture> fixtureList, Elements fixtures, String month) {

                int numberOfFixtures = fixtures.size();

                //INDEX_ONE to ignore fixtures header
                for (int index = INDEX_ONE; index < numberOfFixtures; index++) {

                    /**********HTML parsing to get data************/
                    Element fixture = fixtures.get(index);

                    String date = fixture.getElementsByClass(KEY_DATE_CONTAINER).get(INDEX_ZERO).getElementsByClass("date").get(INDEX_ZERO).text();

                    // Only select fixtures of selected month
                    if (!date.substring(INDEX_ZERO, 2).equalsIgnoreCase(month.substring(INDEX_ZERO, 2)))
                        continue;

                    String status = null;
                    String time = null;

                    try {
                        status = fixture.getElementsByClass(KEY_DATE_CONTAINER).get(INDEX_ZERO).getElementsByClass(KEY_STATUS).get(INDEX_ZERO).text();
                        time = fixture.getElementsByClass(KEY_DATE_CONTAINER).get(INDEX_ZERO).getElementsByClass(KET_TIME).get(INDEX_ZERO).text();
                    } catch (Exception e) {
                        //Continue as one of them needs to be null;
                    }

                    String homeTeam = fixture.getElementsByClass(KEY_HOME_TEAM_SCORE).get(INDEX_ZERO).getElementsByClass(CLASS_TEAM_NAME).get(INDEX_ZERO).text();
                    String homeTeamLogo = fixture.getElementsByClass(KEY_HOME_TEAM_SCORE).get(INDEX_ZERO).getElementsByClass(KEY_TEAM_LOGO).get(INDEX_ZERO).child(INDEX_ZERO).attr(SRC_ATTR);

                    String awayTeam = fixture.getElementsByClass(KEY_AWAY_TEAM_SCORE).get(INDEX_ZERO).getElementsByClass(CLASS_TEAM_NAME).get(INDEX_ZERO).text();
                    String awayTeamLogo = fixture.getElementsByClass(KEY_AWAY_TEAM_SCORE).get(INDEX_ZERO).getElementsByClass(KEY_TEAM_LOGO).get(INDEX_ZERO).child(INDEX_ZERO).attr(SRC_ATTR);

                    String homeScore = fixture.getElementsByClass(KEY_HOME_SCORE).get(INDEX_ZERO).text();
                    String awayScore = fixture.getElementsByClass(KEY_AWAY_SCORE).get(INDEX_ZERO).text();
                    String separator = fixture.getElementsByClass(KEY_SEPARATOR).get(INDEX_ZERO).text();


//                    Log.d(KEY_LOG, PARSING_DONE_MESSAGE);

                    //Object to store fixture data
                    Fixture fixtureObj = new Fixture(date, status, time, homeTeam,
                            homeTeamLogo, homeScore, awayTeam,
                            awayTeamLogo, awayScore, separator);

                    fixtureList.add(fixtureObj);
                }

                fixturesList.addAll(fixtureList);
            }

            /**
             * Show list and hide progress bar
             *
             * @param aVoid
             */
            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                for (Fixture fixture: fixturesList) {
                    post(ScheduleListEvent.READ_EVENT, fixture);
                }
            }
        };
    }
}

