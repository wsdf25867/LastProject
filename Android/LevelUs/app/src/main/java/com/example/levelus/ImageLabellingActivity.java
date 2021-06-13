package com.example.levelus;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

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
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.label.FirebaseVisionImageLabel;
import com.google.firebase.ml.vision.label.FirebaseVisionImageLabeler;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ImageLabellingActivity extends AppCompatActivity implements LocationListener {

    private Button captureImageBtn;                 //사진찍기
    private ImageView imageView;                    //찍은 이미지
    private TextView resultTv;                      //찍은 이미지의 텍스트
    static final int REQUEST_IMAGE_CAPTURE = 1;     //찍은 사진 1장의 의미 인가?

    private Bitmap imageBitmap; //인코딩된 이미지

    //gps관련 (사진에서 말고 현재 위치 가져오는 방식임!)
    final static String TAG = "MSP03";
    final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    TextView logView;   //처음 받아오는 현재위치
    LocationManager lm;

    TextView location2; //사진 찍으면 고정되는 현재위치

    double lat;         //처음 받아오는 좌표
    double lng;         //처음 받아오는 좌표
//    double nowLat;      //사진 찍으면 고정되는 현재좌표
//    double nowLng;      //사진 찍으면 고정되는 현재좌표
    double nowLat = -33.53222;      //사진 찍으면 고정되는 현재좌표  test때문에 호주로 고정해놓은 상태
    double nowLng = 151.21111;      //사진 찍으면 고정되는 현재좌표
    TextView txt;       //주소반환 버튼 누를 시 반환되는 주소 값
    Button b1;          //주소반환 버튼

    //현재시간
    long now = System.currentTimeMillis();  //현재시간
    Date date = new Date(now);              //Date로 형변환
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");       //원하는 시간형식으로 변환

    String finished_date = dateFormat.format(date);

    Boolean objectSuccess = false;

    //intent로 값 받아와야 함.
    String title_ko;    //얘는 테스트용
    String quest_num;
    String way;

    //db에서 받아오는 keyword
    String keyword;
    

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference mDatabaseRef2 = firebaseDatabase.getReference("quest");
    private DatabaseReference mDatabaseRef = firebaseDatabase.getReference("quest_log");
    private FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();
    private FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();



    //인식된 객체 배열
    ArrayList list = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_labelling);

        RatingBar rb = (RatingBar)findViewById(R.id.rb);
        Button submit = (Button)findViewById(R.id.submit);

        rb.setVisibility(View.INVISIBLE);
        submit.setVisibility(View.INVISIBLE);



        //gps관련
        logView = (TextView) findViewById(R.id.location);    //gps2

        location2 = (TextView) findViewById(R.id.location2);
        b1 = (Button) findViewById(R.id.b1);
        txt = (TextView)findViewById(R.id.txt);

        // LocationManager 참조 객체
        lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        //intent로 값 받아와야 함.
        Intent intent = getIntent();
        quest_num = intent.getStringExtra("quest_num");
        title_ko = intent.getStringExtra("title_ko");
        System.out.println("사진 찍기 전 받아오는지 확인");
        System.out.println(quest_num);

        //검증방법 받아오기
        mDatabaseRef2.child("ALL").child(quest_num).child("way").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<DataSnapshot> task) {
                way = String.valueOf(task.getResult().getValue());
                System.out.println("db에서 가져오는 way : "+ way);    //이게 지금 null이다.
                if(way.equals("object")){
                    txt.setVisibility(View.INVISIBLE);
                    b1.setVisibility(View.INVISIBLE);
                    logView.setVisibility(View.INVISIBLE);
                    location2.setVisibility(View.INVISIBLE);
                    captureImageBtn.setVisibility(View.VISIBLE);
                }else if(way.equals("gps")){
                    captureImageBtn.setVisibility(View.INVISIBLE);
                    b1.setVisibility(View.VISIBLE);
                }
            }
        });

        //keyword받아오기
        mDatabaseRef2.child("ALL").child(quest_num).child("keyword").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<DataSnapshot> task) {
                keyword = String.valueOf(task.getResult().getValue());
                System.out.println("db에서 가져오는 keyword : "+ keyword);
            }
        });



        Geocoder g = new Geocoder(this);    //좌표 -> 주소  //gps검증하기
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                nowLat = lat;
//                nowLng = lng;
                location2.setText("latitude: " + nowLat + ", longitude: " + nowLng);
                List<Address> address = null;
                try {
                    address = g.getFromLocation(nowLat,nowLng,10);
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.d("test","입출력오류");
                }
                if(address!=null){
                    if(address.size()==0){
                        txt.setText("주소찾기 오류");
                    }else{
                        Log.d("찾은 주소",address.get(0).toString());
                        txt.setText(address.get(0).getAddressLine(0));
                        String countryName = address.get(0).getCountryName();
                        String admin = address.get(0).getAdminArea();
                        String sub_admin = address.get(0).getSubAdminArea();
                        String locality = address.get(0).getLocality();
                        if(keyword.equals(countryName) || keyword.equals(admin) || keyword.equals(sub_admin) || keyword.equals(locality)){
                            Toast myToast = Toast.makeText(ImageLabellingActivity.this.getApplicationContext(),"검증에 성공하셨습니다", Toast.LENGTH_SHORT);
                            myToast.show();
                            rb.setVisibility(View.VISIBLE);
                            submit.setVisibility(View.VISIBLE);
                            //제출 버튼 클릭했을때
                            submit.setOnClickListener(new View.OnClickListener(){


                                @Override
                                public void onClick(View view) {
                                    Toast.makeText(getApplicationContext(),"제출했당~",Toast.LENGTH_SHORT).show();
                                    String rating;
                                    rating = String.valueOf(rb.getRating());
                                    mDatabaseRef.child(firebaseUser.getUid()).child(quest_num).child("rating").setValue(rating);
                                    mDatabaseRef.child(firebaseUser.getUid()).child(quest_num).child("finished_date").setValue(finished_date);
                                }
                            });

                        }else {
                            Toast myToast = Toast.makeText(ImageLabellingActivity.this.getApplicationContext(),"해당 위치가 아닙니다.", Toast.LENGTH_SHORT);
                            myToast.show();
                        }
                        
                    }
                }
            }
        });


        captureImageBtn = findViewById(R.id.capture_image_btn);
        resultTv = findViewById(R.id.textView);
        imageView = findViewById(R.id.imageView);

        captureImageBtn.setOnClickListener(new View.OnClickListener() { //검증사진촬영 버튼 누를시
            @Override
            public void onClick(View v) {
                list.clear();

                dispatchTakePictureIntent();

                if(objectSuccess == true){
                    rb.setVisibility(View.VISIBLE);
                    submit.setVisibility(View.VISIBLE);
                    //제출 버튼 클릭했을때
                    submit.setOnClickListener(new View.OnClickListener(){


                        @Override
                        public void onClick(View view) {
                            Toast.makeText(getApplicationContext(),"제출했당~",Toast.LENGTH_SHORT).show();
                            String rating;
                            rating = String.valueOf(rb.getRating());
                            mDatabaseRef.child(firebaseUser.getUid()).child(quest_num).child("rating").setValue(rating);
                            mDatabaseRef.child(firebaseUser.getUid()).child(quest_num).child("finished_date").setValue(finished_date);
                        }
                    });
                }
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ContextCompat.checkSelfPermission(ImageLabellingActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(ImageLabellingActivity.this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

            } else {

                ActivityCompat.requestPermissions(ImageLabellingActivity.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
            }
        } else {

            // GPS provider를 이용
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        }
    }   //gps2

    @Override
    public void onLocationChanged(@NonNull Location location) {
        lat = location.getLatitude();
        lng = location.getLongitude();

        System.out.print("어케나오노?");
        System.out.print(lat);
        System.out.print(lng);

        logView.setText("latitude: " + lat + ", longitude: " + lng);
    }   //gps2

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    try {
                        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
                    } catch (SecurityException e) {
                        Log.d(TAG, "SecurityException: permission required");
                    }

                } else {
                }
                return;
            }

        }
    }   //gps2

    @Override
    protected void onPause() {
        super.onPause();

        lm.removeUpdates(this);
    }   //gps2

    public void onStatusChanged(String provider, int status, Bundle extras) {

    }   //gps2

    public void onProviderEnabled(String provider) {

    }   //gps2

    public void onProviderDisabled(String provider) {

    }   //gps2

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
//            startActivityForResult(takePictureIntent, 2);
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();     //여기부터 아래 3줄이 원본 if바로 밑
            imageBitmap = (Bitmap) extras.get("data");  //Bitmap = 이미지를 인코딩 //얘네는 String 형태가 아님!
            imageView.setImageBitmap(imageBitmap);      //이미지 띄우기.    //이거로 하면 화질 많이 안좋음

//            FirebaseVisionImage image;
            //                image = FirebaseVisionImage.fromFilePath(getApplicationContext(), data.getData());
            FirebaseVisionImage firebaseVisionImage = FirebaseVisionImage.fromBitmap(imageBitmap);
            FirebaseVisionImageLabeler labeler = FirebaseVision.getInstance()
                    .getOnDeviceImageLabeler();
            labeler.processImage(firebaseVisionImage)
                    .addOnSuccessListener(new OnSuccessListener<List<FirebaseVisionImageLabel>>() {
                        @Override
                        public void onSuccess(List<FirebaseVisionImageLabel> labels) {
                            for (FirebaseVisionImageLabel label : labels) {
                                String text = label.getText();
                                String entityId = label.getEntityId();
                                float confidence = label.getConfidence();
                                resultTv.append(text + "    " + confidence + "\n");
                                list.add(text);             //텍스트부분 배열에 넣기
                                System.out.println(text);

                            }
                            System.out.println(list);
                            
                            for(int i = 0; i<list.size(); i++){
                                if(keyword.equals((String)list.get(i))){
                                    Toast myToast = Toast.makeText(ImageLabellingActivity.this.getApplicationContext(),"검증에 성공하셨습니다", Toast.LENGTH_SHORT);
                                    long now = System.currentTimeMillis();  //현재시간
                                    Date date = new Date(now);              //Date로 형변환
                                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");       //원하는 시간형식으로 변환

                                    String finished_date = dateFormat.format(date);

                                    String rating = "5";    //test용
                                    
                                    mDatabaseRef.child(firebaseUser.getUid()).child(quest_num).child("finished_date").setValue(finished_date);
                                    mDatabaseRef.child(firebaseUser.getUid()).child(quest_num).child("rating").setValue(rating);

                                    System.out.println("종료 시간:"+date);

                                    myToast.show();

                                    //별점주기를 layout에 띄우기 위해 다른 방법을 사용해야함. activity를 새로 만들던가?
                                }
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                        }
                    });
        }
    }


}