package com.example.levelus;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static androidx.core.content.ContextCompat.getSystemService;


public class MyQuestFragment extends Fragment implements LoggedPages.onKeyBackPressedListener {

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference rRef = firebaseDatabase.getReference("recommend_list");
    private DatabaseReference iRef = firebaseDatabase.getReference("quest_log");
    private FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();
    private FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();

    String uid = firebaseUser.getUid();

    ArrayList<QuestInfo> rData = new ArrayList<>();
    ArrayList<QuestlogInfo> iData = new ArrayList<>();

    RecommendListAdapter rAdapter;
    IngQuestAdapter iAdapter;

    TextView title_recommend, title_ing;




    public MyQuestFragment() {

    }
    // TODO: Rename and change types and number of parameters


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        webPractice();
        getRecommendData();
        getIngQuest();

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_my_quest, container, false);
        RecyclerView recyclerView_recommend = (RecyclerView) v.findViewById(R.id.recycleView_recommend);
        RecyclerView recyclerView_ing = (RecyclerView) v.findViewById(R.id.recycleView_ing);
        GridLayoutManager gridlayoutManager;

        title_recommend = (TextView) v.findViewById(R.id.title_recommend);
        title_ing = (TextView) v.findViewById(R.id.title_ing);


        recyclerView_recommend.setHasFixedSize(true);
        recyclerView_ing.setHasFixedSize(true);

        gridlayoutManager = new GridLayoutManager(getActivity(), 6);
        gridlayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
//                int gridPosition = position % 5;
//                switch (gridPosition) {
//                    case 0:
//                    case 1:
//                    case 2:
//                        return 2;
//                    case 3:
//                    case 4:
//                        return 3;
//                }
                return 3;
            }
        });


        RecyclerView.LayoutManager mLayoutManager2 = new LinearLayoutManager(getActivity());
        recyclerView_recommend.setLayoutManager(gridlayoutManager);
        recyclerView_ing.setLayoutManager(mLayoutManager2);
        recyclerView_recommend.setItemAnimator(new DefaultItemAnimator());
        recyclerView_ing.setItemAnimator(new DefaultItemAnimator());
        recyclerView_recommend.setAdapter(rAdapter);
        recyclerView_ing.setAdapter(iAdapter);

        recyclerView_recommend.setVisibility(View.VISIBLE);
        recyclerView_ing.setVisibility(View.INVISIBLE);
        title_recommend.setTextColor(Color.parseColor("#0E1621"));
        title_ing.setTextColor(Color.parseColor("#9FA7C1"));

        title_recommend.setOnClickListener(new View.OnClickListener() { // 추천 버튼
            @Override
            public void onClick(View v) {
                recyclerView_recommend.setVisibility(View.VISIBLE);
                recyclerView_ing.setVisibility(View.INVISIBLE);
                title_recommend.setTextColor(Color.parseColor("#0E1621"));
                title_ing.setTextColor(Color.parseColor("#9FA7C1"));

            }
        });

        title_ing.setOnClickListener(new View.OnClickListener() { // 진행중 버튼
            @Override
            public void onClick(View v) {
                recyclerView_recommend.setVisibility(View.INVISIBLE);
                recyclerView_ing.setVisibility(View.VISIBLE);
                title_ing.setTextColor(Color.parseColor("#0E1621"));
                title_recommend.setTextColor(Color.parseColor("#9FA7C1"));

            }
        });


        return v;
    }

    public void webPractice() {
        Intent GoToWebPractice2 = new Intent(getActivity(), WebPractice2.class);
        GoToWebPractice2.putExtra("uid", uid);
        startActivity(GoToWebPractice2);
    }

    public void getRecommendData() {
        rRef.child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                rData.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    QuestInfo questInfo = dataSnapshot.getValue(QuestInfo.class);
                    rData.add(questInfo);

                }
                rAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
        rAdapter = new RecommendListAdapter(rData);
    }

        public void getIngQuest() {
        iRef.child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                iData.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    QuestlogInfo questlogInfo = dataSnapshot.getValue(QuestlogInfo.class);
                    if (questlogInfo.getRating().equals("0")) {
                        iData.add(questlogInfo);
                    }
                }
                iAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
        iAdapter = new IngQuestAdapter(iData);
    }

    @Override
    public void onBackKey() {
        LoggedPages activity = (LoggedPages) getActivity();
        activity.setOnKeyBackPressedListener(null);
        activity.onBackPressed();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ((LoggedPages) context).setOnKeyBackPressedListener(this::onBackKey);
    }
}
