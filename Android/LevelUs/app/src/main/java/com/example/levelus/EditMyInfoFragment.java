package com.example.levelus;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.io.InputStream;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditMyInfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditMyInfoFragment extends Fragment{

    private DatabaseReference mDatabaseRef;
    private FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();
    private FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private static final int REQUEST_CODE = 1;

    ImageView user_img;
    TextView user_name, user_age, user_favorite, user_local;
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

        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Level Us");
        mDatabaseRef.child("UserAccount").child(firebaseUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserAccount userAccount = dataSnapshot.getValue(UserAccount.class);

                //각각의 값 받아오기 get어쩌구 함수들은 Together_group_list.class에서 지정한것
                String strName = userAccount.getName();
                String strAge = userAccount.getAge();
                String strFavorite = userAccount.getFavorite();
                String strLocal = userAccount.getLocal();

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


        user_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (view.getId()) { // 갤러리에서 사진 업로드 클릭
                    case R.id.user_img: // 갤러리에서 사진 가져오기 창을 띄운다.
                        Intent intent = new Intent();
                        intent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT); // 위의 Activity를 실행한 이후 이벤트를 정의
                        startActivityForResult(intent, REQUEST_CODE);
                        break;
                }
            }
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
//        AppCompatActivity appCompatActivity = newInstance();
        if(requestCode == REQUEST_CODE)
        {
            if(resultCode == Activity.RESULT_OK)
            {
                try{
                    InputStream in = getActivity().getContentResolver().openInputStream(data.getData());

                    Bitmap img = BitmapFactory.decodeStream(in);
                    in.close();

                    user_img.setImageBitmap(img);
                }catch(Exception e)
                {

                }
            }
            else if(resultCode == Activity.RESULT_CANCELED)
            {
                Toast.makeText(getActivity(), "사진 선택 취소", Toast.LENGTH_LONG).show();
            }
        }
    }
}