package com.example.levelus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class EditActivity extends AppCompatActivity {
    private EditText email, password;
    private EditText name, age, favorite, local;
    private Button button_edit;

    private UserAccount currentUserInfo;

    private DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("Level Us");
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        Map<String, Object> taskMap = new HashMap<String, Object>();

        email = findViewById(R.id.input_id);
        password = findViewById(R.id.input_password);

        name = findViewById(R.id.input_name);
        age = findViewById(R.id.input_age);
        favorite = findViewById(R.id.input_favorite);
        local = findViewById(R.id.input_local);

        button_edit = findViewById(R.id.button_edit);

        button_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                taskMap.put("age", age.getText().toString());
                taskMap.put("emailId", email.getText().toString());
                taskMap.put("favorite", favorite.getText().toString());
                taskMap.put("local", local.getText().toString());
                taskMap.put("name", name.getText().toString());
                taskMap.put("password", password.getText().toString());
                databaseReference.child("UserAccount").child(firebaseAuth.getUid()).updateChildren(taskMap);
                firebaseUser.delete();
                firebaseUser.updateEmail(email.getText().toString());
                firebaseUser.updatePassword(email.getText().toString());
//                firebaseAuth.updateCurrentUser(firebaseUser);

                Intent intent = new Intent(getApplicationContext(), LoggedPages.class);
                startActivity(intent);
                finish();
            }
        });

        databaseReference.child("UserAccount").child(firebaseUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                currentUserInfo = snapshot.getValue(UserAccount.class);
                String strCurrentUserId = currentUserInfo.getEmailId();
                String strCurrentUserPassword = currentUserInfo.getPassword();

                String strCurrentUserName = currentUserInfo.getName();
                String strCurrentUserAge = currentUserInfo.getAge();
                String strCurrentUserFavorite = currentUserInfo.getFavorite();
                String strCurrentUserLocal = currentUserInfo.getLocal();

                email.setText(strCurrentUserId);
                password.setText(strCurrentUserPassword);

                name.setText(strCurrentUserName);
                age.setText(strCurrentUserAge);
                favorite.setText(strCurrentUserFavorite);
                local.setText(strCurrentUserLocal);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}