package com.example.levelus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

public class MainActivity extends AppCompatActivity {
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
    private DatabaseReference databaseReference = firebaseDatabase.getReference("Level Us");
    private String TAG = "VideoActivity";
    private VideoView videoView;
    private TextView titleText;
//    private static String SAVE_INSTANCE_KEY;
//    private boolean isChanged = false;
    private int rNum;
    public static boolean isLoginSuccess = true;
    Button login_in,sign_in;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        rNum = (int)((Math.random()*10000)%4);
        if(firebaseUser != null && isLoginSuccess){
            Intent GoToLoggedPages = new Intent(getApplicationContext(), LoggedPages.class);
            startActivity(GoToLoggedPages);
            Toast.makeText(MainActivity.this, "로그인 성공", Toast.LENGTH_SHORT).show();
            finish();
        }

        //play video
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        if (savedInstanceState != null) {
//            rNum = savedInstanceState.getInt("");
//        }
        titleText = findViewById(R.id.first_page);
        videoView = (VideoView) findViewById(R.id.video_view);
        login_in = findViewById(R.id.log_in);
        sign_in = findViewById(R.id.sign_in);
//        videoView.setVideoURI(null);
        switch (rNum) {
            case 0:
                videoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.main_background_video));
                titleText.setTextColor(Color.parseColor("#ffffff"));
                break;
            case 1:
                videoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.main_background_video1));
                titleText.setTextColor(Color.parseColor("#ffffff"));
                break;
            case 2:
                videoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.main_background_video2));
                titleText.setTextColor(Color.parseColor("#ffffff"));
                break;
            default:
                videoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.main_background_video3));
                titleText.setTextColor(Color.parseColor("#000000"));
                break;
        }
//        videoView.start();
//        SAVE_INSTANCE_KEY = "key";
//        if(savedInstanceState != null && SAVE_INSTANCE_KEY != null){
//            videoView.resume();
//        }
//        loop
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true); // 동영상 무한 반복. 반복을 원치 않을 경우 false
                if(!mp.isPlaying()) {
                    videoView.start();
                }
            }
        });
        login_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent moveToLoginActivity = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(moveToLoginActivity);
            }
        });
        sign_in.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent moveToSignActivity = new Intent(MainActivity.this, SignActivity.class);
                startActivity(moveToSignActivity);
            }
        });
    }
//    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//        isChanged = true;
//        outState.putBoolean("onSaveInstanceState", isChanged);
//        super.onSaveInstanceState(outState);
//    }
    @Override
    protected void onResume() {
        super.onResume();
        rNum = (int)((Math.random()*10000)%4);
        switch (rNum) {
            case 0:
                videoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.main_background_video));
                titleText.setTextColor(Color.parseColor("#ffffff"));
                break;
            case 1:
                videoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.main_background_video1));
                titleText.setTextColor(Color.parseColor("#ffffff"));
                break;
            case 2:
                videoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.main_background_video2));
                titleText.setTextColor(Color.parseColor("#ffffff"));
                break;
            default:
                videoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.main_background_video3));
                titleText.setTextColor(Color.parseColor("#000000"));
                break;
        }
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true); // 동영상 무한 반복. 반복을 원치 않을 경우 false
                if(!mp.isPlaying()) {
                    videoView.start();
                }
            }
        });
    }

}