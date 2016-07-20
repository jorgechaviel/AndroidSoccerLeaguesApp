package com.jchaviel.soccerleaguesapp.schedule.ui.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.andexert.library.RippleView;
import com.jchaviel.soccerleaguesapp.R;
import com.jchaviel.soccerleaguesapp.domain.Utils;
import com.jchaviel.soccerleaguesapp.entities.Fixture;
import com.jchaviel.soccerleaguesapp.lib.base.ImageLoader;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by jchavielreyes on 7/16/16.
 */
public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ViewHolder> {

    private Utils utils;
    private List<Fixture> fixtureList;
    private ImageLoader imageLoader;

    public ScheduleAdapter(Utils utils, List<Fixture> fixtureList, ImageLoader imageLoader) {
        this.utils = utils;
        this.fixtureList = fixtureList;
        this.imageLoader = imageLoader;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fixture_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.team1Name.setText(fixtureList.get(position).getHomeTeam());
        holder.team2Name.setText(fixtureList.get(position).getAwayTeam());
        holder.date.setText(fixtureList.get(position).getDate());
        holder.team1Score.setText(fixtureList.get(position).getHomeTeamScore());
        holder.team2Score.setText(fixtureList.get(position).getAwayTeamScore());

        //For some, time can be null as past fixtures will only have status and not time
        if (fixtureList.get(position).getTime() != null && !fixtureList.get(position).getTime().isEmpty()) {
            holder.status.setText(fixtureList.get(position).getTime());
        } else {
            holder.status.setText(fixtureList.get(position).getStatus());
        }

        if (fixtureList.get(position).getHomeTeamLogo()!=null){
            imageLoader.load(holder.team1Logo, fixtureList.get(position).getHomeTeamLogo());
        }
        else holder.team1Logo.setImageResource(R.mipmap.ic_launcher);

        if (fixtureList.get(position).getAwayTeamLogo()!=null){
            imageLoader.load(holder.team2Logo, fixtureList.get(position).getAwayTeamLogo());
        }
        else holder.team2Logo.setImageResource(R.mipmap.ic_launcher);
    }

    public void addFixture(Fixture fixture) {
        fixtureList.add(fixture);
        notifyDataSetChanged();
    }

    public void clearFixtures() {
        fixtureList.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return fixtureList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.team2_logo)
        ImageView team2Logo;
        @Bind(R.id.team2_name)
        TextView team2Name;
        @Bind(R.id.team2_score)
        TextView team2Score;
        @Bind(R.id.team1_logo)
        ImageView team1Logo;
        @Bind(R.id.team1_name)
        TextView team1Name;
        @Bind(R.id.team1_score)
        TextView team1Score;
        @Bind(R.id.date)
        TextView date;
        @Bind(R.id.status)
        TextView status;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
