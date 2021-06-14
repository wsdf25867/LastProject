package com.example.levelus;

import android.content.Context;
import android.content.Intent;

import android.content.SharedPreferences;
import android.util.Log;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import java.util.ArrayList;

public class IngQuestAdapter extends RecyclerView.Adapter<IngQuestAdapter.ViewHolder> {

    ArrayList<QuestlogInfo> iData;



    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference mDatabaseRef = firebaseDatabase.getReference("quest_log");
    private FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();
    private FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();

    String uid = firebaseUser.getUid();
    private Context context;

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView ing_quest_name;
        Button check_button, giveup_button;

        ViewHolder(View view) {
            super(view);
            // 뷰 객체에 대한 참조. (hold strong reference)
            ing_quest_name = (TextView) view.findViewById(R.id.ing_quest_name);
            check_button = (Button) view.findViewById(R.id.check_button);
            giveup_button = (Button) view.findViewById(R.id.giveup_button);
        }
    }

    public IngQuestAdapter(ArrayList<QuestlogInfo> iData) {
        this.iData = iData;
    }


    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ing_list_adapter, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {

        holder.ing_quest_name.setText(iData.get(position).getTitle_ko()); //진행 퀘스트 제목 출력

        mDatabaseRef.child(uid).addValueEventListener(new ValueEventListener() { //퀘스트 포기
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                holder.giveup_button.setOnClickListener(new View.OnClickListener() { //포기 버튼
                    @Override
                    public void onClick(View v) {
                        for (int i = 0; i < 222; i++) {
                            QuestlogInfo questlogInfo = snapshot.child(Integer.toString(i)).getValue(QuestlogInfo.class);
                            if (questlogInfo != null) {
                                if (iData.get(position).getTitle_ko().equals(questlogInfo.getTitle_ko())) {
                                    mDatabaseRef.child(uid).child(Integer.toString(i)).removeValue();
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


        holder.check_button.setOnClickListener(new View.OnClickListener() { //검증버튼
            @Override
            public void onClick(View v) {
                Context context = v.getContext();

                Intent intent = new Intent(v.getContext(), ImageLabellingActivity.class);
                intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK);
                firebaseDatabase.getReference("quest").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        for (int i = 0; i < 222; i++) {
                            QuestInfo questInfo = snapshot.child(Integer.toString(i)).getValue(QuestInfo.class);
                            if (questInfo != null) {
                                if (questInfo.getTitle_ko().equals(iData.get(position).getTitle_ko())) {
                                    intent.putExtra("title_ko", questInfo.getTitle_ko());
                                    intent.putExtra("keyword", questInfo.getKeyword());
                                    intent.putExtra("way", questInfo.getWay());
                                    intent.putExtra("quest_num", questInfo.getQuest_num());
                                    notifyDataSetChanged();

                                    break;
                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }
                });
                v.getContext().startActivity(intent);

//                Intent intent = new Intent(context, ImageLabellingActivity.class);
//                firebaseDatabase.getReference("quest").addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
//                        for (int i = 0; i < 222; i++) {
//                            QuestInfo questInfo = snapshot.child(Integer.toString(i)).getValue(QuestInfo.class);
//                            if (questInfo != null) {
//                                if (questInfo.getTitle_ko().equals(qData.get(position).getTitle_ko())) {
//                                    intent.putExtra("title_ko", questInfo.getTitle_ko());
//                                    intent.putExtra("keyword", questInfo.getKeyword());
//                                    intent.putExtra("way", questInfo.getWay());
//                                    intent.putExtra("quest_num", questInfo.getQuest_num());
//                                    break;
//                                }
//                            }
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull @NotNull DatabaseError error) {
//
//                    }
//                });

                intent.putExtra("title_ko","test title_ko");
                intent.putExtra("keyword","test keyword");
                intent.putExtra("way","test way");
                intent.putExtra("quest_num","test_quest_num");

                    //안드로이드 자체 db실패
//                SharedPreferences sharedPreferences= context.getSharedPreferences("test", context.MODE_PRIVATE);    // test 이름의 기본모드 설정
//                SharedPreferences.Editor editor= sharedPreferences.edit(); //sharedPreferences를 제어할 editor를 선언
//                editor.putString("inputText","test"); // key,value 형식으로 저장
//                editor.commit();    //최종 커밋. 커밋을 해야 저장이 된다.
                context.startActivity(intent);

            }
        });


    }

    @Override
    public int getItemCount() {
        return iData.size();
    }




}

