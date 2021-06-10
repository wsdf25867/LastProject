package com.example.levelus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.io.InputStream;

public class LoggedPages extends AppCompatActivity {
    //    private TextView logout;
//    private FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();
//    public static LoggedPages loggedPages;

    private Fragment selected_fragment = null;
    private FragmentTransaction transaction;

    private BottomNavigationView navigationView;
    private BottomNavigationView.OnNavigationItemSelectedListener listener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch(item.getItemId()){
                case R.id.edit_my_info:
                    selected_fragment = new EditMyInfoFragment();
                    break;
                case R.id.media_play:
                    selected_fragment = new MyQuestFragment();
                    break;
                case R.id.plus_square:
                    selected_fragment = new QuestListFragment();
                    break;
                case R.id.bar_chart:
                    selected_fragment = new RankFragment();
                    break;
            }
            transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frame_layout, selected_fragment);
            transaction.addToBackStack(null);
            transaction.commit();
//            getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, selected_fragment).commit();
            return true;
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        loggedPages = LoggedPages.this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loggedpages);

        if(selected_fragment == null){
            selected_fragment = new EditMyInfoFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, selected_fragment).commit();
        }

        navigationView = findViewById(R.id.bottom_nav);
        navigationView.setOnNavigationItemSelectedListener(listener);
    }
    public interface onKeyBackPressedListener {
        void onBackKey();
    }
    private onKeyBackPressedListener mOnKeyBackPressedListener;
    public void setOnKeyBackPressedListener(onKeyBackPressedListener listener) {
        mOnKeyBackPressedListener = listener;
    } //메인에서 토스트를 띄우며 종료확인을 하기 위해 필드선언
    @Override
    public void onBackPressed() {
        if (mOnKeyBackPressedListener != null) {
            mOnKeyBackPressedListener.onBackKey();
        }
        else { //쌓인 BackStack 여부에 따라 Toast를 띄울지, 뒤로갈지
            if(getSupportFragmentManager().getBackStackEntryCount()==0){
                //* 종료 EndToast Bean 사용
                Toast.makeText(LoggedPages.this, "종료하려면 한번 더 누르세요.",Toast.LENGTH_SHORT).show();
                finish();
            }
            else{
                super.onBackPressed();
            }
        }
    }
}