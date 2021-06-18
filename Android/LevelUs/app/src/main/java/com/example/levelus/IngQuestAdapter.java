package com.example.levelus;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
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
import org.w3c.dom.Text;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class IngQuestAdapter extends RecyclerView.Adapter<IngQuestAdapter.ViewHolder> {

    ArrayList<QuestlogInfo> qData;
    String quest_num;   //검증부분(imageLabelling으로 보낼 퀘스트 번호)


    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference mDatabaseRef = firebaseDatabase.getReference("quest_log");
    private FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();
    private FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();
    private FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
    private StorageReference storageRef = firebaseStorage.getReference();

    String uid = firebaseUser.getUid();
    private Context context;

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView ing_quest_name, ing_quest_category, ing_quest_achievement, ing_quest_difficulty;
        Button check_button, giveup_button;
        CircleImageView ing_quest_thumbnail;

        ViewHolder(View view) {
            super(view);
            // 뷰 객체에 대한 참조. (hold strong reference)
            ing_quest_name = (TextView) view.findViewById(R.id.ing_quest_name);
            ing_quest_category = (TextView) view.findViewById(R.id.ing_quest_category);
            ing_quest_achievement = (TextView) view.findViewById(R.id.ing_quest_achievement);
            check_button = (Button) view.findViewById(R.id.check_button);
            giveup_button = (Button) view.findViewById(R.id.giveup_button);
            ing_quest_thumbnail = view.findViewById(R.id.ing_quest_thumbnail);
            ing_quest_difficulty = (TextView) view.findViewById(R.id.ing_quest_difficulty);

        }
    }

    public IngQuestAdapter(ArrayList<QuestlogInfo> ing_list) {
        this.qData = ing_list;
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

        holder.ing_quest_name.setText(qData.get(position).getTitle_ko()); //진행 퀘스트 제목 출력
        holder.ing_quest_category.setText("#" + qData.get(position).getCategory()); // 진행 퀘스트 카테고리 출력
        holder.ing_quest_achievement.setText("#" + qData.get(position).getAchievement()); // 진행 퀘스트 성취 분야 출력
        holder.ing_quest_difficulty.setText("난이도 : " + qData.get(position).getDifficulty()); // 진행 퀘스트 난이도 출력

        storageRef.child("quest_thumbnail/" + qData.get(position).getQuest_num() + ".jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(holder.itemView.getContext()).load(uri).into(holder.ing_quest_thumbnail);
            }
        });


        mDatabaseRef.child(uid).addValueEventListener(new ValueEventListener() { //퀘스트 포기
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                holder.giveup_button.setOnClickListener(new View.OnClickListener() { //포기 버튼
                    @Override
                    public void onClick(View v) {
                        for (int i = 0; i < 222; i++) {
                            QuestlogInfo questlogInfo = snapshot.child(Integer.toString(i)).getValue(QuestlogInfo.class);
                            if (questlogInfo != null) {
                                if (qData.get(position).getTitle_ko().equals(questlogInfo.getTitle_ko())) {
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
            String quest_num;
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, ImageLabellingActivity.class);
                mDatabaseRef.child(uid).addValueEventListener(new ValueEventListener() {
                    QuestlogInfo[] questlogInfo = new QuestlogInfo[222];
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            for (int i = 0; i < 222; i++) {
                                System.out.println("해당 퀘스트 타이틀");
                                System.out.println(qData.get(position).getTitle_ko());  //이거 뜨고
                                questlogInfo[i] = snapshot.child(Integer.toString(i)).getValue(QuestlogInfo.class);
                                System.out.println(questlogInfo[i]);
                                if(questlogInfo[i] != null) {
                                    if (qData.get(position).getTitle_ko().equals(questlogInfo[i].getTitle_ko())) {
                                        System.out.println("해당 퀘스트와 일치하는 퀘스트 넘버");
                                        System.out.println(questlogInfo[i].getQuest_num());     //얘도 뜬다. 근데 데이터가 안넘어가네?
                                        quest_num = questlogInfo[i].getQuest_num();
//                                    intent.putExtra("quest_num",quest_num);       //여기서 넣으면 안넘어가짐
//                                    intent.putExtra("title_ko","test title_ko");  //여기서 넣으면 안넘어가짐
                                        System.out.println(quest_num);
                                        break;
                                    }
                                }
                            }
                        }
                        //여기가 onDataChange메소드 끝나기 직전 부분
//                        System.out.println("onDataChange메소드 끝나기 직전부분");
//                        System.out.println("quest_num :"+quest_num);
//                        intent.putExtra("title_ko","test title_ko");
//                        intent.putExtra("quest_num",quest_num);

                        //여기서도 intent로 넘어가지가 않는다.
                        intent.putExtra("quest_num",quest_num);
//                        intent.addFlags(intent.FLAG_ACTIVITY_NEW_TASK | intent.FLAG_ACTIVITY_CLEAR_TASK | intent.FLAG_ACTIVITY_CLEAR_TOP);
                        context.startActivity(intent);



                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }
                });
                //여기서 intent로 값을 지정해줘야만 imageLabelling에서 받을 수 있는데, 여기는 onDataChange메소드 전에 실행이 된다..이 무슨 모순이냐?

//                intent.putExtra("title_ko","test title_ko");
//                intent.putExtra("quest_num","4");
//                System.out.println("넘기기 직전 intent위치 바꾼 결과는?");
//                System.out.println(quest_num);
                //intent의 위치는 상관 없었음. 지금 intent로 값을 지정하는 행위 자체가 onDataChange메소드보다 먼저 실행 되어버림.
                //onDataChange이 메소드 뒤에 quest_num을 넘길 방법을 찾아야함.

            }
        });



    }

    @Override
    public int getItemCount() {
        return qData.size();
    }
}