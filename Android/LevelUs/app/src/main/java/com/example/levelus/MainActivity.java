package com.example.levelus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
//    private boolean state = firebaseAuth.

    Button login_in,sign_in;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if(firebaseUser != null){
            Intent GoToLoggedPages = new Intent(getApplicationContext(), LoggedPages.class);
            startActivity(GoToLoggedPages);
            Toast.makeText(MainActivity.this, "로그인 성공", Toast.LENGTH_SHORT).show();
            finish();
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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