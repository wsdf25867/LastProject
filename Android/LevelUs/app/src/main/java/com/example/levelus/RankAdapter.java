package com.example.levelus;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;

public class RankAdapter extends RecyclerView.Adapter<RankAdapter.ViewHolder> {
    private ArrayList<UserAccount> rankList = new ArrayList<UserAccount>();
//    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
//    private FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

    private FirebaseStorage storage;
    private StorageReference storageRef;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        protected TextView text_name, text_level, rank;
        protected ImageView highest_rank_imageView;
        protected Context context;
        protected CircleImageView user_profile;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            this.rank = itemView.findViewById(R.id.rank);
            this.text_name = itemView.findViewById(R.id.text_name);
            this.text_level = itemView.findViewById(R.id.text_level);
            this.highest_rank_imageView = itemView.findViewById(R.id.highest_rank_imageView);
            this.user_profile = itemView.findViewById(R.id.user_profile);
        }
    }

    public RankAdapter(ArrayList<UserAccount> rankList) {
        this.rankList = rankList;
        Collections.sort(this.rankList);

        storage = FirebaseStorage.getInstance("gs://collabtest-71a4d.appspot.com");
        storageRef = storage.getReference();

    }

    @NonNull
    @NotNull
    @Override
    public RankAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        Collections.sort(this.rankList);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rank_list, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RankAdapter.ViewHolder holder, int position) {
        int rank = holder.getAdapterPosition();
        holder.text_name.setText(rankList.get(position).getName());
        holder.text_level.setText(rankList.get(position).getLevel());

        if(storageRef.child(rankList.get(position).getIdToken()+"/profile_img") != null){
            StorageReference submitProfile = storageRef.child(rankList.get(position).getIdToken()+"/profile_img");
            submitProfile.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if(task.isSuccessful())
                        Glide.with(holder.itemView.getContext()).load(task.getResult()).into(holder.user_profile);
                }

            });
        }

        switch (rank){
            case 0:
                holder.highest_rank_imageView.setImageResource(R.drawable.gold_medal);
                holder.rank.setVisibility(View.INVISIBLE);
                holder.rank.setTextColor(Color.parseColor("#EDDA2E"));
                holder.rank.setTextSize(30);
                break;
            case 1:
                holder.highest_rank_imageView.setImageResource(R.drawable.silver_medal);
                holder.rank.setVisibility(View.INVISIBLE);
                holder.rank.setTextColor(Color.parseColor("#DDD5D5"));
                holder.rank.setTextSize(30);
                break;
            case 2:
                holder.highest_rank_imageView.setImageResource(R.drawable.bronze_medal);
                holder.rank.setVisibility(View.INVISIBLE);
                holder.rank.setTextColor(Color.parseColor("#CF6B4B"));
                holder.rank.setTextSize(30);
                break;
            default : holder.rank.setText((rank+1)+"위"); holder.highest_rank_imageView.setVisibility(View.INVISIBLE); break;
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
