package com.example.levelus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.view.View;


import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;


public class CompletedQuestActivity extends AppCompatActivity {

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference mDatabaseRef;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser firebaseUser;
    private FirebaseStorage storage;
    private StorageReference storageRef;

    private ArrayList<ListViewItem> listViewItemList ;


    private TextView back;
    private ListView listView = null;          // 검색을 보여줄 리스트변수
    private CompletedQuestAdapter adapter;      // 리스트뷰에 연결할 아답터
    private Drawable questThumbnail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quest_finished);

        firebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseRef = firebaseDatabase.getReference("quest_log");
        mFirebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = mFirebaseAuth.getCurrentUser();
        storage = FirebaseStorage.getInstance("gs://collabtest-71a4d.appspot.com");
        storageRef = storage.getReference();


        listViewItemList = new ArrayList<ListViewItem>();
        // 검색에 사용할 데이터을 미리 저장한다.
        this.prepareData();

        // Adapter 생성
        adapter = new CompletedQuestAdapter(listViewItemList, this) ;


        listView = (ListView) findViewById(R.id.listview1);
        listView.setAdapter(adapter);


        ((CompletedQuestAdapter)listView.getAdapter()).getFilter().filter(" ") ;

        EditText editTextFilter = (EditText)findViewById(R.id.editTextFilter) ;
        editTextFilter.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable edit) {
                String filterText = edit.toString() ;
                ((CompletedQuestAdapter)listView.getAdapter()).getFilter().filter(filterText) ;
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        }) ;
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

    }


    public void prepareData() {
        String uid = firebaseUser.getUid();
        for(int i =0; i<= 222;i++){
            try{
                mDatabaseRef.child(uid).child(Integer.toString(i)).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        questThumbnail = null;
                        QuestlogInfo questlogInfo = snapshot.getValue(QuestlogInfo.class);
                        try{
                            if(Float.parseFloat(questlogInfo.getRating()) > 0){
                                storageRef.child(uid).child(questlogInfo.getQuest_num()+".jpeg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        Glide.with(getApplicationContext()).asBitmap().load(uri)
                                                .into(new SimpleTarget<Bitmap>() {
                                                    @Override
                                                    public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                                                        questThumbnail = new BitmapDrawable(getResources(),resource);
                                                        //Drawable icon, String title_ko, String rating, String category, String accepted_date, String finished_date
                                                        addItem(questThumbnail, questlogInfo.getTitle_ko(),questlogInfo.getRating(),questlogInfo.getCategory(),questlogInfo.getAccepted_date(),questlogInfo.getFinished_date());
                                                        ((CompletedQuestAdapter)listView.getAdapter()).getFilter().filter(" ") ;
                                                    }
                                                });
                                    }
                                });
                            }
                        }catch(NullPointerException e){
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }
                });
            }catch(NullPointerException e){

            }
        }


    }

    // 아이템 데이터 추가를 위한 함수. 개발자가 원하는대로 작성 가능.
    public void addItem(Drawable icon, String title_ko, String rating, String category, String accepted_date, String finished_date ) {
        ListViewItem item = new ListViewItem();

        item.setIconDrawable(icon);
        item.setTitle_ko(title_ko);
        item.setRating(rating);
        item.setCategory(category);
        item.setAccepted_date(accepted_date);
        item.setFinished_date(finished_date);

        listViewItemList.add(item);
    }



}