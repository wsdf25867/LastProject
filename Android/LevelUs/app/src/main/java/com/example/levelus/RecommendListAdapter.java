package com.example.levelus;

import android.content.Context;
import android.content.Intent;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;


public class RecommendListAdapter extends RecyclerView.Adapter<RecommendListAdapter.ViewHolder> {

    ArrayList<QuestInfo> rData = new ArrayList<>();
    ArrayList<QuestlogInfo> iData = new ArrayList<>();


    //오늘 날짜 받아서 String 변환
    long now = System.currentTimeMillis();
    Date date = new Date(now);
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    String getTime = sdf.format(date);

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference rRef = firebaseDatabase.getReference("recommend_list");
    private DatabaseReference iRef = firebaseDatabase.getReference("quest_log");
    private DatabaseReference qRef = firebaseDatabase.getReference("quest");
    private DatabaseReference rjRef = firebaseDatabase.getReference("rejected_list");
    private FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();
    private FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();
    private FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
    private StorageReference storageRef = firebaseStorage.getReference();
    String uid = firebaseUser.getUid();

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView quest_name, quest_category, quest_achievement;
        CircleImageView quest_thumbnail;
        Button agree_button, disagree_button;

        ViewHolder(View view) {
            super(view);
            quest_name = view.findViewById(R.id.recommend_quest_name);
            quest_category = view.findViewById(R.id.recommend_quest_category);
            quest_achievement = view.findViewById(R.id.recommend_quest_achievement);
            quest_thumbnail = view.findViewById(R.id.recommend_quest_thumbnail);
            agree_button = view.findViewById(R.id.agree_button);
            disagree_button = view.findViewById(R.id.disagree_button);

        }
    }

    public RecommendListAdapter(ArrayList<QuestInfo> list) {
        this.rData = list;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recommend_list_adapter, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecommendListAdapter.ViewHolder holder, int position) {
        QuestInfo cData = rData.get(position);

        holder.quest_name.setText(rData.get(position).getTitle_ko());
        holder.quest_category.setText("#" + rData.get(position).getCategory());
        holder.quest_achievement.setText("#" + rData.get(position).getAchievement());

        switch((int)((Math.random()*10000)%3)){
            case 0:
                holder.itemView.setBackgroundResource(R.drawable.rectangle_questlist_blue);
                break;
            case 1:
                holder.itemView.setBackgroundResource(R.drawable.rectangle_questlist_mint);
                break;
            case 2:
                holder.itemView.setBackgroundResource(R.drawable.rectangle_questlist_orange);
                break;
        }

        storageRef.child("quest_thumbnail/" + rData.get(position).getQuest_num() + ".jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(holder.itemView.getContext()).load(uri).into(holder.quest_thumbnail);
            }
        });


        holder.agree_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "퀘스트를 수락했습니다. 진행중 페이지로 이동해 확인하세요.", Toast.LENGTH_SHORT).show();
                //로그에 넣을 데이터
                QuestlogInfo questlogInfo = new QuestlogInfo();

                questlogInfo.setTitle_ko(cData.getTitle_ko());
                questlogInfo.setRating("0");
                questlogInfo.setQuest_num(cData.getQuest_num());
                questlogInfo.setCategory(cData.getCategory());
                questlogInfo.setAccepted_date(getTime);
                questlogInfo.setFinished_date("0000-00-00");
                questlogInfo.setAchievement(cData.getAchievement());
                questlogInfo.setPeriod(cData.getPeriod());
                questlogInfo.setDifficulty(cData.getDifficulty());


                //퀘스트에 added에 1더하기
                QuestInfo questInfo = new QuestInfo();

                int added = Integer.parseInt(cData.getAdded()) + 1;
                String addeds= String.valueOf(added);

//                questInfo.setTitle(cData.getTitle());
//                questInfo.setTitle_ko(cData.getTitle_ko());
//                questInfo.setAdded(Integer.toString(added));
//                questInfo.setCategory(cData.getCategory());
//                questInfo.setDifficulty(cData.getDifficulty());
//                questInfo.setDone(cData.getDone());
//                questInfo.setKeyword(cData.getKeyword());
//                questInfo.setPeriod(cData.getPeriod());
//                questInfo.setWay(cData.getWay());
//                questInfo.setQuest_num(cData.getQuest_num());
//                questInfo.setAchievement(cData.getAchievement());

                iRef.child(uid).child(cData.getQuest_num()).setValue(questlogInfo);

                qRef.child("ALL").child(cData.getQuest_num()).child("added").setValue(addeds);
                System.out.println("여기까지왔음2");


                Intent intent = new Intent(v.getContext(),WebPractice2.class);
                intent.putExtra("uid",uid);
                v.getContext().startActivity(intent);

            }
        });

        rRef.child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                holder.disagree_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        for(int i=0;i<10;i++){
                            if(snapshot.child(Integer.toString(i)).exists()){
                                Toast.makeText(v.getContext(), "퀘스트를 거절했습니다. 해당 퀘스트를 제외한 추천 퀘스트 목록을 제공합니다.", Toast.LENGTH_SHORT).show();
                                QuestInfo questInfo = snapshot.child(Integer.toString(i)).getValue(QuestInfo.class);
                                if(questInfo.getQuest_num().equals(cData.getQuest_num())){
                                    rRef.child(uid).child(Integer.toString(i)).removeValue();
                                    rjRef.child(uid).child(cData.getQuest_num()).setValue(questInfo);
                                    Intent intent = new Intent(v.getContext(),WebPractice2.class);
                                    intent.putExtra("uid",uid);
                                    v.getContext().startActivity(intent);
                                    break;
                                }
                            }
                        }
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return rData.size();
    }


}
