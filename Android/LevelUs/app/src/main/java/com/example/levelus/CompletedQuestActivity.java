package com.example.levelus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class CompletedQuestActivity extends AppCompatActivity {

    private ArrayList<QuestlogInfo> loglist = new ArrayList<>();     // quest_log 데이터 저장 리스트
    private ArrayList<QuestlogInfo> searchlist =  new ArrayList<>();    // 검색 리스트

    private TextView back;
    private TextView logout;
    private ListView listView;          // 검색을 보여줄 리스트변수
    private EditText editSearch;        // 검색어를 입력할 Input 창
    private SearchAdapter adapter;      // 리스트뷰에 연결할 아답터
    int cnt; //quest_log 개수 반환

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference mDatabaseRef = firebaseDatabase.getReference("quest_log");
    private FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();
    private FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quest_finished);

        editSearch = (EditText) findViewById(R.id.editSearch);
        listView = (ListView) findViewById(R.id.listView);
        back = (TextView) findViewById(R.id.back);
        logout = (TextView) findViewById(R.id.logout);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent GoToLoggedPages = new Intent(getApplicationContext(), LoggedPages.class);
                startActivity(GoToLoggedPages);
                Toast.makeText(CompletedQuestActivity.this, "되돌아가기", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        // 검색에 사용할 데이터을 미리 저장한다.
        prepareData();

        // 리스트의 모든 데이터를 searchlist에 복사한다.// list 복사본을 만든다.
        searchlist = new ArrayList<QuestlogInfo>();
        searchlist.addAll(loglist);

        // 리스트에 연동될 아답터를 생성한다.
        adapter = new SearchAdapter(searchlist,this);

        // 리스트뷰에 아답터를 연결한다.
        listView.setAdapter(adapter);

        // input창에 검색어를 입력시 "addTextChangedListener" 이벤트 리스너를 정의한다.
        editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                // input창에 문자를 입력할때마다 호출된다.
                // search 메소드를 호출한다.
                String text = editSearch.getText().toString();
                search(text);
            }
        });



    }

    public void prepareData() {
        String uid = firebaseUser.getUid();
        mDatabaseRef.child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                cnt = (int)snapshot.getChildrenCount();
                Log.d("count",Integer.toString(cnt));

                for(int i =0;i<cnt;i++){
                    mDatabaseRef.child(uid).child(Integer.toString(i)).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                            QuestlogInfo questlogInfo = snapshot.getValue(QuestlogInfo.class);
                            Log.d("questInfo",questlogInfo.getTitle_ko());
                            loglist.add(questlogInfo);
                        }

                        @Override
                        public void onCancelled(@NonNull @NotNull DatabaseError error) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });


    }

    // 검색을 수행하는 메소드
    public void search(String charText) {

        // 문자 입력시마다 리스트를 지우고 새로 뿌려준다.
        searchlist.clear();

        // 문자 입력이 없을때는 모든 데이터를 보여준다.
        if (charText.length() == 0) {
            searchlist.addAll(loglist);
        }
        // 문자 입력을 할때..
        else
        {
            // 리스트의 모든 데이터를 검색한다.
            //Log.d("size",Integer.toString(loglist.size())) ;
            Log.d("charText",charText);
            for(int i = 0;i < loglist.size(); i++)
            {
                Log.d("title", loglist.get(i).getTitle_ko());
                // searchlist의 모든 데이터에 입력받은 단어(charText)가 포함되어 있으면 true를 반환한다.
                if (loglist.get(i).getTitle_ko().toLowerCase().contains(charText))
                {
                    // 검색된 데이터를 리스트에 추가한다.
                    searchlist.add(loglist.get(i));
                }
            }
        }
        // 리스트 데이터가 변경되었으므로 아답터를 갱신하여 검색된 데이터를 화면에 보여준다.
        adapter.notifyDataSetChanged();
    }
}