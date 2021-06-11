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

import java.util.ArrayList;


public class MyQuestFragment extends Fragment implements LoggedPages.onKeyBackPressedListener{

    private ArrayList<QuestInfo> list = new ArrayList<>();
    private ArrayList<QuestlogInfo> qlist = new ArrayList<>();

    private RecyclerView recyclerView;
    private RecyclerView recyclerView2;
    private SimpleTextAdapter mAdapter;
    private IngQuestAdapter ingQuestAdapter;

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    //    private DatabaseReference mDatabaseRef = firebaseDatabase.getReference("recommend_list");
//    private DatabaseReference mDatabaseRef2 = firebaseDatabase.getReference("quest_log");
    private FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();
    private FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();

    String uid = firebaseUser.getUid();



    public MyQuestFragment() {
        // Required empty public constructor
    }
    // TODO: Rename and change types and number of parameters


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        webPractice();
        prepareData();
        prepareLogData();

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_my_quest, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.recycleView);
        recyclerView2 = (RecyclerView) v.findViewById(R.id.recycleView2);
        recyclerView.setHasFixedSize(true);
        recyclerView2.setHasFixedSize(true);
        mAdapter = new SimpleTextAdapter(list);
        ingQuestAdapter = new IngQuestAdapter(qlist);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        RecyclerView.LayoutManager mLayoutManager2 = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView2.setLayoutManager(mLayoutManager2);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView2.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        recyclerView2.setAdapter(ingQuestAdapter);

        return v;
    }


    public void prepareData() {
        DatabaseReference ref = firebaseDatabase.getReference("recommend_list").child(uid);
        for (int i = 0; i < 10; i++) {
            ref.child(Integer.toString(i)).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                    QuestInfo questInfo = snapshot.getValue(QuestInfo.class);
                    if (questInfo != null) {
                        list.add(questInfo);
                    }

                }

                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {

                }
            });
        }

    }

    public void prepareLogData() {
        for (int i = 0; i < 222; i++) {
            firebaseDatabase.getReference("quest_log").child(uid).child(Integer.toString(i)).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                    QuestlogInfo questlogInfo = snapshot.getValue(QuestlogInfo.class);
                    if (questlogInfo != null) {
                        if (questlogInfo.getRating().equals("0")) {
                            qlist.add(questlogInfo);
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {

                }
            });
        }

    }

    public void webPractice(){
        Intent GoToWebPractice2 = new Intent(getActivity(),WebPractice2.class);
        GoToWebPractice2.putExtra("uid",uid);
        startActivity(GoToWebPractice2);
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
