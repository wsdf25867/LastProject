package com.example.levelus;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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
import com.google.android.gms.tasks.OnSuccessListener;
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
    private FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();
    private FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();


    //xml
    private TextView back;
    private RadarChart chart;


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

    //차트 데이터 생성
    private ArrayList<RadarEntry> dataValue(){
        ArrayList<RadarEntry> dataVals = new ArrayList<>();
        dataVals.add(new RadarEntry(80)); // 의지력
        dataVals.add(new RadarEntry(60)); // 지력
        dataVals.add(new RadarEntry(75)); // 체력
        dataVals.add(new RadarEntry(40)); // 추진력
        dataVals.add(new RadarEntry(50)); // 매력
        return  dataVals;
    }

    //차트생성
    private void makeChart(){
        RadarDataSet dataSet = new RadarDataSet(dataValue(), "내 성취도");
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

//    public void prepareData() {
//        String uid = firebaseUser.getUid();
//        mDatabaseRef.child(uid).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
//                for(int i =0; i<= 222;i++){
//                    try{
//                        mDatabaseRef.child(uid).child(Integer.toString(i)).addValueEventListener(new ValueEventListener() {
//                            @Override
//                            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
//                                questThumbnail = null;
//                                QuestlogInfo questlogInfo = snapshot.getValue(QuestlogInfo.class);
//                                try{
//                                    if(Integer.parseInt(questlogInfo.getRating()) > 0){
//                                        storageRef.child("quest_thumbnail/" + questlogInfo.getQuest_num() + ".jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                                            @Override
//                                            public void onSuccess(Uri uri) {
//                                                Glide.with(getApplicationContext()).asBitmap().load(uri)
//                                                        .into(new SimpleTarget<Bitmap>() {
//                                                            @Override
//                                                            public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
//                                                                questThumbnail = new BitmapDrawable(getResources(),resource);
//                                                                //Drawable icon, String title_ko, String rating, String category, String accepted_date, String finished_date
//                                                                addItem(questThumbnail, questlogInfo.getTitle_ko(),questlogInfo.getRating(),questlogInfo.getCategory(),questlogInfo.getAccepted_date(),questlogInfo.getFinished_date());
//                                                                ((ListViewAdapter)listView.getAdapter()).getFilter().filter(" ") ;
//                                                            }
//                                                        });
//                                            }
//                                        });
//                                    }
//                                }catch(NullPointerException e){
//                                }
//                            }
//                            @Override
//                            public void onCancelled(@NonNull @NotNull DatabaseError error) {
//
//                            }
//                        });
//                    }catch(NullPointerException e){
//
//                    }
//                }
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull @NotNull DatabaseError error) {
//
//            }
//        });
//    }
}
