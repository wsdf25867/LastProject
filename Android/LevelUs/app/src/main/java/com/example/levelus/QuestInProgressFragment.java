package com.example.levelus;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


public class QuestInProgressFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public QuestInProgressFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static QuestInProgressFragment newInstance(String param1, String param2) {
        QuestInProgressFragment fragment = new QuestInProgressFragment();
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
        View view = inflater.inflate(R.layout.fragment_quest_in_progress, container, false);
        Button capture_image_btn = view.findViewById(R.id.capture_image_btn);   //Text인식
//        capture_image_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent GoToTextRecognitionActivity = new Intent(getActivity(), TextRecognitionActivity.class);
//                startActivity(GoToTextRecognitionActivity);
//            }
//        });

        Button capture_image_btn2 = view.findViewById(R.id.capture_image_btn2);     //객체 인식
        capture_image_btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent GoToImageLabellingActivity = new Intent(getActivity(), ImageLabellingActivity.class);
                startActivity(GoToImageLabellingActivity);
            }
        });
        // Inflate the layout for this fragment
        return view;
    }
}