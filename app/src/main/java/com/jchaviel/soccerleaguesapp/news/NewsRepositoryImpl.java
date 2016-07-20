package com.jchaviel.soccerleaguesapp.news;

import android.os.AsyncTask;
import android.util.Log;
import com.jchaviel.soccerleaguesapp.domain.FirebaseAPI;
import com.jchaviel.soccerleaguesapp.entities.New;
import com.jchaviel.soccerleaguesapp.global.Constants;
import com.jchaviel.soccerleaguesapp.global.Global;
import com.jchaviel.soccerleaguesapp.lib.base.EventBus;
import com.jchaviel.soccerleaguesapp.news.events.NewsListEvent;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by jchavielreyes on 7/14/16.
 */
public class NewsRepositoryImpl implements NewsRepository {

    private EventBus eventBus;
    private FirebaseAPI firebaseAPI;

    private final CharSequence KEY_RSS = "rss";
    private final String KEY_INDEX = "index";
    private final String KEY_LOG = "clear";
    private final String KEY_NEWS_ITEM = "item";
    private final String KEY_TITLE = "title";
    private final String KEY_LINK = "link";
    private final String KEY_DATE = "pubDate";
    private final java.lang.String KEY_URL = "url";
    private final String KEY_IMAGE = "enclosure";
    private String leagueNameSelected;
    private List<New> newsList;

    public NewsRepositoryImpl(EventBus eventBus, FirebaseAPI firebaseAPI) {
        this.eventBus = eventBus;
        this.firebaseAPI = firebaseAPI;
    }

    @Override
    public void subscribe(String leagueName, ArrayList<New> mNews, String teamName) {
        leagueNameSelected = leagueName;
        load().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,
                leagueName,
                mNews,
                teamName);
    }

    @Override
    public void unsubscribe() {
        firebaseAPI.unsubscribe();
    }

    private void post(int type, String error) {
        post(type, error, null);
    }

    private void post(int type, New objNews) {
        post(type, null, objNews);
    }

    private void post(int type, String error, New objNews) {
        NewsListEvent event = new NewsListEvent();
        event.setType(type);
        event.setError(error);
        event.setObjNew(objNews);
        eventBus.post(event);
    }

    public AsyncTask<Object, Void, Void> load() {

        return new AsyncTask<Object, Void, Void>(){
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                newsList = new ArrayList<>();
            }

            private String getTeamNewsLink(String team) {
                while (true) {
                    if (!Global.teamList.isEmpty())
                        break; //Links are in news object contained in global teamlist
                }
                String homeLink = Global.teamList.get(Global.teamNameList.indexOf(team)).getHomeLink();
                return homeLink.replace(KEY_INDEX, KEY_RSS);
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

            /**
             * Given league name, get league news link
             *
             * @param leagueName
             * @return
             */
            private String getLeagueNewsLink(String leagueName) {
                return String.format(Constants.LEAGUE_NEWS_WEBLINK,
                        leagueName.toLowerCase().replaceAll(" ", "-").trim(),
                        getLeagueCode(leagueNameSelected));
            }

            /**
             * Fetch news data from retrived news document
             *
             * @param params
             * @return
             */
            @Override
            protected Void doInBackground(Object... params) {
                String leagueName = (String) params[0];
                List<New> newsObjList = (List<New>) params[1];
                String team = ((String) params[2]).trim();

                //Page contains all feeds web-links for all leagues and teams
                //Document newsFeedsPage = getNewsFeedPage();

                String newsLink = "";
                if (team.equalsIgnoreCase("all")) {
                    newsLink = getLeagueNewsLink(leagueName);
                } else {
                    newsLink = getTeamNewsLink(team);
                }
                Log.d("test", newsLink);

                // Extract data from rss feed
                getRssData(newsLink, newsObjList);

                while (true) // Wait for teams list to populate
                {
                    if (!Global.teamList.isEmpty()) break;
                }

                return null;
            }

            private void getRssData(String newsLink, List<New> newsObjList) {
                try {
                    Document doc = Jsoup.connect(newsLink).userAgent(Constants.USER_AGENT).get();
                    Document doc1 = Jsoup.parse(doc.html(), "", Parser.xmlParser());
                    Elements items = doc1.select(KEY_NEWS_ITEM);

                    for (int index = 0; index < items.size(); index++) {
                        String title = items.get(index).select(KEY_TITLE).first().text();
                        String link = items.get(index).select(KEY_LINK).first().text();
                        String date = items.get(index).select(KEY_DATE).first().text();
                        String imageLink = null;
                        try {
                            imageLink = items.get(index).select(KEY_IMAGE).first().attr(KEY_URL);
                        } catch (NullPointerException e) {
                            // Some news might not have image
                        }
                        newsObjList.add(new New(title, imageLink, date, link));
                    }
                    newsList.addAll(newsObjList);
                    Log.d(KEY_LOG, doc.html());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            /**
             * Show list and hide progress bar
             *
             * @param aVoid
             */
            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                for (New objNew: newsList) {
                    post(NewsListEvent.READ_EVENT, objNew);
                }
            }
        };
    }
}