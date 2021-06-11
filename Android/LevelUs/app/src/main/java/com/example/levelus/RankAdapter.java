package com.example.levelus;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;

public class RankAdapter extends RecyclerView.Adapter<RankAdapter.ViewHolder> {
    private ArrayList<UserAccount> rankList = new ArrayList<UserAccount>();
    public static class ViewHolder extends RecyclerView.ViewHolder {
        protected TextView text_name, text_level, rank;
        protected ImageView highest_rank_imageView;
        protected Context context;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            this.rank = itemView.findViewById(R.id.rank);
            this.text_name = itemView.findViewById(R.id.text_name);
            this.text_level = itemView.findViewById(R.id.text_level);
            this.highest_rank_imageView = itemView.findViewById(R.id.highest_rank_imageView);
//            gold_medal = context.getResources().getDrawable(R.drawable.gold_medal);
//            silver_medal = context.getResources().getDrawable(R.drawable.silver_medal);
//            bronze_medal = context.getResources().getDrawable(R.drawable.bronze_medal);
        }
    }

    public RankAdapter(ArrayList<UserAccount> rankList) {
        this.rankList = rankList;
        Collections.sort(this.rankList);
    }

    @NonNull
    @NotNull
    @Override
    public RankAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rank_list, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RankAdapter.ViewHolder holder, int position) {
        int rank = holder.getAdapterPosition();
        holder.text_name.setText(rankList.get(position).getName());
        holder.text_level.setText(Integer.toString(rankList.get(position).getLevel()));
//        holder.highest_rank.setImageDrawable(gold_medal);
        switch (rank){
            case 0: holder.highest_rank_imageView.setBackgroundResource(R.drawable.gold_medal); break;
            case 1: holder.highest_rank_imageView.setBackgroundResource(R.drawable.silver_medal); break;
            case 2: holder.highest_rank_imageView.setBackgroundResource(R.drawable.bronze_medal); break;
            default : holder.rank.setText("rank. "+(rank+1)); break;
        }
        holder.text_level.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String clickedToken = holder.text_level.getText().toString();
                Toast.makeText(v.getContext(),clickedToken,Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return (null != rankList ? rankList.size() : 0);
    }
}
