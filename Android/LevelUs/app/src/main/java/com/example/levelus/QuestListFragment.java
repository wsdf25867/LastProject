package com.example.levelus;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class QuestListFragment extends Fragment implements LoggedPages.onKeyBackPressedListener {

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference questDataReference = firebaseDatabase.getReference("quest");
    private FirebaseStorage storage = FirebaseStorage.getInstance("gs://collabtest-71a4d.appspot.com");
    private StorageReference storageRef = storage.getReference();

    private ArrayList<QuestInfo> rData = new ArrayList<>();

    QuestListAdapter qAdapter;

    ImageButton category_diy,category_entertainment,category_food,category_health,category_hiking,category_life_milestone,
            category_love,category_nature,category_new_skill,category_outdoor,category_sports,category_travel;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

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
        View view = inflater.inflate(R.layout.fragment_quest_list_grid, container, false);
        RecyclerView recyclerView_diy = (RecyclerView) view.findViewById(R.id.recycleView_diy);
        RecyclerView recyclerView_entertainment = (RecyclerView) view.findViewById(R.id.recycleView_entertainment);
        RecyclerView recyclerView_food = (RecyclerView) view.findViewById(R.id.recycleView_food);
        RecyclerView recyclerView_health = (RecyclerView) view.findViewById(R.id.recycleView_health);
        RecyclerView recyclerView_hiking = (RecyclerView) view.findViewById(R.id.recycleView_hiking);
        RecyclerView recyclerView_life_milestone = (RecyclerView) view.findViewById(R.id.recycleView_life_milestone);
        RecyclerView recyclerView_love = (RecyclerView) view.findViewById(R.id.recycleView_love);
        RecyclerView recyclerView_nature = (RecyclerView) view.findViewById(R.id.recycleView_nature);
        RecyclerView recyclerView_new_skill = (RecyclerView) view.findViewById(R.id.recycleView_new_skill);
        RecyclerView recyclerView_outdoor = (RecyclerView) view.findViewById(R.id.recycleView_outdoor);
        RecyclerView recyclerView_sports = (RecyclerView) view.findViewById(R.id.recycleView_sports);
        RecyclerView recyclerView_travel = (RecyclerView) view.findViewById(R.id.recycleView_travel);

        category_diy = view.findViewById(R.id.category1);
        category_entertainment = view.findViewById(R.id.category2);
        category_food = view.findViewById(R.id.category3);
        category_health = view.findViewById(R.id.category4);
        category_hiking = view.findViewById(R.id.category5);
        category_life_milestone = view.findViewById(R.id.category6);
        category_love = view.findViewById(R.id.category7);
        category_nature = view.findViewById(R.id.category8);
        category_new_skill = view.findViewById(R.id.category9);
        category_outdoor = view.findViewById(R.id.category10);
        category_sports = view.findViewById(R.id.category11);
        category_travel = view.findViewById(R.id.category12);

        recyclerView_diy.setVisibility(View.INVISIBLE);
        recyclerView_entertainment.setVisibility(View.INVISIBLE);
        recyclerView_food.setVisibility(View.INVISIBLE);
        recyclerView_health.setVisibility(View.INVISIBLE);
        recyclerView_hiking.setVisibility(View.INVISIBLE);
        recyclerView_life_milestone.setVisibility(View.INVISIBLE);
        recyclerView_love.setVisibility(View.INVISIBLE);
        recyclerView_nature.setVisibility(View.INVISIBLE);
        recyclerView_new_skill.setVisibility(View.INVISIBLE);
        recyclerView_outdoor.setVisibility(View.INVISIBLE);
        recyclerView_sports.setVisibility(View.INVISIBLE);
        recyclerView_travel.setVisibility(View.INVISIBLE);

        category_diy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getQuestListData("diy");

                GridLayoutManager gridlayoutManager;
                gridlayoutManager = new GridLayoutManager(getActivity(), 6);
                gridlayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                    @Override
                    public int getSpanSize(int position) {
                        return 3;
                    }
                });

                recyclerView_diy.setLayoutManager(gridlayoutManager);
                recyclerView_diy.setItemAnimator(new DefaultItemAnimator());
                recyclerView_diy.setAdapter(qAdapter);

                category_diy.setBackgroundColor(Color.parseColor("#64E7AE"));
                category_entertainment.setBackgroundColor(Color.parseColor("#00000000"));
                category_food.setBackgroundColor(Color.parseColor("#00000000"));
                category_health.setBackgroundColor(Color.parseColor("#00000000"));
                category_hiking.setBackgroundColor(Color.parseColor("#00000000"));
                category_life_milestone.setBackgroundColor(Color.parseColor("#00000000"));
                category_love.setBackgroundColor(Color.parseColor("#00000000"));
                category_nature.setBackgroundColor(Color.parseColor("#00000000"));
                category_new_skill.setBackgroundColor(Color.parseColor("#00000000"));
                category_outdoor.setBackgroundColor(Color.parseColor("#00000000"));
                category_sports.setBackgroundColor(Color.parseColor("#00000000"));
                category_travel.setBackgroundColor(Color.parseColor("#00000000"));

                recyclerView_diy.setVisibility(View.VISIBLE);
                recyclerView_entertainment.setVisibility(View.INVISIBLE);
                recyclerView_food.setVisibility(View.INVISIBLE);
                recyclerView_health.setVisibility(View.INVISIBLE);
                recyclerView_hiking.setVisibility(View.INVISIBLE);
                recyclerView_life_milestone.setVisibility(View.INVISIBLE);
                recyclerView_love.setVisibility(View.INVISIBLE);
                recyclerView_nature.setVisibility(View.INVISIBLE);
                recyclerView_new_skill.setVisibility(View.INVISIBLE);
                recyclerView_outdoor.setVisibility(View.INVISIBLE);
                recyclerView_sports.setVisibility(View.INVISIBLE);
                recyclerView_travel.setVisibility(View.INVISIBLE);
            }
        });

        category_entertainment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getQuestListData("entertainment");

                GridLayoutManager gridlayoutManager;
                gridlayoutManager = new GridLayoutManager(getActivity(), 6);
                gridlayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                    @Override
                    public int getSpanSize(int position) {
                        return 3;
                    }
                });

                recyclerView_entertainment.setLayoutManager(gridlayoutManager);
                recyclerView_entertainment.setItemAnimator(new DefaultItemAnimator());
                recyclerView_entertainment.setAdapter(qAdapter);

                category_diy.setBackgroundColor(Color.parseColor("#00000000"));
                category_entertainment.setBackgroundColor(Color.parseColor("#64E7AE"));
                category_food.setBackgroundColor(Color.parseColor("#00000000"));
                category_health.setBackgroundColor(Color.parseColor("#00000000"));
                category_hiking.setBackgroundColor(Color.parseColor("#00000000"));
                category_life_milestone.setBackgroundColor(Color.parseColor("#00000000"));
                category_love.setBackgroundColor(Color.parseColor("#00000000"));
                category_nature.setBackgroundColor(Color.parseColor("#00000000"));
                category_new_skill.setBackgroundColor(Color.parseColor("#00000000"));
                category_outdoor.setBackgroundColor(Color.parseColor("#00000000"));
                category_sports.setBackgroundColor(Color.parseColor("#00000000"));
                category_travel.setBackgroundColor(Color.parseColor("#00000000"));

                recyclerView_diy.setVisibility(View.INVISIBLE);
                recyclerView_entertainment.setVisibility(View.VISIBLE);
                recyclerView_food.setVisibility(View.INVISIBLE);
                recyclerView_health.setVisibility(View.INVISIBLE);
                recyclerView_hiking.setVisibility(View.INVISIBLE);
                recyclerView_life_milestone.setVisibility(View.INVISIBLE);
                recyclerView_love.setVisibility(View.INVISIBLE);
                recyclerView_nature.setVisibility(View.INVISIBLE);
                recyclerView_new_skill.setVisibility(View.INVISIBLE);
                recyclerView_outdoor.setVisibility(View.INVISIBLE);
                recyclerView_sports.setVisibility(View.INVISIBLE);
                recyclerView_travel.setVisibility(View.INVISIBLE);
            }
        });

        category_food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getQuestListData("food");

                GridLayoutManager gridlayoutManager;
                gridlayoutManager = new GridLayoutManager(getActivity(), 6);
                gridlayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                    @Override
                    public int getSpanSize(int position) {
                        return 3;
                    }
                });

                recyclerView_food.setLayoutManager(gridlayoutManager);
                recyclerView_food.setItemAnimator(new DefaultItemAnimator());
                recyclerView_food.setAdapter(qAdapter);

                category_diy.setBackgroundColor(Color.parseColor("#00000000"));
                category_entertainment.setBackgroundColor(Color.parseColor("#00000000"));
                category_food.setBackgroundColor(Color.parseColor("#64E7AE"));
                category_health.setBackgroundColor(Color.parseColor("#00000000"));
                category_hiking.setBackgroundColor(Color.parseColor("#00000000"));
                category_life_milestone.setBackgroundColor(Color.parseColor("#00000000"));
                category_love.setBackgroundColor(Color.parseColor("#00000000"));
                category_nature.setBackgroundColor(Color.parseColor("#00000000"));
                category_new_skill.setBackgroundColor(Color.parseColor("#00000000"));
                category_outdoor.setBackgroundColor(Color.parseColor("#00000000"));
                category_sports.setBackgroundColor(Color.parseColor("#00000000"));
                category_travel.setBackgroundColor(Color.parseColor("#00000000"));

                recyclerView_diy.setVisibility(View.INVISIBLE);
                recyclerView_entertainment.setVisibility(View.INVISIBLE);
                recyclerView_food.setVisibility(View.VISIBLE);
                recyclerView_health.setVisibility(View.INVISIBLE);
                recyclerView_hiking.setVisibility(View.INVISIBLE);
                recyclerView_life_milestone.setVisibility(View.INVISIBLE);
                recyclerView_love.setVisibility(View.INVISIBLE);
                recyclerView_nature.setVisibility(View.INVISIBLE);
                recyclerView_new_skill.setVisibility(View.INVISIBLE);
                recyclerView_outdoor.setVisibility(View.INVISIBLE);
                recyclerView_sports.setVisibility(View.INVISIBLE);
                recyclerView_travel.setVisibility(View.INVISIBLE);
            }
        });

        category_health.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getQuestListData("health");

                GridLayoutManager gridlayoutManager;
                gridlayoutManager = new GridLayoutManager(getActivity(), 6);
                gridlayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                    @Override
                    public int getSpanSize(int position) {
                        return 3;
                    }
                });

                recyclerView_health.setLayoutManager(gridlayoutManager);
                recyclerView_health.setItemAnimator(new DefaultItemAnimator());
                recyclerView_health.setAdapter(qAdapter);

                category_diy.setBackgroundColor(Color.parseColor("#00000000"));
                category_entertainment.setBackgroundColor(Color.parseColor("#00000000"));
                category_food.setBackgroundColor(Color.parseColor("#00000000"));
                category_health.setBackgroundColor(Color.parseColor("#64E7AE"));
                category_hiking.setBackgroundColor(Color.parseColor("#00000000"));
                category_life_milestone.setBackgroundColor(Color.parseColor("#00000000"));
                category_love.setBackgroundColor(Color.parseColor("#00000000"));
                category_nature.setBackgroundColor(Color.parseColor("#00000000"));
                category_new_skill.setBackgroundColor(Color.parseColor("#00000000"));
                category_outdoor.setBackgroundColor(Color.parseColor("#00000000"));
                category_sports.setBackgroundColor(Color.parseColor("#00000000"));
                category_travel.setBackgroundColor(Color.parseColor("#00000000"));

                recyclerView_diy.setVisibility(View.INVISIBLE);
                recyclerView_entertainment.setVisibility(View.INVISIBLE);
                recyclerView_food.setVisibility(View.INVISIBLE);
                recyclerView_health.setVisibility(View.VISIBLE);
                recyclerView_hiking.setVisibility(View.INVISIBLE);
                recyclerView_life_milestone.setVisibility(View.INVISIBLE);
                recyclerView_love.setVisibility(View.INVISIBLE);
                recyclerView_nature.setVisibility(View.INVISIBLE);
                recyclerView_new_skill.setVisibility(View.INVISIBLE);
                recyclerView_outdoor.setVisibility(View.INVISIBLE);
                recyclerView_sports.setVisibility(View.INVISIBLE);
                recyclerView_travel.setVisibility(View.INVISIBLE);
            }
        });

        category_hiking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getQuestListData("hiking");

                GridLayoutManager gridlayoutManager;
                gridlayoutManager = new GridLayoutManager(getActivity(), 6);
                gridlayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                    @Override
                    public int getSpanSize(int position) {
                        return 3;
                    }
                });

                recyclerView_hiking.setLayoutManager(gridlayoutManager);
                recyclerView_hiking.setItemAnimator(new DefaultItemAnimator());
                recyclerView_hiking.setAdapter(qAdapter);

                category_diy.setBackgroundColor(Color.parseColor("#00000000"));
                category_entertainment.setBackgroundColor(Color.parseColor("#00000000"));
                category_food.setBackgroundColor(Color.parseColor("#00000000"));
                category_health.setBackgroundColor(Color.parseColor("#00000000"));
                category_hiking.setBackgroundColor(Color.parseColor("#64E7AE"));
                category_life_milestone.setBackgroundColor(Color.parseColor("#00000000"));
                category_love.setBackgroundColor(Color.parseColor("#00000000"));
                category_nature.setBackgroundColor(Color.parseColor("#00000000"));
                category_new_skill.setBackgroundColor(Color.parseColor("#00000000"));
                category_outdoor.setBackgroundColor(Color.parseColor("#00000000"));
                category_sports.setBackgroundColor(Color.parseColor("#00000000"));
                category_travel.setBackgroundColor(Color.parseColor("#00000000"));

                recyclerView_diy.setVisibility(View.INVISIBLE);
                recyclerView_entertainment.setVisibility(View.INVISIBLE);
                recyclerView_food.setVisibility(View.INVISIBLE);
                recyclerView_health.setVisibility(View.INVISIBLE);
                recyclerView_hiking.setVisibility(View.VISIBLE);
                recyclerView_life_milestone.setVisibility(View.INVISIBLE);
                recyclerView_love.setVisibility(View.INVISIBLE);
                recyclerView_nature.setVisibility(View.INVISIBLE);
                recyclerView_new_skill.setVisibility(View.INVISIBLE);
                recyclerView_outdoor.setVisibility(View.INVISIBLE);
                recyclerView_sports.setVisibility(View.INVISIBLE);
                recyclerView_travel.setVisibility(View.INVISIBLE);
            }
        });

        category_life_milestone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getQuestListData("life_milestone");

                GridLayoutManager gridlayoutManager;
                gridlayoutManager = new GridLayoutManager(getActivity(), 6);
                gridlayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                    @Override
                    public int getSpanSize(int position) {
                        return 3;
                    }
                });

                recyclerView_life_milestone.setLayoutManager(gridlayoutManager);
                recyclerView_life_milestone.setItemAnimator(new DefaultItemAnimator());
                recyclerView_life_milestone.setAdapter(qAdapter);

                category_diy.setBackgroundColor(Color.parseColor("#00000000"));
                category_entertainment.setBackgroundColor(Color.parseColor("#00000000"));
                category_food.setBackgroundColor(Color.parseColor("#00000000"));
                category_health.setBackgroundColor(Color.parseColor("#00000000"));
                category_hiking.setBackgroundColor(Color.parseColor("#00000000"));
                category_life_milestone.setBackgroundColor(Color.parseColor("#64E7AE"));
                category_love.setBackgroundColor(Color.parseColor("#00000000"));
                category_nature.setBackgroundColor(Color.parseColor("#00000000"));
                category_new_skill.setBackgroundColor(Color.parseColor("#00000000"));
                category_outdoor.setBackgroundColor(Color.parseColor("#00000000"));
                category_sports.setBackgroundColor(Color.parseColor("#00000000"));
                category_travel.setBackgroundColor(Color.parseColor("#00000000"));

                recyclerView_diy.setVisibility(View.INVISIBLE);
                recyclerView_entertainment.setVisibility(View.INVISIBLE);
                recyclerView_food.setVisibility(View.INVISIBLE);
                recyclerView_health.setVisibility(View.INVISIBLE);
                recyclerView_hiking.setVisibility(View.INVISIBLE);
                recyclerView_life_milestone.setVisibility(View.VISIBLE);
                recyclerView_love.setVisibility(View.INVISIBLE);
                recyclerView_nature.setVisibility(View.INVISIBLE);
                recyclerView_new_skill.setVisibility(View.INVISIBLE);
                recyclerView_outdoor.setVisibility(View.INVISIBLE);
                recyclerView_sports.setVisibility(View.INVISIBLE);
                recyclerView_travel.setVisibility(View.INVISIBLE);
            }
        });
        category_love.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getQuestListData("love");

                GridLayoutManager gridlayoutManager;
                gridlayoutManager = new GridLayoutManager(getActivity(), 6);
                gridlayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                    @Override
                    public int getSpanSize(int position) {
                        return 3;
                    }
                });

                recyclerView_love.setLayoutManager(gridlayoutManager);
                recyclerView_love.setItemAnimator(new DefaultItemAnimator());
                recyclerView_love.setAdapter(qAdapter);

                category_diy.setBackgroundColor(Color.parseColor("#00000000"));
                category_entertainment.setBackgroundColor(Color.parseColor("#00000000"));
                category_food.setBackgroundColor(Color.parseColor("#00000000"));
                category_health.setBackgroundColor(Color.parseColor("#00000000"));
                category_hiking.setBackgroundColor(Color.parseColor("#00000000"));
                category_life_milestone.setBackgroundColor(Color.parseColor("#00000000"));
                category_love.setBackgroundColor(Color.parseColor("#64E7AE"));
                category_nature.setBackgroundColor(Color.parseColor("#00000000"));
                category_new_skill.setBackgroundColor(Color.parseColor("#00000000"));
                category_outdoor.setBackgroundColor(Color.parseColor("#00000000"));
                category_sports.setBackgroundColor(Color.parseColor("#00000000"));
                category_travel.setBackgroundColor(Color.parseColor("#00000000"));

                recyclerView_diy.setVisibility(View.INVISIBLE);
                recyclerView_entertainment.setVisibility(View.INVISIBLE);
                recyclerView_food.setVisibility(View.INVISIBLE);
                recyclerView_health.setVisibility(View.INVISIBLE);
                recyclerView_hiking.setVisibility(View.INVISIBLE);
                recyclerView_life_milestone.setVisibility(View.VISIBLE);
                recyclerView_love.setVisibility(View.INVISIBLE);
                recyclerView_nature.setVisibility(View.INVISIBLE);
                recyclerView_new_skill.setVisibility(View.INVISIBLE);
                recyclerView_outdoor.setVisibility(View.INVISIBLE);
                recyclerView_sports.setVisibility(View.INVISIBLE);
                recyclerView_travel.setVisibility(View.INVISIBLE);
            }
        });

        category_nature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getQuestListData("nature");

                GridLayoutManager gridlayoutManager;
                gridlayoutManager = new GridLayoutManager(getActivity(), 6);
                gridlayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                    @Override
                    public int getSpanSize(int position) {
                        return 3;
                    }
                });

                recyclerView_nature.setLayoutManager(gridlayoutManager);
                recyclerView_nature.setItemAnimator(new DefaultItemAnimator());
                recyclerView_nature.setAdapter(qAdapter);

                category_diy.setBackgroundColor(Color.parseColor("#00000000"));
                category_entertainment.setBackgroundColor(Color.parseColor("#00000000"));
                category_food.setBackgroundColor(Color.parseColor("#00000000"));
                category_health.setBackgroundColor(Color.parseColor("#00000000"));
                category_hiking.setBackgroundColor(Color.parseColor("#00000000"));
                category_life_milestone.setBackgroundColor(Color.parseColor("#00000000"));
                category_love.setBackgroundColor(Color.parseColor("#00000000"));
                category_nature.setBackgroundColor(Color.parseColor("#64E7AE"));
                category_new_skill.setBackgroundColor(Color.parseColor("#00000000"));
                category_outdoor.setBackgroundColor(Color.parseColor("#00000000"));
                category_sports.setBackgroundColor(Color.parseColor("#00000000"));
                category_travel.setBackgroundColor(Color.parseColor("#00000000"));

                recyclerView_diy.setVisibility(View.INVISIBLE);
                recyclerView_entertainment.setVisibility(View.INVISIBLE);
                recyclerView_food.setVisibility(View.INVISIBLE);
                recyclerView_health.setVisibility(View.INVISIBLE);
                recyclerView_hiking.setVisibility(View.INVISIBLE);
                recyclerView_life_milestone.setVisibility(View.INVISIBLE);
                recyclerView_love.setVisibility(View.INVISIBLE);
                recyclerView_nature.setVisibility(View.VISIBLE);
                recyclerView_new_skill.setVisibility(View.INVISIBLE);
                recyclerView_outdoor.setVisibility(View.INVISIBLE);
                recyclerView_sports.setVisibility(View.INVISIBLE);
                recyclerView_travel.setVisibility(View.INVISIBLE);
            }
        });

        category_new_skill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getQuestListData("new_skill");

                GridLayoutManager gridlayoutManager;
                gridlayoutManager = new GridLayoutManager(getActivity(), 6);
                gridlayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                    @Override
                    public int getSpanSize(int position) {
                        return 3;
                    }
                });

                recyclerView_new_skill.setLayoutManager(gridlayoutManager);
                recyclerView_new_skill.setItemAnimator(new DefaultItemAnimator());
                recyclerView_new_skill.setAdapter(qAdapter);

                category_diy.setBackgroundColor(Color.parseColor("#00000000"));
                category_entertainment.setBackgroundColor(Color.parseColor("#00000000"));
                category_food.setBackgroundColor(Color.parseColor("#00000000"));
                category_health.setBackgroundColor(Color.parseColor("#00000000"));
                category_hiking.setBackgroundColor(Color.parseColor("#00000000"));
                category_life_milestone.setBackgroundColor(Color.parseColor("#00000000"));
                category_love.setBackgroundColor(Color.parseColor("#00000000"));
                category_nature.setBackgroundColor(Color.parseColor("#00000000"));
                category_new_skill.setBackgroundColor(Color.parseColor("#64E7AE"));
                category_outdoor.setBackgroundColor(Color.parseColor("#00000000"));
                category_sports.setBackgroundColor(Color.parseColor("#00000000"));
                category_travel.setBackgroundColor(Color.parseColor("#00000000"));

                recyclerView_diy.setVisibility(View.INVISIBLE);
                recyclerView_entertainment.setVisibility(View.INVISIBLE);
                recyclerView_food.setVisibility(View.INVISIBLE);
                recyclerView_health.setVisibility(View.INVISIBLE);
                recyclerView_hiking.setVisibility(View.INVISIBLE);
                recyclerView_life_milestone.setVisibility(View.INVISIBLE);
                recyclerView_love.setVisibility(View.INVISIBLE);
                recyclerView_nature.setVisibility(View.INVISIBLE);
                recyclerView_new_skill.setVisibility(View.VISIBLE);
                recyclerView_outdoor.setVisibility(View.INVISIBLE);
                recyclerView_sports.setVisibility(View.INVISIBLE);
                recyclerView_travel.setVisibility(View.INVISIBLE);
            }
        });

        category_outdoor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getQuestListData("outdoor");

                GridLayoutManager gridlayoutManager;
                gridlayoutManager = new GridLayoutManager(getActivity(), 6);
                gridlayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                    @Override
                    public int getSpanSize(int position) {
                        return 3;
                    }
                });

                recyclerView_outdoor.setLayoutManager(gridlayoutManager);
                recyclerView_outdoor.setItemAnimator(new DefaultItemAnimator());
                recyclerView_outdoor.setAdapter(qAdapter);

                category_diy.setBackgroundColor(Color.parseColor("#00000000"));
                category_entertainment.setBackgroundColor(Color.parseColor("#00000000"));
                category_food.setBackgroundColor(Color.parseColor("#00000000"));
                category_health.setBackgroundColor(Color.parseColor("#00000000"));
                category_hiking.setBackgroundColor(Color.parseColor("#00000000"));
                category_life_milestone.setBackgroundColor(Color.parseColor("#00000000"));
                category_love.setBackgroundColor(Color.parseColor("#00000000"));
                category_nature.setBackgroundColor(Color.parseColor("#00000000"));
                category_new_skill.setBackgroundColor(Color.parseColor("#00000000"));
                category_outdoor.setBackgroundColor(Color.parseColor("#64E7AE"));
                category_sports.setBackgroundColor(Color.parseColor("#00000000"));
                category_travel.setBackgroundColor(Color.parseColor("#00000000"));

                recyclerView_diy.setVisibility(View.INVISIBLE);
                recyclerView_entertainment.setVisibility(View.INVISIBLE);
                recyclerView_food.setVisibility(View.INVISIBLE);
                recyclerView_health.setVisibility(View.INVISIBLE);
                recyclerView_hiking.setVisibility(View.INVISIBLE);
                recyclerView_life_milestone.setVisibility(View.INVISIBLE);
                recyclerView_love.setVisibility(View.INVISIBLE);
                recyclerView_nature.setVisibility(View.INVISIBLE);
                recyclerView_new_skill.setVisibility(View.INVISIBLE);
                recyclerView_outdoor.setVisibility(View.VISIBLE);
                recyclerView_sports.setVisibility(View.INVISIBLE);
                recyclerView_travel.setVisibility(View.INVISIBLE);
            }
        });

        category_sports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getQuestListData("sports");

                GridLayoutManager gridlayoutManager;
                gridlayoutManager = new GridLayoutManager(getActivity(), 6);
                gridlayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                    @Override
                    public int getSpanSize(int position) {
                        return 3;
                    }
                });

                recyclerView_sports.setLayoutManager(gridlayoutManager);
                recyclerView_sports.setItemAnimator(new DefaultItemAnimator());
                recyclerView_sports.setAdapter(qAdapter);

                category_diy.setBackgroundColor(Color.parseColor("#00000000"));
                category_entertainment.setBackgroundColor(Color.parseColor("#00000000"));
                category_food.setBackgroundColor(Color.parseColor("#00000000"));
                category_health.setBackgroundColor(Color.parseColor("#00000000"));
                category_hiking.setBackgroundColor(Color.parseColor("#00000000"));
                category_life_milestone.setBackgroundColor(Color.parseColor("#00000000"));
                category_love.setBackgroundColor(Color.parseColor("#00000000"));
                category_nature.setBackgroundColor(Color.parseColor("#00000000"));
                category_new_skill.setBackgroundColor(Color.parseColor("#00000000"));
                category_outdoor.setBackgroundColor(Color.parseColor("#00000000"));
                category_sports.setBackgroundColor(Color.parseColor("#64E7AE"));
                category_travel.setBackgroundColor(Color.parseColor("#00000000"));

                recyclerView_diy.setVisibility(View.INVISIBLE);
                recyclerView_entertainment.setVisibility(View.INVISIBLE);
                recyclerView_food.setVisibility(View.INVISIBLE);
                recyclerView_health.setVisibility(View.INVISIBLE);
                recyclerView_hiking.setVisibility(View.INVISIBLE);
                recyclerView_life_milestone.setVisibility(View.INVISIBLE);
                recyclerView_love.setVisibility(View.INVISIBLE);
                recyclerView_nature.setVisibility(View.INVISIBLE);
                recyclerView_new_skill.setVisibility(View.INVISIBLE);
                recyclerView_outdoor.setVisibility(View.INVISIBLE);
                recyclerView_sports.setVisibility(View.VISIBLE);
                recyclerView_travel.setVisibility(View.INVISIBLE);
            }
        });

        category_travel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getQuestListData("travel");

                GridLayoutManager gridlayoutManager;
                gridlayoutManager = new GridLayoutManager(getActivity(), 6);
                gridlayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                    @Override
                    public int getSpanSize(int position) {
                        return 3;
                    }
                });

                recyclerView_travel.setLayoutManager(gridlayoutManager);
                recyclerView_travel.setItemAnimator(new DefaultItemAnimator());
                recyclerView_travel.setAdapter(qAdapter);

                category_diy.setBackgroundColor(Color.parseColor("#00000000"));
                category_entertainment.setBackgroundColor(Color.parseColor("#00000000"));
                category_food.setBackgroundColor(Color.parseColor("#00000000"));
                category_health.setBackgroundColor(Color.parseColor("#00000000"));
                category_hiking.setBackgroundColor(Color.parseColor("#00000000"));
                category_life_milestone.setBackgroundColor(Color.parseColor("#00000000"));
                category_love.setBackgroundColor(Color.parseColor("#00000000"));
                category_nature.setBackgroundColor(Color.parseColor("#00000000"));
                category_new_skill.setBackgroundColor(Color.parseColor("#00000000"));
                category_outdoor.setBackgroundColor(Color.parseColor("#00000000"));
                category_sports.setBackgroundColor(Color.parseColor("#00000000"));
                category_travel.setBackgroundColor(Color.parseColor("#64E7AE"));

                recyclerView_diy.setVisibility(View.INVISIBLE);
                recyclerView_entertainment.setVisibility(View.INVISIBLE);
                recyclerView_food.setVisibility(View.INVISIBLE);
                recyclerView_health.setVisibility(View.INVISIBLE);
                recyclerView_hiking.setVisibility(View.INVISIBLE);
                recyclerView_life_milestone.setVisibility(View.INVISIBLE);
                recyclerView_love.setVisibility(View.INVISIBLE);
                recyclerView_nature.setVisibility(View.INVISIBLE);
                recyclerView_new_skill.setVisibility(View.INVISIBLE);
                recyclerView_outdoor.setVisibility(View.INVISIBLE);
                recyclerView_sports.setVisibility(View.INVISIBLE);
                recyclerView_travel.setVisibility(View.VISIBLE);
            }
        });
        return view;
    }

    public void getQuestListData(String category) {
        questDataReference.child(category).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                rData.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    QuestInfo questInfo = dataSnapshot.getValue(QuestInfo.class);
                    rData.add(questInfo);

                }
                qAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }

        });
        qAdapter = new QuestListAdapter(rData);
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
