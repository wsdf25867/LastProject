package com.example.levelus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button login_in,sign_in;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
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