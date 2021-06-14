package com.example.levelus;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RankFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RankFragment extends Fragment implements LoggedPages.onKeyBackPressedListener{
//    static boolean isCurUser = false;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

//    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
//    private DatabaseReference databaseReference = firebaseDatabase.getReference("Level Us");
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

    private ArrayList<UserAccount> rankList;
    private RankAdapter rankAdapter;
    private RecyclerView rankRecyclerView;
    private LinearLayoutManager linearLayoutManager;

    private TextView my_rank;
//    private RankAdapter rankAdapter = new RankAdapter();

//    ArrayAdapter adapter;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RankFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RankFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RankFragment newInstance(String param1, String param2) {
        RankFragment fragment = new RankFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View view = inflater.inflate(R.layout.fragment_rank, container, false);
        my_rank = view.findViewById(R.id.my_rank);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Level Us");

//        adapter = new ArrayAdapter<String>(getActivity(), R.layout.fragment_rank, R.id.rank_list, rankList);
        rankRecyclerView = view.findViewById(R.id.rank_list);
        rankRecyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(getContext());
        rankRecyclerView.setLayoutManager(linearLayoutManager);
        rankList = new ArrayList<>();
        rankAdapter = new RankAdapter(rankList);
        rankRecyclerView.setAdapter(rankAdapter);
        databaseReference.child("UserAccount").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
                if(snapshot.exists()){  //result.next()랑 비슷한 개념인듯?
                    UserAccount userAccount = snapshot.getValue(UserAccount.class);
                    rankList.add(userAccount);
                    rankRecyclerView.scrollToPosition(rankList.size()-1);
                    rankAdapter.notifyItemInserted(rankList.size()-1);
                }
                Collections.sort(rankList);
                rankAdapter.notifyDataSetChanged();

                for(int i=0;i< rankList.size();i++) {
                    UserAccount userAccount = rankList.get(i);
                    Log.i(i + "번째 사람의 레벨 ", Integer.toString(userAccount.getLevel()));
                    if (userAccount.getIdToken().equals(firebaseUser.getUid())) {
//                            userAccount.setRank(i+1);
                        Log.i("현재 사용자 등수",Integer.toString(i+1));
                        my_rank.setText(Integer.toString(i+1));
//                            isCurUser = true;
                    }
                }
            }


            @Override
            public void onChildChanged(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
                Collections.sort(rankList);
                rankAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull @NotNull DataSnapshot snapshot) {
                Collections.sort(rankList);
                rankAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
                Collections.sort(rankList);
                rankAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
        return view;
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