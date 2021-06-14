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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.label.FirebaseVisionImageLabel;
import com.google.firebase.ml.vision.label.FirebaseVisionImageLabeler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ImageLabellingActivity extends AppCompatActivity implements LocationListener {

    private Button captureImageBtn;                 //사진찍기
    private ImageView imageView;                    //찍은 이미지
    private TextView resultTv;                      //찍은 이미지의 텍스트
    static final int REQUEST_IMAGE_CAPTURE = 1;     //찍은 사진 1장의 의미 인가?
    Context context;

    private Bitmap imageBitmap; //인코딩된 이미지

    //gps관련 (사진에서 말고 현재 위치 가져오는 방식임!)
    final static String TAG = "MSP03";
    final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    TextView logView;   //처음 받아오는 현재위치
    LocationManager lm;

    TextView location2; //사진 찍으면 고정되는 현재위치

    double lat;         //처음 받아오는 좌표
    double lng;         //처음 받아오는 좌표
    double nowLat;      //사진 찍으면 고정되는 현재좌표
    double nowLng;      //사진 찍으면 고정되는 현재좌표
    TextView txt;       //주소반환 버튼 누를 시 반환되는 주소 값
    Button b1;          //주소반환 버튼

    //intent로 값 받아와야 함.
    String title_ko;
    String keyword;
    String way;
    String quest_num;

    //안드로이드 자체 db실패
//    SharedPreferences sharedPreferences= context.getSharedPreferences("test", MODE_PRIVATE);    // test 이름의 기본모드 설정, 만약 test key값이 있다면 해당 값을 불러옴.
//    String title_ko = sharedPreferences.getString("inputText","");




    //얘넨 테스트용
    ArrayList list = new ArrayList();

    String object = "object";
    String gps = "gps";
    String room = "Room";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_labelling);

        //gps관련
        logView = (TextView) findViewById(R.id.location);    //gps2

        location2 = (TextView) findViewById(R.id.location2);
        b1 = (Button) findViewById(R.id.b1);
        txt = (TextView)findViewById(R.id.txt);

        // LocationManager 참조 객체
        lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        //intent로 값 받아와야 함.
        Intent intent = getIntent();
        String title_ko = intent.getStringExtra("title_ko");
        String keyword = intent.getStringExtra("keyword");
        String way = intent.getStringExtra("way");
        String quest_num = intent.getStringExtra("quest_num");


        Geocoder g = new Geocoder(this);    //좌표 -> 주소
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Address> address=null;
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

                dispatchTakePictureIntent();
                nowLat = lat;
                nowLng = lng;
                location2.setText("latitude: " + nowLat + ", longitude: " + nowLng);
                System.out.println("이부분");
                System.out.println(title_ko);
                System.out.println(keyword);
                System.out.println(quest_num);
                System.out.println(way);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        //*******************************************************************
        // Runtime permission check
        //*******************************************************************
        if (ContextCompat.checkSelfPermission(ImageLabellingActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(ImageLabellingActivity.this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(ImageLabellingActivity.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
            }
        } else {
            // ACCESS_FINE_LOCATION 권한이 있는 것이므로
            // location updates 요청을 할 수 있다.

            // GPS provider를 이용
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        }
        //*********************************************************************
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

                    // permission was granted, yay! Do the
                    // read_external_storage-related task you need to do.

                    // ACCESS_FINE_LOCATION 권한을 얻었으므로
                    // 관련 작업을 수행할 수 있다
                    try {
                        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
                    } catch (SecurityException e) {
                        Log.d(TAG, "SecurityException: permission required");
                    }

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.

                    // 권한을 얻지 못 하였으므로 location 요청 작업을 수행할 수 없다
                    // 적절히 대처한다

                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
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
//                labeler.processImage(image)
                    .addOnSuccessListener(new OnSuccessListener<List<FirebaseVisionImageLabel>>() {
                        @Override
                        public void onSuccess(List<FirebaseVisionImageLabel> labels) {
                            // Task completed successfully
                            // ...
                            for (FirebaseVisionImageLabel label : labels) {
                                String text = label.getText();
                                String entityId = label.getEntityId();
                                float confidence = label.getConfidence();
                                resultTv.append(text + "    " + confidence + "\n");
                                list.add(text);
                                System.out.println(text);

                            }
                            System.out.println(list);
                            for(int i = 0; i<list.size(); i++){
                                if(room.equals((String)list.get(i))){
                                    Toast myToast = Toast.makeText(ImageLabellingActivity.this.getApplicationContext(),"검증에 성공하셨습니다", Toast.LENGTH_SHORT);
                                    myToast.show();
                                }
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // Task failed with an exception
                            // ...
                        }
                    });
        }
    }
}