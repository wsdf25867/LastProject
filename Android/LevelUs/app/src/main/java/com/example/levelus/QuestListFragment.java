package com.example.levelus;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link QuestListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QuestListFragment extends Fragment {

    private DatabaseReference mDatabaseRef;
    private FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();
    private FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();

    private LinearLayout container;

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
        mDatabaseRef = FirebaseDatabase.getInstance().getReference();


//        TextView added = view.findViewById(R.id.added);
//        TextView category = view.findViewById(R.id.category);
//        TextView done = view.findViewById(R.id.done);
//        TextView keyword = view.findViewById(R.id.keyword);
//        TextView quest_num = view.findViewById(R.id.quest_num);
//        TextView title = view.findViewById(R.id.title);
        TextView title_ko = view.findViewById(R.id.title_ko);       //quest title
        TextView title_ko1 = view.findViewById(R.id.title_ko1);
        TextView title_ko2 = view.findViewById(R.id.title_ko2);
        TextView title_ko3 = view.findViewById(R.id.title_ko3);
        TextView title_ko4 = view.findViewById(R.id.title_ko4);
        TextView title_ko5 = view.findViewById(R.id.title_ko5);


//        TextView way = view.findViewById(R.id.way);

        TextView title = view.findViewById(R.id.title);                 //카테고리 이름
        ImageButton category1 = view.findViewById(R.id.category1);     //카테고리들 선택
        ImageButton category2 = view.findViewById(R.id.category2);
        ImageButton category3 = view.findViewById(R.id.category3);
        ImageButton category4 = view.findViewById(R.id.category4);
        ImageButton category5 = view.findViewById(R.id.category5);
        ImageButton category6 = view.findViewById(R.id.category6);
        ImageButton category7 = view.findViewById(R.id.category7);
        ImageButton category8 = view.findViewById(R.id.category8);
        ImageButton category9 = view.findViewById(R.id.category9);
        ImageButton category10 = view.findViewById(R.id.category10);
        ImageButton category11 = view.findViewById(R.id.category11);
        ImageButton category12 = view.findViewById(R.id.category12);




        QuestInfo[] questInfo = new QuestInfo[100];
        category1.setOnClickListener(new View.OnClickListener() {   //diy
            @Override
            public void onClick(View v) {
                mDatabaseRef.child("quest").child("diy").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        title.setText("diy");
                        for(int i = 0; i<(int)snapshot.getChildrenCount(); i++) {
                            questInfo[i] = snapshot.child(Integer.toString(i)).getValue(QuestInfo.class);
                            questNum++;
                        }
                        title_ko.setText(questInfo[0].getTitle_ko());      //얘만 한글버전
                        title_ko1.setText(questInfo[1].getTitle_ko());
                        title_ko2.setText(questInfo[2].getTitle_ko());
                        title_ko3.setText(questInfo[3].getTitle_ko());
                        title_ko4.setText(questInfo[4].getTitle_ko());
                        title_ko5.setText(questInfo[5].getTitle_ko());

                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }
                });
            }
        });

        category2.setOnClickListener(new View.OnClickListener() {   //entertainment
            @Override
            public void onClick(View v) {
                mDatabaseRef.child("quest").child("entertainment").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        title.setText("entertainment");
                        for(int i = 0; i<(int)snapshot.getChildrenCount(); i++) {
                            questInfo[i] = snapshot.child(Integer.toString(i)).getValue(QuestInfo.class);
                            questNum++;
                        }
                        title_ko.setText(questInfo[0].getTitle_ko());      //얘만 한글버전
                        title_ko1.setText(questInfo[1].getTitle_ko());
                        title_ko2.setText(questInfo[2].getTitle_ko());
                        title_ko3.setText(questInfo[3].getTitle_ko());
                        title_ko4.setText(questInfo[4].getTitle_ko());
                        title_ko5.setText(questInfo[5].getTitle_ko());

                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }
                });
            }
        });

        category3.setOnClickListener(new View.OnClickListener() {   //food
            @Override
            public void onClick(View v) {
                mDatabaseRef.child("quest").child("food").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        title.setText("food");
                        for(int i = 0; i<(int)snapshot.getChildrenCount(); i++) {
                            questInfo[i] = snapshot.child(Integer.toString(i)).getValue(QuestInfo.class);
                            questNum++;
                        }
                        title_ko.setText(questInfo[0].getTitle_ko());      //얘만 한글버전
                        title_ko1.setText(questInfo[1].getTitle_ko());
                        title_ko2.setText(questInfo[2].getTitle_ko());
                        title_ko3.setText(questInfo[3].getTitle_ko());
                        title_ko4.setText(questInfo[4].getTitle_ko());
                        title_ko5.setText(questInfo[5].getTitle_ko());

                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }
                });
            }
        });

        category4.setOnClickListener(new View.OnClickListener() {   //health
            @Override
            public void onClick(View v) {
                mDatabaseRef.child("quest").child("health").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        title.setText("health");
                        for(int i = 0; i<(int)snapshot.getChildrenCount(); i++) {
                            questInfo[i] = snapshot.child(Integer.toString(i)).getValue(QuestInfo.class);
                            questNum++;
                        }
                        title_ko.setText(questInfo[0].getTitle_ko());      //얘만 한글버전
                        title_ko1.setText(questInfo[1].getTitle_ko());
                        title_ko2.setText(questInfo[2].getTitle_ko());
                        title_ko3.setText(questInfo[3].getTitle_ko());
                        title_ko4.setText(questInfo[4].getTitle_ko());
                        title_ko5.setText(questInfo[5].getTitle_ko());

                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }
                });
            }
        });


        category5.setOnClickListener(new View.OnClickListener() {   //hiking
            @Override
            public void onClick(View v) {
                mDatabaseRef.child("quest").child("hiking").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        title.setText("hiking");
                        for(int i = 0; i<(int)snapshot.getChildrenCount(); i++) {
                            questInfo[i] = snapshot.child(Integer.toString(i)).getValue(QuestInfo.class);
                            questNum++;
                        }
                        title_ko.setText(questInfo[0].getTitle_ko());      //얘만 한글버전
                        title_ko1.setText(questInfo[1].getTitle_ko());
                        title_ko2.setText(questInfo[2].getTitle_ko());
                        title_ko3.setText(questInfo[3].getTitle_ko());
                        title_ko4.setText(questInfo[4].getTitle_ko());
                        title_ko5.setText(questInfo[5].getTitle_ko());

                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }
                });
            }
        });

        category6.setOnClickListener(new View.OnClickListener() {   //life_milestone
            @Override
            public void onClick(View v) {
                mDatabaseRef.child("quest").child("life_milestone").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        title.setText("life_milestone");
                        for(int i = 0; i<(int)snapshot.getChildrenCount(); i++) {
                            questInfo[i] = snapshot.child(Integer.toString(i)).getValue(QuestInfo.class);
                            questNum++;
                        }
                        title_ko.setText(questInfo[0].getTitle_ko());      //얘만 한글버전
                        title_ko1.setText(questInfo[1].getTitle_ko());
                        title_ko2.setText(questInfo[2].getTitle_ko());
                        title_ko3.setText(questInfo[3].getTitle_ko());
                        title_ko4.setText(questInfo[4].getTitle_ko());
                        title_ko5.setText(questInfo[5].getTitle_ko());

                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }
                });
            }
        });

        category7.setOnClickListener(new View.OnClickListener() {   //love
            @Override
            public void onClick(View v) {
                mDatabaseRef.child("quest").child("love").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        title.setText("love");
                        for(int i = 0; i<(int)snapshot.getChildrenCount(); i++) {
                            questInfo[i] = snapshot.child(Integer.toString(i)).getValue(QuestInfo.class);
                            questNum++;
                        }
                        title_ko.setText(questInfo[0].getTitle_ko());      //얘만 한글버전
                        title_ko1.setText(questInfo[1].getTitle_ko());
                        title_ko2.setText(questInfo[2].getTitle_ko());
                        title_ko3.setText(questInfo[3].getTitle_ko());
                        title_ko4.setText(questInfo[4].getTitle_ko());
                        title_ko5.setText(questInfo[5].getTitle_ko());

                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }
                });
            }
        });

        category8.setOnClickListener(new View.OnClickListener() {   //nature
            @Override
            public void onClick(View v) {
                mDatabaseRef.child("quest").child("nature").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        title.setText("nature");
                        for(int i = 0; i<(int)snapshot.getChildrenCount(); i++) {
                            questInfo[i] = snapshot.child(Integer.toString(i)).getValue(QuestInfo.class);
                            questNum++;
                        }
                        title_ko.setText(questInfo[0].getTitle_ko());      //얘만 한글버전
                        title_ko1.setText(questInfo[1].getTitle_ko());
                        title_ko2.setText(questInfo[2].getTitle_ko());
                        title_ko3.setText(questInfo[3].getTitle_ko());
                        title_ko4.setText(questInfo[4].getTitle_ko());
                        title_ko5.setText(questInfo[5].getTitle_ko());

                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }
                });
            }
        });

        category9.setOnClickListener(new View.OnClickListener() {   //new_skill
            @Override
            public void onClick(View v) {
                mDatabaseRef.child("quest").child("new_skill").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        title.setText("new_skill");
                        for(int i = 0; i<(int)snapshot.getChildrenCount(); i++) {
                            questInfo[i] = snapshot.child(Integer.toString(i)).getValue(QuestInfo.class);
                            questNum++;
                        }
                        title_ko.setText(questInfo[0].getTitle_ko());      //얘만 한글버전
                        title_ko1.setText(questInfo[1].getTitle_ko());
                        title_ko2.setText(questInfo[2].getTitle_ko());
                        title_ko3.setText(questInfo[3].getTitle_ko());
                        title_ko4.setText(questInfo[4].getTitle_ko());
                        title_ko5.setText(questInfo[5].getTitle_ko());

                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }
                });
            }
        });

        category10.setOnClickListener(new View.OnClickListener() {   //outdoor
            @Override
            public void onClick(View v) {
                mDatabaseRef.child("quest").child("outdoor").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        title.setText("outdoor");
                        for(int i = 0; i<(int)snapshot.getChildrenCount(); i++) {
                            questInfo[i] = snapshot.child(Integer.toString(i)).getValue(QuestInfo.class);
                            questNum++;
                        }
                        title_ko.setText(questInfo[0].getTitle_ko());      //얘만 한글버전
                        title_ko1.setText(questInfo[1].getTitle_ko());
                        title_ko2.setText(questInfo[2].getTitle_ko());
                        title_ko3.setText(questInfo[3].getTitle_ko());
                        title_ko4.setText(questInfo[4].getTitle_ko());
                        title_ko5.setText(questInfo[5].getTitle_ko());

                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }
                });
            }
        });

        category11.setOnClickListener(new View.OnClickListener() {   //sports
            @Override
            public void onClick(View v) {
                mDatabaseRef.child("quest").child("sports").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        title.setText("sports");
                        for(int i = 0; i<(int)snapshot.getChildrenCount(); i++) {
                            questInfo[i] = snapshot.child(Integer.toString(i)).getValue(QuestInfo.class);
                            questNum++;
                        }
                        title_ko.setText(questInfo[0].getTitle_ko());      //얘만 한글버전
                        title_ko1.setText(questInfo[1].getTitle_ko());
                        title_ko2.setText(questInfo[2].getTitle_ko());
                        title_ko3.setText(questInfo[3].getTitle_ko());
                        title_ko4.setText(questInfo[4].getTitle_ko());
                        title_ko5.setText(questInfo[5].getTitle_ko());

                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }
                });
            }
        });

        category12.setOnClickListener(new View.OnClickListener() {   //travel
            @Override
            public void onClick(View v) {
                mDatabaseRef.child("quest").child("travel").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        title.setText("travel");
                        for(int i = 0; i<(int)snapshot.getChildrenCount(); i++) {
                            questInfo[i] = snapshot.child(Integer.toString(i)).getValue(QuestInfo.class);
                            questNum++;
                        }
                        title_ko.setText(questInfo[0].getTitle_ko());      //얘만 한글버전
                        title_ko1.setText(questInfo[1].getTitle_ko());
                        title_ko2.setText(questInfo[2].getTitle_ko());
                        title_ko3.setText(questInfo[3].getTitle_ko());
                        title_ko4.setText(questInfo[4].getTitle_ko());
                        title_ko5.setText(questInfo[5].getTitle_ko());

                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }
                });
            }
        });


        return view;
    }
}
