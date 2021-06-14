package com.example.levelus;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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
    private FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();
    private FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();
    String uid = firebaseUser.getUid();

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView quest_name;
        Button agree_button, disagree_button;

        ViewHolder(View view) {
            super(view);
            quest_name = view.findViewById(R.id.recommend_quest_name);
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

//        iRef.child(uid).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
//                holder.agree_button.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        QuestlogInfo questlogInfo = new QuestlogInfo();
//                        questlogInfo.setFinished_date("0000-00-00");
//                        questlogInfo.setAccepted_date(getTime);
//                        questlogInfo.setCategory(cData.getCategory());
//                        questlogInfo.setQuest_num(cData.getQuest_num());
//                        questlogInfo.setRating("0");
//                        questlogInfo.setTitle_ko(cData.getTitle_ko());
//                        for (int i = 0; i < 233; i++) {
//                            System.out.println(iRef.child(uid).child(Integer.toString(i)));
//                            if(iRef.child(uid).child(Integer.toString(i)).equals("null")){
//                                iRef.child(uid).child(Integer.toString(i)).setValue(questlogInfo);
//                            }
//                        }
//                    }
//                });
//                notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(@NonNull @NotNull DatabaseError error) {
//
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return rData.size();
    }


}
