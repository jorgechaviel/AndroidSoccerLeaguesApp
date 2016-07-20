package com.jchaviel.soccerleaguesapp.news.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jchaviel.soccerleaguesapp.R;
import com.jchaviel.soccerleaguesapp.domain.Utils;
import com.jchaviel.soccerleaguesapp.entities.New;
import com.jchaviel.soccerleaguesapp.lib.base.ImageLoader;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by jchavielreyes on 7/14/16.
 */
public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    @Bind(R.id.image_progress_bar)
    ProgressBar imageProgressBar;
    private Utils utils;
    private List<New> newsList;
    private ImageLoader imageLoader;
    private OnItemClickListener onItemClickListener;

    public NewsAdapter(Utils utils, List<New> newsList, ImageLoader imageLoader, OnItemClickListener onItemClickListener) {
        this.utils = utils;
        this.newsList = newsList;
        this.imageLoader = imageLoader;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        New currentNew = newsList.get(position);
        holder.setOnItemClickListener(currentNew, onItemClickListener);

        holder.titleNews.setText(currentNew.getTitle());
        holder.dateNews.setText(currentNew.getDate());
        if (newsList.get(position).getImageLink() != null) {
            imageLoader.load(holder.imageNews, currentNew.getImageLink());
        } else holder.imageNews.setImageResource(R.mipmap.ic_launcher);
    }

    public void addNew(New objNew) {
        newsList.add(objNew);
        notifyDataSetChanged();
    }

    public void clearNews() {
        newsList.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.image_news)
        ImageView imageNews;
        @Bind(R.id.title_news)
        TextView titleNews;
        @Bind(R.id.date_news)
        TextView dateNews;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setOnItemClickListener(final New objNews, final OnItemClickListener listener) {
            imageNews.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(objNews);
                }
            });
        }
    }
}
