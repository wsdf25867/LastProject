package com.example.levelus;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import androidx.core.content.FileProvider;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.loader.content.CursorLoader;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


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
import android.net.Uri;
import android.os.Build;
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
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.label.FirebaseVisionImageLabel;
import com.google.firebase.ml.vision.label.FirebaseVisionImageLabeler;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

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
    static final int CAMERA_ACTION = 1;     //찍은 사진 1장의 의미 인가?
    static final int GET_FROM_GALLERY = 2;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    Context context;

    private Bitmap imageBitmap; //인코딩된 이미지
    private String mCurrentPhotoPath;

    //gps관련 (사진에서 말고 현재 위치 가져오는 방식임!)
    final static String TAG = "MSP03";
    final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    TextView logView;   //처음 받아오는 현재위치
    LocationManager lm;

            Uri checkedPhotoUri;
    String getUri;


    TextView location2; //사진 찍으면 고정되는 현재위치

    double lat;         //처음 받아오는 좌표
    double lng;         //처음 받아오는 좌표//
     double nowLat;      //사진 찍으면 고정되는 현재좌표
    double nowLng;      //사진 찍으면 고정되는 현재좌표
//    double nowLat = 41.5180;      //사진 찍으면 고정되는 현재좌표
//    double nowLng = 82.8516;      //사진 찍으면 고정되는 현재좌표
    TextView txt;       //주소반환 버튼 누를 시 반환되는 주소 값
    Button b1;          //주소반환 버튼

    //현재시간
    long now = System.currentTimeMillis();  //현재시간
    Date date = new Date(now);              //Date로 형변환
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");       //원하는 시간형식으로 변환

    String finished_date = dateFormat.format(date); //퀘스트 종료한 날짜

    //intent로 받아오는 값
    String quest_num;


    //db에서 받아오는 것들
    String keyword;
    String way;
    String difficulty;
    String done;
    String level;


    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference mDatabaseRef2 = firebaseDatabase.getReference("quest");
    private DatabaseReference mDatabaseRef = firebaseDatabase.getReference("quest_log");
    private DatabaseReference mDatabaseRef3 = firebaseDatabase.getReference("Level Us");
    private FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();
    private FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();
    private FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();

    private StorageReference storageReference = firebaseStorage.getReferenceFromUrl("gs://collabtest-71a4d.appspot.com");



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

        //difficulty받아오기
        mDatabaseRef2.child("ALL").child(quest_num).child("difficulty").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<DataSnapshot> task) {
                difficulty = String.valueOf(task.getResult().getValue());
                System.out.println("db에서 가져오는 difficulty : "+ difficulty);
            }
        });

        //done받아오기
        mDatabaseRef2.child("ALL").child(quest_num).child("done").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<DataSnapshot> task) {
                done = String.valueOf(task.getResult().getValue());
                System.out.println("db에서 가져오는 done : "+ done);
            }
        });

        //사용자 level받아오기
        mDatabaseRef3.child("UserAccount").child(firebaseUser.getUid()).child("level").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<DataSnapshot> task) {
                level = String.valueOf(task.getResult().getValue());
                System.out.println("db에서 가져오는 level : "+ level);
            }
        });



        Geocoder g = new Geocoder(this);    //좌표 -> 주소  //gps검증하기
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nowLat = lat;
                nowLng = lng;
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
                        String thoroughfare = address.get(0).getThoroughfare();
                        String feature = address.get(0).getFeatureName();
                        if(keyword.equals(countryName) || keyword.equals(admin)
                                || keyword.equals(sub_admin) || keyword.equals(locality)
                                || keyword.equals(thoroughfare) || keyword.equals((feature))){
                            Toast myToast = Toast.makeText(ImageLabellingActivity.this.getApplicationContext(),"검증에 성공하셨습니다", Toast.LENGTH_SHORT);
                            myToast.show();
                            rb.setVisibility(View.VISIBLE);
                            submit.setVisibility(View.VISIBLE);
                            //제출 버튼 클릭했을때
                            submit.setOnClickListener(new View.OnClickListener(){


                                @Override
                                public void onClick(View view) {
                                    Toast.makeText(ImageLabellingActivity.this, "제출하신 별점은 다음 퀘스트 추천의 기반이 됩니다~!",Toast.LENGTH_SHORT).show();
                                    String rating;
                                    rating = String.valueOf(rb.getRating());
                                    //별점부여
                                    mDatabaseRef.child(firebaseUser.getUid()).child(quest_num).child("rating").setValue(rating);
                                    //퀘스트 종료 날짜
                                    mDatabaseRef.child(firebaseUser.getUid()).child(quest_num).child("finished_date").setValue(finished_date);


                                    StorageReference checkedPhotoRef = storageReference.child(firebaseUser.getUid()+"/"+quest_num);
//                                    UploadTask uploadTask = checkedPhotoRef.putFile(checkedPhotoUri);
                                    UploadTask uploadTask = checkedPhotoRef.putBytes(imageBitmap.getNinePatchChunk());
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
                                        }
                                    });

                                    //done증가
                                    String realDone = String.valueOf(Integer.valueOf(done)+1);
                                    mDatabaseRef2.child("ALL").child(quest_num).child("done").setValue(realDone);

                                    //해당 난이도에 따른 레벨 증가
                                    String realLevel = String.valueOf(Integer.valueOf(level) + Integer.valueOf(difficulty));
                                    mDatabaseRef3.child("UserAccount").child(firebaseUser.getUid()).child("level").setValue(realLevel);


                                    Intent intent1 = new Intent(context, EditMyInfoFragment.class);
                                    startActivity(intent1);

                                    FragmentManager fragmentManager = getSupportFragmentManager();
                                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                    EditMyInfoFragment editMyInfoFragment = new EditMyInfoFragment();
                                    fragmentTransaction.replace(R.id.drawer_layout, editMyInfoFragment);
                                    fragmentTransaction.commit();

                                    finish();
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
                list.clear();   //인식된 객체가 담겨져 있는 배열 초기화(선언X)

                dispatchTakePictureIntent();

            }
        });
//        if(getUri!=null)
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
//            File photoFile = null;
//
//            try {
//                //임시로 사용할 파일이므로 경로는 캐시폴더로
//                File tempDir = getCacheDir();
//
//                //임시촬영파일 세팅
//                String timeStamp = new SimpleDateFormat("yyyyMMdd").format(new Date());
//                String imageFileName = "Capture_" + timeStamp + "_"; //ex) Capture_20201206_
//
//                File tempImage = File.createTempFile(
//                        imageFileName,  /* 파일이름 */
//                        ".jpg",         /* 파일형식 */
//                        tempDir      /* 경로 */
//                );
//
//                // ACTION_VIEW 인텐트를 사용할 경로 (임시파일의 경로)
//                mCurrentPhotoPath = tempImage.getAbsolutePath();
//
//                photoFile = tempImage;
//
//            } catch (IOException e) {
//                //에러 로그는 이렇게 관리하는 편이 좋다.
//                Log.w(TAG, "파일 생성 에러!", e);
//            }
//
//            //파일이 정상적으로 생성되었다면 계속 진행
//            if (photoFile != null) {
//                //Uri 가져오기
//                Uri photoURI = FileProvider.getUriForFile(this,
//                        getPackageName() + ".fileprovider",
//                        photoFile);
//                //인텐트에 Uri담기
//                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
//            startActivityForResult(takePictureIntent, 2); && data != null && data.getData() != null
                startActivityForResult(takePictureIntent, CAMERA_ACTION);
//            }
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
                        @RequiresApi(api = Build.VERSION_CODES.P)
                        @Override
                        public void onSuccess(List<FirebaseVisionImageLabel> labels) {
                            for (FirebaseVisionImageLabel label : labels) {
                                String text = label.getText();
                                String entityId = label.getEntityId();
                                float confidence = label.getConfidence();
                                resultTv.append(text + "    " + confidence + "\n");
                                list.add(text);             //인식된 객체 텍스트 배열에 넣기
                                System.out.println(text);

                            }
                            System.out.println(list);

                            for(int i = 0; i<list.size(); i++){     //배열이랑 비교하여 keyword랑 같을 경우
                                if(keyword.equals((String)list.get(i))){
                                    Toast myToast = Toast.makeText(ImageLabellingActivity.this.getApplicationContext(),"검증에 성공하셨습니다", Toast.LENGTH_SHORT);
                                    long now = System.currentTimeMillis();  //현재시간
                                    Date date = new Date(now);              //Date로 형변환
                                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");       //원하는 시간형식으로 변환

                                    String finished_date = dateFormat.format(date);

                                    myToast.show();

                                    RatingBar rb = requireViewById(R.id.rb);
                                    Button submit = requireViewById(R.id.submit);
                                    rb.setVisibility(View.VISIBLE);
                                    submit.setVisibility(View.VISIBLE);

                                    submit.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            Toast.makeText(ImageLabellingActivity.this.getApplicationContext(),"제출하신 별점은 다음 퀘스트 추천의 기반이 됩니다~!",Toast.LENGTH_SHORT).show();  //이게 안뜨네??
                                            String rating;
                                            rating = String.valueOf(rb.getRating());
                                            mDatabaseRef.child(firebaseUser.getUid()).child(quest_num).child("rating").setValue(rating);
                                            mDatabaseRef.child(firebaseUser.getUid()).child(quest_num).child("finished_date").setValue(finished_date);
                                            String realDone = String.valueOf(Integer.valueOf(done)+1);
                                            mDatabaseRef2.child("ALL").child(quest_num).child("done").setValue(realDone);
                                            String realLevel = String.valueOf(Integer.valueOf(level) + Integer.valueOf(difficulty));
                                            mDatabaseRef3.child("UserAccount").child(firebaseUser.getUid()).child("level").setValue(realLevel);

                                            FragmentManager fragmentManager = getSupportFragmentManager();
                                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                            EditMyInfoFragment editMyInfoFragment = new EditMyInfoFragment();
                                            fragmentTransaction.replace(R.id.drawer_layout, editMyInfoFragment);
                                            fragmentTransaction.commit();
                                            finish();


                                            Intent intent = new Intent(ImageLabellingActivity.this.getApplicationContext(), MainActivity.class);
                                            intent.addFlags(intent.FLAG_ACTIVITY_NEW_TASK);
                                            ImageLabellingActivity.this.getApplicationContext().startActivity(intent);
                                            finishAffinity();

                                        }
                                    });
                                    break;
                                }
                            }
                            Toast myToast = Toast.makeText(ImageLabellingActivity.this.getApplicationContext(),"객체를 인식하지 못했습니다. 사진을 다시 찍어 주세요.", Toast.LENGTH_SHORT);
                            myToast.show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                        }
                    });
        }
    }
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if(resultCode == RESULT_OK){
//            switch (requestCode){
//                case CAMERA_ACTION:
//                    getUri = data.getDataString();
//                    Log.d("Uri", getUri);
//                    checkedPhotoUri = data.getData();
//                    Bundle extras = data.getExtras();     //여기부터 아래 3줄이 원본 if바로 밑
//                    imageBitmap = (Bitmap) extras.get("data");  //Bitmap = 이미지를 인코딩 //얘네는 String 형태가 아님!
//                    imageView.setImageBitmap(imageBitmap);      //이미지 띄우기.    //이거로 하면 화질 많이 안좋음
//                    imageView.setImageURI(checkedPhotoUri);
////            FirebaseVisionImage image;
//                    //                image = FirebaseVisionImage.fromFilePath(getApplicationContext(), data.getData());
//                    FirebaseVisionImage firebaseVisionImage = FirebaseVisionImage.fromBitmap(imageBitmap);
//                    FirebaseVisionImageLabeler labeler = FirebaseVision.getInstance()
//                            .getOnDeviceImageLabeler();
//                    labeler.processImage(firebaseVisionImage)
//                            .addOnSuccessListener(new OnSuccessListener<List<FirebaseVisionImageLabel>>() {
//                                @RequiresApi(api = Build.VERSION_CODES.P)
//                                @Override
//                                public void onSuccess(List<FirebaseVisionImageLabel> labels) {
//                                    for (FirebaseVisionImageLabel label : labels) {
//                                        String text = label.getText();
//                                        String entityId = label.getEntityId();
//                                        float confidence = label.getConfidence();
//                                        resultTv.append(text + "    " + confidence + "\n");
//                                        list.add(text);             //인식된 객체 텍스트 배열에 넣기
//                                        System.out.println(text);
//
//                                    }
//                                    System.out.println(list);
//
//                                    for(int i = 0; i<list.size(); i++){     //배열이랑 비교하여 keyword랑 같을 경우
//                                        if(keyword.equals((String)list.get(i))){
//                                            Toast myToast = Toast.makeText(ImageLabellingActivity.this.getApplicationContext(),"검증에 성공하셨습니다", Toast.LENGTH_SHORT);
//                                            long now = System.currentTimeMillis();  //현재시간
//                                            Date date = new Date(now);              //Date로 형변환
//                                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");       //원하는 시간형식으로 변환
//
//                                            String finished_date = dateFormat.format(date);
//
//                                            myToast.show();
//
//                                            RatingBar rb = requireViewById(R.id.rb);
//                                            Button submit = requireViewById(R.id.submit);
//                                            rb.setVisibility(View.VISIBLE);
//                                            submit.setVisibility(View.VISIBLE);
//
//                                            submit.setOnClickListener(new View.OnClickListener(){
//                                                @Override
//                                                public void onClick(View view) {
//                                                    Toast.makeText(getApplicationContext(),"제출하신 별점은 다음 퀘스트 추천의 기반이 됩니다~!",Toast.LENGTH_SHORT).show();  //이게 안뜨네??
//                                                    String rating;
//                                                    rating = String.valueOf(rb.getRating());
//                                                    mDatabaseRef.child(firebaseUser.getUid()).child(quest_num).child("rating").setValue(rating);
//                                                    mDatabaseRef.child(firebaseUser.getUid()).child(quest_num).child("finished_date").setValue(finished_date);
//                                                    String realDone = String.valueOf(Integer.valueOf(done)+1);
//                                                    mDatabaseRef2.child("ALL").child(quest_num).child("done").setValue(realDone);
//                                                    String realLevel = String.valueOf(Integer.valueOf(level) + Integer.valueOf(difficulty));
//                                                    mDatabaseRef3.child("UserAccount").child(firebaseUser.getUid()).child("level").setValue(realLevel);
//                                                    Intent intent1 = new Intent(context, EditMyInfoFragment.class);
//                                                    startActivity(intent1);
//                                                    finish();
//                                                }
//                                            });
//                                            break;
//                                        }
//                                    }
//                                    Toast myToast = Toast.makeText(ImageLabellingActivity.this.getApplicationContext(),"객체를 인식하지 못했습니다. 사진을 다시 찍어 주세요.", Toast.LENGTH_SHORT);
//                                    myToast.show();
//                                }
//                            })
//                            .addOnFailureListener(new OnFailureListener() {
//                                @Override
//                                public void onFailure(@NonNull Exception e) {
//                                }
//                            });
//                    break;
//                case GET_FROM_GALLERY:
//                    if(data.getData()!=null){
//                        try{
//                            checkedPhotoUri = data.getData();
//                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), checkedPhotoUri);
////                            img1.setImageBitmap(bitmap);
//                        }catch (Exception e){
//                            e.printStackTrace();
//                        }
//                    }
//                    break;
//            }
//        }
//    }
//    public void galleryAddPic(){
//        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
//        File f = new File(mCurrentPhotoPath);
//        Uri contentUri = Uri.fromFile(f);
//        mediaScanIntent.setData(contentUri);
//        sendBroadcast(mediaScanIntent);
//        Toast.makeText(this,"사진이 저장되었습니다",Toast.LENGTH_SHORT).show();
//    }
//    public void selectAlbum(){
//        //앨범에서 이미지 가져옴
//        //앨범 열기
//        Intent intent = new Intent(Intent.ACTION_PICK);
//        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
//        intent.setType("image/*");
//        startActivityForResult(intent, GET_FROM_GALLERY);
//    }




}