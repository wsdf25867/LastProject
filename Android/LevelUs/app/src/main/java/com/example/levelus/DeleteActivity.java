package com.example.levelus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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

public class DeleteActivity extends AppCompatActivity {
    private EditText input_id, input_password;
    private Button button_admit;

    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Level Us");
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference storageRef = storage.getReferenceFromUrl("gs://collabtest-71a4d.appspot.com");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);
        input_id = findViewById(R.id.input_id);
        input_password = findViewById(R.id.input_password);
        button_admit = findViewById(R.id.button_admit);
        button_admit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strEmail = input_id.getText().toString();
                String strPassword = input_password.getText().toString();
                databaseReference.child("UserAccount").child(firebaseUser.getUid()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        UserAccount userAccount = snapshot.getValue(UserAccount.class);
                        if(snapshot.exists()){
                            if(strEmail.equals(userAccount.getEmailId())&&strPassword.equals(userAccount.getPassword())){
                                Toast.makeText(DeleteActivity.this,"아이디, 비밀번호 일치",Toast.LENGTH_SHORT).show();
                                AlertDialog.Builder askDeleteAccount = new AlertDialog.Builder(DeleteActivity.this);
                                askDeleteAccount.setIcon(R.mipmap.ic_launcher);
                                askDeleteAccount.setTitle("회원탈퇴");
                                askDeleteAccount.setMessage("정말 회원탈퇴 하시겠습니까?");

                                askDeleteAccount.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        if(storageRef.child(firebaseUser.getUid()+"/profile_img") != null){
                                            storageRef.child(firebaseUser.getUid()+"/profile_img").delete();
                                            storageRef.child(firebaseUser.getUid()).delete();
                                        }
                                        firebaseUser.delete();
                                        firebaseAuth.signOut();
                                        databaseReference.child("UserAccount").child(firebaseUser.getUid()).removeValue();
                                        dialog.dismiss();
                                        Toast.makeText(DeleteActivity.this,"회원탈퇴 성공",Toast.LENGTH_SHORT).show();
                                        Intent GoToMainActivity = new Intent(DeleteActivity.this, MainActivity.class);
                                        MainActivity.isLoginSuccess = false;
                                        startActivity(GoToMainActivity);
                                        finish();

//                                    LoggedPages lp = LoggedPages.loggedPages;
//                                    lp.finish();
                                    }
                                });

                                askDeleteAccount.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                                askDeleteAccount.show();
                            }
                            else{
                                Toast.makeText(DeleteActivity.this,"아이디, 비밀번호 불일치",Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }
                });
            }
        });
    }
}