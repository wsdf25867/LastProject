package com.example.levelus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class NotificationMoveActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_move);

        Intent intent = new Intent(getApplicationContext(),MyQuestFragment.class);



        finish();
    }
}