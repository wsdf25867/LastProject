package com.example.levelus;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MyService extends Service {

    private static final String TAG = "지금이순간";
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference rRef = firebaseDatabase.getReference("recommend_list");
    private DatabaseReference iRef = firebaseDatabase.getReference("quest_log");
    private FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();
    private FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();

    String uid;

    ArrayList<QuestInfo> rData = new ArrayList<>();
    ArrayList<QuestlogInfo> iData = new ArrayList<>();
    ArrayList<String> qData = new ArrayList<>();

    RecommendListAdapter rAdapter;
    IngQuestAdapter iAdapter;

    Intent intent;

    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {

        super.onCreate();

        try {
            uid = firebaseUser.getUid();
            getIngQuest();
            getRecommendData();

        }catch(NullPointerException e){
            this.onDestroy();
        }

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (intent == null) {
            return Service.START_STICKY;
        } else {
            System.out.println("여기왔니?");

        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void getRecommendData() {
        rRef.child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                rData.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    QuestInfo questInfo = dataSnapshot.getValue(QuestInfo.class);
                    rData.add(questInfo);

                }
                rAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
        rAdapter = new RecommendListAdapter(rData);
    }

    public void getIngQuest() {
        iRef.child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                iData.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    QuestlogInfo questlogInfo = dataSnapshot.getValue(QuestlogInfo.class);
                    if (questlogInfo.getRating().equals("0")) {
                        iData.add(questlogInfo);
                        if (questlogInfo.getPeriod() != null) {
                            if (Long.parseLong(questlogInfo.getPeriod()) - calDate(questlogInfo.getAccepted_date()) <= 1) {
                                show(questlogInfo.getTitle_ko());
                            }
                        }
                    }
                }
                iAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
        iAdapter = new IngQuestAdapter(iData);
    }

    private void show(String title) {


        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "default");

        builder.setSmallIcon(R.drawable.app_logo);
        builder.setContentTitle("LEVEL US");
        builder.setContentText("진행중인 퀘스트 '" + title + "'가 하루 남았습니다");


        //인텐트 설정
        intent = new Intent(this, MainActivity.class); //페이지 이동해버리기

        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        builder.setContentIntent(pendingIntent);

        this.onDestroy();

        Bitmap largeIcon = BitmapFactory.decodeResource(getResources(),
                R.drawable.app_logo);

        builder.setLargeIcon(largeIcon);
        builder.setColor(Color.BLACK);

        Uri ringtoneUri = RingtoneManager.getActualDefaultRingtoneUri(getApplicationContext(),
                RingtoneManager.TYPE_NOTIFICATION);

        builder.setSound(ringtoneUri);

        long[] vibrate = {0, 100, 200, 300};
        builder.setVibrate(vibrate);
        builder.setAutoCancel(true); //알림을 클릭하면 알림 없앰

        NotificationManager manager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            manager.createNotificationChannel(new NotificationChannel("default", "기본 채널",
                    NotificationManager.IMPORTANCE_DEFAULT));
        }
        manager.notify(1, builder.build());
    }

    public long calDate(String accepted_date) {
        long calDateDays = 0;
        // 현재 날짜 가져오기 (설정한 형식인 "yyyy-MM-dd HH:mm:ss" 대로 가져와진다.
        // current = "2020-08-29 21:58:50";
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String current = format1.format(System.currentTimeMillis());


        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); // 소문자mm으로 할 경우 분을 의미한다.

            // current, last 두 날짜를 parse()를 통해 Date형으로 변환.
            Date currentDate = format.parse(current);
            Date acceptedDate = format.parse(accepted_date);

            // Date로 변환된 두 날짜를 계산한 후, 리턴값으로 long type 변수를 초기화
            // 연산의 결과 -950400000 long type 으로 return
            long calDate = currentDate.getTime() - acceptedDate.getTime();

            // getTime()은 해당날짜를 기준으로1970년 00:00:00 부터 몇 초가 흘렀는지를 반환해준다.
            // 24*60*60*1000(각 시간값에 따른 차이점) 을 나눠주면 일수가 나온다.
            calDateDays = calDate / (24 * 60 * 60 * 1000);

            calDateDays = Math.abs(calDateDays);

            System.out.println("날짜 계산 결과 : " + calDateDays);
        } catch (ParseException e) {
            // 예외 처리
        }


        return calDateDays;
    }
}