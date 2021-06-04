package com.example.levelus;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.loader.content.CursorLoader;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.InputStream;
import java.net.URL;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditMyInfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditMyInfoFragment extends Fragment{

    private DatabaseReference mDatabaseRef;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();
    private FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();
    private FirebaseStorage storage;
    private StorageReference storageRef;
    private String imagePath;
    private String fileName;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private final int GET_GALLERY_IMAGE = 200;

    ImageView user_img;
    TextView user_name, user_age, user_favorite, user_local;
    private String strName, strAge, strFavorite, strLocal;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public EditMyInfoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EditMyInfoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EditMyInfoFragment newInstance(String param1, String param2) {
        EditMyInfoFragment fragment = new EditMyInfoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
        }
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReferenceFromUrl("gs://collabtest-71a4d.appspot.com");
        mDatabaseRef = firebaseDatabase.getReference("Level Us");
        mDatabaseRef.child("UserAccount").child(firebaseUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserAccount userAccount = dataSnapshot.getValue(UserAccount.class);

                //각각의 값 받아오기 get어쩌구 함수들은 Together_group_list.class에서 지정한것
                strName = userAccount.getName();
                strAge = userAccount.getAge();
                strFavorite = userAccount.getFavorite();
                strLocal = userAccount.getLocal();

                //텍스트뷰에 받아온 문자열 대입하기
                user_name.setText(strName);
                user_age.setText(strAge);
                user_favorite.setText(strFavorite);
                user_local.setText(strLocal);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("MainActivity", String.valueOf(databaseError.toException())); // 에러문 출력
            }
        });
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_my_info, container, false);

        TextView logout = view.findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFirebaseAuth.signOut();
                Intent BackToLoginActivity = new Intent(getActivity(), LoginActivity.class);
                startActivity(BackToLoginActivity);
                getActivity().finish();
            }
        });

        user_img = view.findViewById(R.id.user_img);

        user_name = view.findViewById(R.id.user_name);
        user_age = view.findViewById(R.id.user_age);
        user_favorite = view.findViewById(R.id.user_favorite);
        user_local = view.findViewById(R.id.user_local);

        Intent intent = new Intent();

        user_name.setText(intent.getStringExtra("name"));
        user_age.setText(intent.getStringExtra("age"));
        user_favorite.setText(intent.getStringExtra("favorite"));
        user_local.setText(intent.getStringExtra("local"));

        if(storageRef.child(firebaseUser.getUid()+"/profile_img") != null){
            StorageReference submitProfile = storageRef.child(firebaseUser.getUid()+"/profile_img");
            submitProfile.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if(task.isSuccessful())
                        Glide.with(EditMyInfoFragment.this).load(task.getResult()).into(user_img);
                }

            });
        }

        user_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);

                startActivityForResult(intent, GET_GALLERY_IMAGE);
            }
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
//        AppCompatActivity appCompatActivity = newInstance();
        if (requestCode == GET_GALLERY_IMAGE && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {

            Uri selectedImageUri = data.getData();
            imagePath = getPath(selectedImageUri);
            user_img.setImageURI(selectedImageUri);

//            System.out.println(getPath(selectedImageUri));

            Uri file = Uri.fromFile(new File(imagePath));

            StorageReference riversRef = storageRef.child(firebaseUser.getUid()+"/profile_img");
            UploadTask uploadTask = riversRef.putFile(file);

// Register observers to listen for when the download is done or if it fails
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle unsuccessful uploads
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                    // ...
//                    Uri downloadURL = taskSnapshot.getUploadSessionUri();
//                    UserImage userImage = new UserImage();
//                    userImage.setImageUrl(downloadURL.toString());
//                    mDatabaseRef.child("UserAccount").child(firebaseUser.getUid()).child("userImage").setValue(userImage);
                }
            });

        }
        else if(resultCode == Activity.RESULT_CANCELED) {
            Toast.makeText(getActivity(), "사진 선택 취소", Toast.LENGTH_LONG).show();
        }
    }
    public String getPath(Uri uri){
        String[] proj = {MediaStore.Images.Media.DATA};
        CursorLoader cursorLoader = new CursorLoader(getActivity(), uri, proj, null, null, null);

        Cursor cursor = cursorLoader.loadInBackground();
        int index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

        cursor.moveToFirst();

        return cursor.getString(index);
    }
}