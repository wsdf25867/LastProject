package com.example.levelus;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;

import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.RadarChart;

import com.github.mikephil.charting.components.XAxis;

import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.data.RadarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.spans.DotSpan;

import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;

public class AchievementActivity extends AppCompatActivity {

    //firebase
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference mDatabaseRef = firebaseDatabase.getReference("quest_log");
    private FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();
    private FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();


    //xml
    private TextView back;
    private RadarChart chart;

    private MaterialCalendarView materialCalendarView;
    private TextView questTitle;
    private TextView questAchievement;

    private ArrayList<CalendarText> calendarinfo;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievement);

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

        // 원형 차트
        chart = findViewById(R.id.chart1);
        chart.setBackgroundColor(Color.rgb(255, 255, 255));
        chart.getDescription().setEnabled(false);
        chart.setWebLineWidth(1f);
        chart.setWebColor(Color.LTGRAY);
        chart.setWebLineWidthInner(1f);
        chart.setWebColorInner(Color.LTGRAY);
        chart.setWebAlpha(100);

        makeChart();

        Toast.makeText(this, "그래프를 터치하면 내 성취도가 나타납니다.", Toast.LENGTH_SHORT).show();
        //캘린더
        questTitle = findViewById(R.id.calendar_quest_title);
        questAchievement = findViewById(R.id.calendar_quest_achievement);
        materialCalendarView = findViewById(R.id.calendarView);
        materialCalendarView.setSelectedDate(CalendarDay.today());
        materialCalendarView.addDecorators(
                new SundayDecorator(),
                new SaturdayDecorator()
        );

        materialCalendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                for(int i = 0; i<calendarinfo.size(); i++){
                    String to = null;
                    Date from_date = date.getDate();
                    SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd");
                    to = fm.format(from_date);

                    if(to.equals(calendarinfo.get(i).getFinished_date())){
                        questTitle.setText(calendarinfo.get(i).getTitle_ko());
                        switch (calendarinfo.get(i).getAchievement()){
                            case "growth": questAchievement.setText("성장"); break;
                            case "travel": questAchievement.setText("여행"); break;
                            case "experience": questAchievement.setText("경험"); break;
                            case "challenge": questAchievement.setText("도전"); break;
                            case "enjoy": questAchievement.setText("즐김"); break;
                            default : break;
                        }
                        questAchievement.setText(calendarinfo.get(i).getAchievement());
                    }
                }
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


        ArrayList<RadarEntry> defdataVals = new ArrayList<>();
        for(int i = 0; i<6; i++){
            defdataVals.add(new RadarEntry(10));
        }

        RadarDataSet defdataSet = new RadarDataSet(defdataVals, "목표 성취도");
        defdataSet.setColor(Color.parseColor("#A9BCF5"));
        defdataSet.setDrawFilled(true);
        defdataSet.setFillColor(Color.parseColor("#A9BCF5"));
        defdataSet.setFillAlpha(50);
        defdataSet.setLineWidth(2f);
        defdataSet.setDrawHighlightCircleEnabled(true);


        RadarData data = new RadarData();
        data.addDataSet(dataSet);
        data.addDataSet(defdataSet);
        String[] labels =  {"성장", "여행", "체험", "도전", "즐김", "의지"};
        XAxis xAxis = chart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
        chart.setData(data);
    }


    //차트 데이터 생성
    public ArrayList<RadarEntry> prepareData() {
        String uid = firebaseUser.getUid();

        calendarinfo = new ArrayList<>();
        
        ArrayList<RadarEntry> dataVals = new ArrayList<>();
        for(int i = 0; i<6; i++){
            dataVals.add(new RadarEntry(50));
        }
        for(int i = 0; i<6; i++){
            dataVals.set(i, new RadarEntry(0));
        }

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
                            Log.d("date", questlogInfo.getFinished_date());
                            Log.d("title_ko", questlogInfo.getTitle_ko());

                            String from = questlogInfo.getFinished_date();
                            SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd");
                            Date to = fm.parse(from);
                            
                            CalendarText calendarText = new CalendarText();
                            calendarText.setTitle_ko(questlogInfo.getTitle_ko());
                            calendarText.setFinished_date(questlogInfo.getFinished_date());
                            calendarText.setAchievement(questlogInfo.getAchievement());
                            
                            calendarinfo.add(calendarText);
                            
                            materialCalendarView.addDecorators(new EventDecorator(Color.RED, Collections.singleton(CalendarDay.from(to))));
                            switch (questlogInfo.getAchievement()){
                                case "growth" : dataVals.set(0, new RadarEntry(dataVals.get(0).getValue() + Float.parseFloat(questlogInfo.getDifficulty())));
                                    dataVals.set(5, new RadarEntry(dataVals.get(5).getValue() + 1));break;
                                case "travel" : dataVals.set(1, new RadarEntry(dataVals.get(1).getValue() + Float.parseFloat(questlogInfo.getDifficulty())));
                                    dataVals.set(5, new RadarEntry(dataVals.get(5).getValue() + 1));break;
                                case "experience" : dataVals.set(2, new RadarEntry(dataVals.get(2).getValue() + Float.parseFloat(questlogInfo.getDifficulty())));
                                    dataVals.set(5, new RadarEntry(dataVals.get(5).getValue() + 1));break;
                                case "challenge" : dataVals.set(3, new RadarEntry(dataVals.get(3).getValue() + Float.parseFloat(questlogInfo.getDifficulty())));
                                    dataVals.set(5, new RadarEntry(dataVals.get(5).getValue() + 1));break;
                                case "enjoy" : dataVals.set(4, new RadarEntry(dataVals.get(4).getValue() + Float.parseFloat(questlogInfo.getDifficulty())));
                                    dataVals.set(5, new RadarEntry(dataVals.get(5).getValue() + 1));break;
                                default : break;
                            }
                        }catch(NullPointerException | ParseException e){}

                    }
                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }
                });

            }catch(NullPointerException e){
                System.out.println("없음");
            }

            }
        return  dataVals;
    }
}

class SaturdayDecorator implements DayViewDecorator{
    private final Calendar calendar = Calendar.getInstance();

    public SaturdayDecorator(){

    }

    @Override
    public boolean shouldDecorate(CalendarDay day){
        day.copyTo(calendar);
        int weekDay = calendar.get(Calendar.DAY_OF_WEEK);
        return weekDay == Calendar.SATURDAY;
    }

    @Override
    public void decorate(DayViewFacade view){
        view.addSpan(new ForegroundColorSpan(Color.BLUE));
    }
}

class SundayDecorator implements DayViewDecorator{
    private final Calendar calendar = Calendar.getInstance();

    public SundayDecorator(){

    }

    @Override
    public boolean shouldDecorate(CalendarDay day){
        day.copyTo(calendar);
        int weekDay = calendar.get(Calendar.DAY_OF_WEEK);
        return weekDay == Calendar.SUNDAY;
    }

    @Override
    public void decorate(DayViewFacade view){
        view.addSpan(new ForegroundColorSpan(Color.RED));
    }
}

class EventDecorator implements DayViewDecorator{
    private final int color;
    private final HashSet<CalendarDay> dates;

    public EventDecorator(int color, Collection<CalendarDay> dates){
        this.color = color;
        this.dates = new HashSet<>(dates);
    }

    @Override
    public boolean shouldDecorate(CalendarDay day){
        return dates.contains(day);
    }

    @Override
    public void decorate(DayViewFacade view){
        view.addSpan(new DotSpan(5, color));
    }
}
