package com.example.levelus;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link QuestListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QuestListFragment extends Fragment {

    private DatabaseReference mDatabaseRef;

    private FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();
    private FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();
//    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
//    DatabaseReference allDatabaseRef = firebaseDatabase.getReferenceFromUrl("gs://collabtest-71a4d.appspot.com");

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    static int questNum = 0;

    public QuestListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment QuestListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static QuestListFragment newInstance(String param1, String param2) {
        QuestListFragment fragment = new QuestListFragment();
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
        View view = inflater.inflate(R.layout.fragment_quest_list, container, false);
        // Inflate the layout for this fragment
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("recommend_list");

        Button nextQuest = view.findViewById(R.id.nextQuest);

        TextView added = view.findViewById(R.id.added);
        TextView category = view.findViewById(R.id.category);
        TextView done = view.findViewById(R.id.done);
        TextView keyword = view.findViewById(R.id.keyword);
        TextView quest_num = view.findViewById(R.id.quest_num);
        TextView title = view.findViewById(R.id.title);
        TextView title_ko = view.findViewById(R.id.title_ko);
        TextView way = view.findViewById(R.id.way);


        QuestInfo[] questInfo = new QuestInfo[10];
        nextQuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mDatabaseRef.child(firebaseUser.getUid()).child(Integer.toString(questNum))!=null){
                    mDatabaseRef.child(firebaseUser.getUid()).child(Integer.toString(questNum)).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        QuestInfo questInfo = snapshot.getValue(QuestInfo.class);
//                        added.setText(questInfo.getAdded());
//                        category.setText(questInfo.getCategory());
//                        done.setText(questInfo.getDone());
//                        keyword.setText(questInfo.getKeyword());
//                        quest_num.setText(questInfo.getQuest_num());
//                        title.setText(questInfo.getTitle());
//                        title_ko.setText(questInfo.getTitle_ko());
//                        way.setText(questInfo.getWay());
//                        questNum++;
                            if(snapshot.exists()){
                                questInfo[questNum] = snapshot.getValue(QuestInfo.class);
                                added.setText(questInfo[questNum].getAdded());
                                category.setText(questInfo[questNum].getCategory());
                                done.setText(questInfo[questNum].getDone());
                                keyword.setText(questInfo[questNum].getKeyword());
                                quest_num.setText(questInfo[questNum].getQuest_num());
                                title.setText(questInfo[questNum].getTitle());
                                title_ko.setText(questInfo[questNum].getTitle_ko());
                                way.setText(questInfo[questNum].getWay());
                                questNum++;
                                if(questNum==questInfo.length)
                                    questNum = 0;
                            }
                            else
                                Toast.makeText(getActivity(), "No quest exists", Toast.LENGTH_SHORT).show();
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });

        return view;
    }
}