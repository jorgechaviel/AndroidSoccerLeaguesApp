package com.jchaviel.soccerleaguesapp.clasification.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.jchaviel.soccerleaguesapp.R;
import com.jchaviel.soccerleaguesapp.entities.Team;
import java.util.List;
import butterknife.Bind;
import butterknife.ButterKnife;

public class ClassificationAdapter extends RecyclerView.Adapter<ClassificationAdapter.ViewHolder> {

    private List<Team> teamList;

    public ClassificationAdapter(List<Team> teamList) {
        this.teamList = teamList;
    }

    @NonNull
    @Override
    public ClassificationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.team_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClassificationAdapter.ViewHolder holder, int position) {
        holder.mName.setText(teamList.get(position).getName());
        holder.mMatchesPlayed.setText(teamList.get(position).getMatchesPlayed());
        holder.mWins.setText(teamList.get(position).getWins());
        holder.mDraws.setText(teamList.get(position).getDraws());
        holder.mLoss.setText(teamList.get(position).getLoss());
        holder.mGoalDiff.setText(teamList.get(position).getGoalDiff());
        holder.mPoints.setText(teamList.get(position).getPoints());
        holder.mRank.setText(teamList.get(position).getRank());
    }

    public void addTeam(Team team) {
        teamList.add(team);
        notifyDataSetChanged();
    }

    public void clearTeams() {
        teamList.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return this.teamList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.table_team_name)
        TextView mName;
        @Bind(R.id.table_matches_played)
        TextView mMatchesPlayed;
        @Bind(R.id.table_wins)
        TextView mWins;
        @Bind(R.id.table_draw)
        TextView mDraws;
        @Bind(R.id.table_loss)
        TextView mLoss;
        @Bind(R.id.table_goal_diff)
        TextView mGoalDiff;
        @Bind(R.id.table_points)
        TextView mPoints;
        @Bind(R.id.table_rank)
        TextView mRank;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
