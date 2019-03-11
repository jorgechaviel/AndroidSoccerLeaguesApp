package com.jchaviel.soccerleaguesapp;

import android.app.Application;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.FirebaseDatabase;
import com.jchaviel.soccerleaguesapp.clasification.di.ClassificationComponent;
import com.jchaviel.soccerleaguesapp.clasification.di.ClassificationModule;
import com.jchaviel.soccerleaguesapp.clasification.di.DaggerClassificationComponent;
import com.jchaviel.soccerleaguesapp.clasification.ui.ClassificationFragment;
import com.jchaviel.soccerleaguesapp.clasification.ui.ClassificationView;
import com.jchaviel.soccerleaguesapp.domain.di.DomainModule;
import com.jchaviel.soccerleaguesapp.lib.di.LibsModule;
import com.jchaviel.soccerleaguesapp.login.di.DaggerLoginComponent;
import com.jchaviel.soccerleaguesapp.login.di.LoginComponent;
import com.jchaviel.soccerleaguesapp.login.di.LoginModule;
import com.jchaviel.soccerleaguesapp.login.ui.LoginView;
import com.jchaviel.soccerleaguesapp.main.di.DaggerMainComponent;
import com.jchaviel.soccerleaguesapp.main.di.MainComponent;
import com.jchaviel.soccerleaguesapp.main.di.MainModule;
import com.jchaviel.soccerleaguesapp.main.ui.MainView;
import com.jchaviel.soccerleaguesapp.navigationdrawer.di.DaggerNavigationDrawerComponent;
import com.jchaviel.soccerleaguesapp.navigationdrawer.di.NavigationDrawerComponent;
import com.jchaviel.soccerleaguesapp.navigationdrawer.di.NavigationDrawerModule;
import com.jchaviel.soccerleaguesapp.navigationdrawer.ui.NavigationDrawerFragment;
import com.jchaviel.soccerleaguesapp.navigationdrawer.ui.adapter.OnItemTouchListener;
import com.jchaviel.soccerleaguesapp.news.di.DaggerNewsComponent;
import com.jchaviel.soccerleaguesapp.news.di.NewsComponent;
import com.jchaviel.soccerleaguesapp.news.di.NewsModule;
import com.jchaviel.soccerleaguesapp.news.ui.NewsFragment;
import com.jchaviel.soccerleaguesapp.news.ui.NewsView;
import com.jchaviel.soccerleaguesapp.news.ui.adapter.OnItemClickListener;
import com.jchaviel.soccerleaguesapp.schedule.di.DaggerScheduleComponent;
import com.jchaviel.soccerleaguesapp.schedule.di.ScheduleComponent;
import com.jchaviel.soccerleaguesapp.schedule.di.ScheduleModule;
import com.jchaviel.soccerleaguesapp.schedule.ui.ScheduleFragment;
import com.jchaviel.soccerleaguesapp.schedule.ui.ScheduleView;
import com.squareup.leakcanary.LeakCanary;

/**
 * Created by jchavielreyes On 12/07/2016.
 */
public class SoccerLeaguesApp extends Application {

    private final static String SHARED_PREFERENCES_NAME = "UserPrefs";
    //private final static String FIREBASE_URL = "https://soccerleaguesapp.firebaseio.com";

    private DomainModule domainModule;
    private SoccerLeaguesAppModule soccerLeaguesAppModule;

    @Override
    public void onCreate() {
        super.onCreate();
        initLeakCanary();
        initFirebase();
        initModules();
    }

    private void initLeakCanary() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            //This process is dedicated to LeakCanary for heap analysis. You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
    }

    private void initModules() {
        soccerLeaguesAppModule = new SoccerLeaguesAppModule(this);
        domainModule = new DomainModule();
    }

    private void initFirebase() {
        FirebaseApp.initializeApp(this);
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }

    public String getSharedPreferencesName() {
        return SHARED_PREFERENCES_NAME;
    }

    public LoginComponent getLoginComponent(LoginView view){
        return DaggerLoginComponent
                .builder()
                .soccerLeaguesAppModule(soccerLeaguesAppModule)
                .domainModule(domainModule)
                .libsModule(new LibsModule(null))
                .loginModule(new LoginModule(view))
                .build();
    }

    public MainComponent getMainComponent(MainView view, FragmentManager manager, Fragment[] fragments, String[] titles){
        return DaggerMainComponent
                .builder()
                .soccerLeaguesAppModule(soccerLeaguesAppModule)
                .domainModule(domainModule)
                .libsModule(new LibsModule(null))
                .mainModule(new MainModule(view, titles, fragments, manager))
                .build();
    }

    public NavigationDrawerComponent getNavigationDrawerComponent(NavigationDrawerFragment fragment, OnItemTouchListener onItemTouchListener){
        return DaggerNavigationDrawerComponent
                .builder()
                .soccerLeaguesAppModule(soccerLeaguesAppModule)
                .domainModule(domainModule)
                .libsModule(new LibsModule(fragment))
                .navigationDrawerModule(new NavigationDrawerModule(onItemTouchListener))
                .build();
    }

    public NewsComponent getNewsComponent(NewsFragment fragment, NewsView view, OnItemClickListener onItemClickListener){
        return DaggerNewsComponent
                .builder()
                .soccerLeaguesAppModule(soccerLeaguesAppModule)
                .domainModule(domainModule)
                .libsModule(new LibsModule(fragment))
                .newsModule(new NewsModule(view, onItemClickListener))
                .build();
    }

    public ScheduleComponent getScheduleComponent(ScheduleFragment fragment, ScheduleView view){
        return DaggerScheduleComponent
                .builder()
                .soccerLeaguesAppModule(soccerLeaguesAppModule)
                .domainModule(domainModule)
                .libsModule(new LibsModule(fragment))
                .scheduleModule(new ScheduleModule(view))
                .build();
    }

    public ClassificationComponent getClassificationComponent(ClassificationFragment fragment, ClassificationView view){
        return DaggerClassificationComponent
                .builder()
                .soccerLeaguesAppModule(soccerLeaguesAppModule)
                .domainModule(domainModule)
                .libsModule(new LibsModule(fragment))
                .classificationModule(new ClassificationModule(view))
                .build();
    }
}
