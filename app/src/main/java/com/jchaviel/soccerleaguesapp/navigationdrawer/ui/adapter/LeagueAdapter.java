package com.jchaviel.soccerleaguesapp.navigationdrawer.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.jchaviel.soccerleaguesapp.R;
import com.jchaviel.soccerleaguesapp.entities.League;
import java.util.List;
import butterknife.Bind;
import butterknife.ButterKnife;

public class LeagueAdapter extends RecyclerView.Adapter<LeagueAdapter.ViewHolder>{

    private List<League> leagueList;

    public LeagueAdapter(List<League> leagueList) {
        this.leagueList = leagueList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.league_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        League league = this.leagueList.get(position);
        holder.textViewLeagueName.setText(league.getName());
        holder.imageLeagueLogo.setImageResource(league.getLogoId());
    }

    @Override
    public int getItemCount() {
        return this.leagueList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.league_logo)
        ImageView imageLeagueLogo;
        @Bind(R.id.league_name)
        TextView textViewLeagueName;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
