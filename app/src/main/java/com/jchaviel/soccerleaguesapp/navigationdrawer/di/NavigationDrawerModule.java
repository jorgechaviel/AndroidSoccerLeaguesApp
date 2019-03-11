package com.jchaviel.soccerleaguesapp.navigationdrawer.di;

import com.jchaviel.soccerleaguesapp.entities.League;
import com.jchaviel.soccerleaguesapp.global.Global;
import com.jchaviel.soccerleaguesapp.navigationdrawer.ui.adapter.LeagueAdapter;
import com.jchaviel.soccerleaguesapp.navigationdrawer.ui.adapter.OnItemTouchListener;

import java.util.List;
import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;

/**
 * Created by jchavielreyes on 7/6/2016.
 */
@Module
public class NavigationDrawerModule {

    OnItemTouchListener onItemTouchListener;

    public NavigationDrawerModule(OnItemTouchListener onItemTouchListener) {
        this.onItemTouchListener = onItemTouchListener;
    }

    @Provides @Singleton
    LeagueAdapter providesLeagueAdapter(List<League> leagues){
        return new LeagueAdapter(leagues);
    }

    @Provides @Singleton
    List<League> providesLeagueList(){
        return Global.leagues();
    }
}
