package com.example.levelus;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.data.RadarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IRadarDataSet;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class AchievementActivity extends AppCompatActivity {

    //firebase
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference mDatabaseRef = firebaseDatabase.getReference("quest_log");
    private DatabaseReference qADatabaseRef = firebaseDatabase.getReference("quest");
    private FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();
    private FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();


    //xml
    private TextView back;
    private RadarChart chart;

    private int difficulty; // 퀘스트 난이도
    private int[] achievement_score; // 사용자 성취도

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievement);
        chart = findViewById(R.id.chart1);

        chart.setBackgroundColor(Color.rgb(255, 255, 255));
        chart.getDescription().setEnabled(false);
        chart.setWebLineWidth(1f);
        chart.setWebColor(Color.LTGRAY);
        chart.setWebLineWidthInner(1f);
        chart.setWebColorInner(Color.LTGRAY);
        chart.setWebAlpha(100);

        makeChart();

        // Back 버튼
        back = (TextView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent GoToLoggedPages = new Intent(getApplicationContext(), LoggedPages.class);
                startActivity(GoToLoggedPages);
                finish();
            }
        });
    }

    //차트생성
    private void makeChart(){
        RadarDataSet dataSet = new RadarDataSet(prepareData(), "내 성취도");
        dataSet.setColor(Color.parseColor("#64E7AE"));
        dataSet.setDrawFilled(true);
        dataSet.setFillColor(Color.parseColor("#64E7AE"));
        dataSet.setFillAlpha(50);
        dataSet.setLineWidth(2f);
        dataSet.setDrawHighlightCircleEnabled(true);


        RadarData data = new RadarData();
        data.addDataSet(dataSet);
        String[] labels =  {"성장", "여행", "체험", "도전", "즐김", "의지"};
        XAxis xAxis = chart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
        chart.setData(data);
    }


    //차트 데이터 생성
    public ArrayList<RadarEntry> prepareData() {
        String uid = firebaseUser.getUid();
        achievement_score = new int[6];
        ArrayList<RadarEntry> dataVals = new ArrayList<>();
        for(int i =0; i<= 222;i++){
            int quest_num = i;
            try{
                mDatabaseRef.child(uid).child(Integer.toString(quest_num)).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        QuestlogInfo questlogInfo = snapshot.getValue(QuestlogInfo.class);
                        try{
                            Log.d("dif",questlogInfo.getDifficulty());
                            Log.d("achieve",questlogInfo.getAchievement());
                            switch (questlogInfo.getAchievement()){
                                case "growth" : achievement_score[0] += Integer.parseInt(questlogInfo.getDifficulty()); achievement_score[5]++; break;
                                case "travel" : achievement_score[1] += Integer.parseInt(questlogInfo.getDifficulty()); achievement_score[5]++;break;
                                case "experience" : achievement_score[2] += Integer.parseInt(questlogInfo.getDifficulty()); achievement_score[5]++;break;
                                case "challenge" : achievement_score[3] += Integer.parseInt(questlogInfo.getDifficulty()); achievement_score[5]++;break;
                                case "enjoy" : achievement_score[4] += Integer.parseInt(questlogInfo.getDifficulty()); achievement_score[5]++;break;
                                default : break;
                            }
                            Log.d("growth0",Integer.toString(achievement_score[0]));
                            Log.d("growth1",Integer.toString(achievement_score[1]));
                            Log.d("growth2",Integer.toString(achievement_score[2]));
                            Log.d("growth3",Integer.toString(achievement_score[3]));
                            Log.d("growth4",Integer.toString(achievement_score[4]));
                            Log.d("growth5",Integer.toString(achievement_score[5]));
                        }catch(NullPointerException e){}

                    }
                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }
                });

            }catch(NullPointerException e){
                System.out.println("없음");
            }
                Log.d("growth0",Integer.toString(achievement_score[0]));
                Log.d("growth1",Integer.toString(achievement_score[1]));
                Log.d("growth2",Integer.toString(achievement_score[2]));
                Log.d("growth3",Integer.toString(achievement_score[3]));
                Log.d("growth4",Integer.toString(achievement_score[4]));
                Log.d("growth5",Integer.toString(achievement_score[5]));
                dataVals.add(new RadarEntry(achievement_score[0])); // 성장
                dataVals.add(new RadarEntry(achievement_score[1])); // 여행
                dataVals.add(new RadarEntry(achievement_score[2])); // 경험
                dataVals.add(new RadarEntry(achievement_score[3])); // 도전
                dataVals.add(new RadarEntry(achievement_score[4])); // 즐김
                dataVals.add(new RadarEntry(achievement_score[5])); // 의지
            }


        return  dataVals;
    }
}
