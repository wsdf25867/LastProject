package com.example.levelus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
//    private boolean state = firebaseAuth.
    private String TAG = "VideoActivity";
    private VideoView videoView;

    public static boolean isLoginSuccess = true;
    Button login_in,sign_in;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if(firebaseUser != null && isLoginSuccess){
            Intent GoToLoggedPages = new Intent(getApplicationContext(), LoggedPages.class);
            startActivity(GoToLoggedPages);
            Toast.makeText(MainActivity.this, "로그인 성공", Toast.LENGTH_SHORT).show();
            finish();
        }
//        Log.d("유저 Uid",firebaseUser.getUid().toString());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        videoView = (VideoView) findViewById(R.id.video_view);
        //play video
        videoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.water_video));

        videoView.start();
        //loop
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true); // 동영상 무한 반복. 반복을 원치 않을 경우 false
            }
        });

        login_in = findViewById(R.id.log_in);
        sign_in = findViewById(R.id.sign_in);
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


}