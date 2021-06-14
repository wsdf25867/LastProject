package com.example.levelus;

import android.content.Context;
import android.content.Intent;
import android.icu.text.SymbolTable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.sql.Ref;
import java.util.ArrayList;


public class MyQuestFragment extends Fragment implements LoggedPages.onKeyBackPressedListener{

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference rRef = firebaseDatabase.getReference("recommend_list");
    private DatabaseReference iRef = firebaseDatabase.getReference("quest_log");
    private FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();
    private FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();

    String uid = firebaseUser.getUid();

    ArrayList<QuestInfo> rData = new ArrayList<>();
    ArrayList<QuestlogInfo> iData = new ArrayList<>();
    ArrayList<String> qData = new ArrayList<>();

    RecommendListAdapter rAdapter;
    IngQuestAdapter iAdapter;


    public MyQuestFragment() {
        // Required empty public constructor
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
        RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.recycleView);
        RecyclerView recyclerView2 = (RecyclerView) v.findViewById(R.id.recycleView2);
        recyclerView.setHasFixedSize(true);
        recyclerView2.setHasFixedSize(true);


        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        RecyclerView.LayoutManager mLayoutManager2 = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView2.setLayoutManager(mLayoutManager2);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView2.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(rAdapter);
        recyclerView2.setAdapter(iAdapter);

        return v;
    }

    public void webPractice() {
        Intent GoToWebPractice2 = new Intent(getActivity(), WebPractice2.class);
        GoToWebPractice2.putExtra("uid", uid);
        getActivity().startActivity(GoToWebPractice2);
    }

    public void getRecommendData() {
        rRef.child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                rData.clear();
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){

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
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    QuestlogInfo questlogInfo = dataSnapshot.getValue(QuestlogInfo.class);
                    if(questlogInfo.getRating().equals("0")){
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
    @Override public void onAttach(Context context) {
        super.onAttach(context);
        ((LoggedPages)context).setOnKeyBackPressedListener(this::onBackKey);
    }
}
