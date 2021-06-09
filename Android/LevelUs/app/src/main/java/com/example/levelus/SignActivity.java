package com.example.levelus;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class SignActivity extends AppCompatActivity {
    private TextView login;

    private FirebaseAuth mFirebaseAuth;
    private DatabaseReference mDatabaseRef;
    private EditText email, password;
    private EditText name, age, favorite, local;
    private Button button_sign;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        login = findViewById(R.id.login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent GoToLoginActivity = new Intent(SignActivity.this, LoginActivity.class);
                startActivity(GoToLoginActivity);
                finish();
            }
        });

        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Level Us");

        email = findViewById(R.id.input_id);
        password = findViewById(R.id.input_password);

        name = findViewById(R.id.input_name);
        age = findViewById(R.id.input_age);
        favorite = findViewById(R.id.input_favorite);
        local = findViewById(R.id.input_local);

        button_sign = findViewById(R.id.button_sign);

        button_sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strEmail = email.getText().toString();
                String strPassword = password.getText().toString();

                String strName = name.getText().toString();
                String strAge = age.getText().toString();
                String strFavorite = favorite.getText().toString();
                String strLocal = local.getText().toString();

                mFirebaseAuth.createUserWithEmailAndPassword(strEmail,strPassword).addOnCompleteListener(SignActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();
                            UserAccount account = new UserAccount();
                            account.setIdToken(firebaseUser.getUid());
                            account.setEmailId(firebaseUser.getEmail());
                            account.setPassword(strPassword);

                            account.setName(strName);
                            account.setAge(strAge);
                            account.setFavorite(strFavorite);
                            account.setLocal(strLocal);

                            String level = "0";

                            mDatabaseRef.child("UserAccount").child(firebaseUser.getUid()).setValue(account);
//                            mDatabaseRef.child("SortLevel").push().child("level").setValue(level); //정렬용 level
//                            mDatabaseRef.child("UserLevel").child(firebaseUser.getUid()).setValue(level); //접속자용 level

                            Toast.makeText(SignActivity.this, "회원가입 성공", Toast.LENGTH_SHORT).show();


                            Intent GoToWebPractice = new Intent(SignActivity.this, WebPractice.class);
                            GoToWebPractice.putExtra("uid",firebaseUser.getUid());

                            startActivity(GoToWebPractice);
                        }
                        else{
                            Toast.makeText(SignActivity.this, "회원가입 실패, 이메일 형식확인, 중복된 이메일, 또는 비밀번호 6자리 이상으로 입력", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}
