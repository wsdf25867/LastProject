package com.example.levelus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class LoggedPages extends AppCompatActivity {
    //    private TextView logout;
//    private FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();
//    public static LoggedPages loggedPages;
    private EditMyInfoFragment editMyInfoFragment = new EditMyInfoFragment();
    private MyQuestFragment myQuestFragment = new MyQuestFragment();
    private QuestListFragment questListFragment = new QuestListFragment();
    private RankFragment rankFragment = new RankFragment();

    private long backBtnTime = 0;
    private Fragment selected_fragment = null, current_fragment, preview_fragment;
    private FragmentTransaction transaction;

    private BottomNavigationView navigationView;
    private BottomNavigationView.OnNavigationItemSelectedListener listener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            preview_fragment = selected_fragment;
            switch(item.getItemId()){
                case R.id.edit_my_info:
                    selected_fragment = editMyInfoFragment;
                    break;
                case R.id.media_play:
                    selected_fragment = myQuestFragment;
                    break;
                case R.id.plus_square:
                    selected_fragment = questListFragment;
                    break;
                case R.id.bar_chart:
                    selected_fragment = rankFragment;
                    break;
            }
            current_fragment = selected_fragment;
            transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frame_layout, selected_fragment);
            if(preview_fragment != current_fragment)
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
                long curTime = System.currentTimeMillis();
                long gapTime = curTime - backBtnTime;

                if(0 <= gapTime && 2000 >= gapTime) {
                    super.onBackPressed();
                }
                else {
                    backBtnTime = curTime;
                    Toast.makeText(this, "한번 더 누르면 종료됩니다.",Toast.LENGTH_SHORT).show();
                }
            }
            else{
                super.onBackPressed();
            }
        }
    }
}