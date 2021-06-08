package com.example.levelus;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;


public class MyQuestFragment extends Fragment {

    private ArrayList<QuestInfo> list = new ArrayList<>();
    private RecyclerView recyclerView;
    private SimpleTextAdapter mAdapter;

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference mDatabaseRef = firebaseDatabase.getReference("Level Us");
    private FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();
    private FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();



    public MyQuestFragment() {
        // Required empty public constructor
    }
    // TODO: Rename and change types and number of parameters


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        String uid = firebaseUser.getUid();
        ArrayList<QuestInfo> list = new ArrayList<>();

        for(int i=0;i<10;i++) {

            String qNum = Integer.toString(i);

            mDatabaseRef.child("recommend_list").child(uid).child(qNum).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                    if (snapshot.exists()) {

                        QuestInfo questInfo = snapshot.getValue(QuestInfo.class);

                        list.add(questInfo);
                    }
                }

                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {

                }
            });
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_my_quest, container, false);

        recyclerView = (RecyclerView)v.findViewById(R.id.recycleView);
        recyclerView.setHasFixedSize(true);
        mAdapter = new SimpleTextAdapter(list);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        return v;
    }

}