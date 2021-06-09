package com.example.levelus;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class RankAdapter extends RecyclerView.Adapter<RankAdapter.ViewHolder> {
    private ArrayList<UserAccount> rankList = new ArrayList<UserAccount>();

    public static class ViewHolder extends RecyclerView.ViewHolder {
        protected TextView text_name, text_rank;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            this.text_name = itemView.findViewById(R.id.text_name);
            this.text_rank = itemView.findViewById(R.id.text_rank);
        }
    }

    public RankAdapter(ArrayList<UserAccount> rankList) {
        this.rankList = rankList;
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
        holder.text_name.setText(rankList.get(position).getName());
        holder.text_rank.setText(Integer.toString(rankList.get(position).getLevel()));
        holder.text_rank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String clickedToken = holder.text_rank.getText().toString();
                Toast.makeText(v.getContext(),clickedToken,Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return (null != rankList ? rankList.size() : 0);
    }
}
