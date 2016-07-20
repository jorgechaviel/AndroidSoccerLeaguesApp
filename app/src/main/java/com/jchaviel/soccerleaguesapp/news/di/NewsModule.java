package com.jchaviel.soccerleaguesapp.news.di;

import com.jchaviel.soccerleaguesapp.domain.FirebaseAPI;
import com.jchaviel.soccerleaguesapp.domain.Utils;
import com.jchaviel.soccerleaguesapp.entities.New;
import com.jchaviel.soccerleaguesapp.lib.base.EventBus;
import com.jchaviel.soccerleaguesapp.lib.base.ImageLoader;
import com.jchaviel.soccerleaguesapp.news.NewsInteractor;
import com.jchaviel.soccerleaguesapp.news.NewsInteractorImpl;
import com.jchaviel.soccerleaguesapp.news.NewsPresenter;
import com.jchaviel.soccerleaguesapp.news.NewsPresenterImpl;
import com.jchaviel.soccerleaguesapp.news.NewsRepository;
import com.jchaviel.soccerleaguesapp.news.NewsRepositoryImpl;
import com.jchaviel.soccerleaguesapp.news.ui.NewsView;
import com.jchaviel.soccerleaguesapp.news.ui.adapter.NewsAdapter;
import com.jchaviel.soccerleaguesapp.news.ui.adapter.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;

/**
 * Created by jchavielreyes on 7/6/2016.
 */
@Module
public class NewsModule {
    private NewsView view;
    OnItemClickListener onItemClickListener;

    public NewsModule(NewsView view, OnItemClickListener onItemClickListener) {
        this.view = view;
        this.onItemClickListener = onItemClickListener;
    }

    @Provides @Singleton
    NewsView providesNewsView() {
        return this.view;
    }

    @Provides @Singleton
    NewsPresenter providesNewsPresenter(EventBus eventBus, NewsView view, NewsInteractor interactor){
        return new NewsPresenterImpl(eventBus, view, interactor);
    }

    @Provides @Singleton
    NewsInteractor providesNewsInteractor(NewsRepository repository){
        return new NewsInteractorImpl(repository);
    }

    @Provides @Singleton
    NewsRepository providesNewsRepository(EventBus eventBus, FirebaseAPI firebaseAPI){
        return new NewsRepositoryImpl(eventBus, firebaseAPI);
    }

    @Provides @Singleton
    NewsAdapter providesNewsAdapter(Utils utils, List<New> newsList, ImageLoader imageLoader, OnItemClickListener onItemClickListener){
        return new NewsAdapter(utils, newsList, imageLoader, onItemClickListener);
    }

    @Provides @Singleton
    OnItemClickListener providesOnItemClickListener(){
        return this.onItemClickListener;
    }

    @Provides @Singleton
    List<New> providesNewsList(){
        return new ArrayList<New>();
    }
}
