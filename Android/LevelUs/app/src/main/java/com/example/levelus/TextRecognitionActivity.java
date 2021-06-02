//package com.example.levelus;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.app.ActivityCompat;
//import androidx.core.content.ContextCompat;
//import androidx.core.content.FileProvider;
//
//import android.Manifest;
//import android.content.ActivityNotFoundException;
//import android.content.Context;
//import android.content.Intent;
//import android.content.pm.PackageManager;
//import android.graphics.Bitmap;
//import android.graphics.ImageDecoder;
//import android.location.Geocoder;
//import android.location.Location;
//import android.location.LocationListener;
//import android.location.LocationManager;
//import android.media.ExifInterface;
//import android.net.Uri;
//import android.os.Build;
//import android.os.Bundle;
//import android.os.Environment;
//import android.provider.MediaStore;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.google.android.gms.tasks.OnFailureListener;
//import com.google.android.gms.tasks.OnSuccessListener;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.ml.vision.FirebaseVision;
//import com.google.firebase.ml.vision.common.FirebaseVisionImage;
//import com.google.firebase.ml.vision.text.FirebaseVisionText;
//import com.google.firebase.ml.vision.text.FirebaseVisionTextDetector;
//
//import java.io.File;
//import java.io.IOException;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.List;
//
//public class TextRecognitionActivity extends AppCompatActivity implements LocationListener {
//
//    private Button captureImageBtn, detectTextBtn;  //사진찍기, 텍스트 추출하기
//    private ImageView imageView;                    //찍은 이미지
//    private TextView textView;                      //찍은 이미지의 텍스트
//    static final int REQUEST_IMAGE_CAPTURE = 1;     //찍은 사진 1장의 의미 인가?
//
//    //인코딩 방식이 2가지라 firebase가 인식하는 인코딩 변수는 imageBitmap임
//    private Bitmap imageBitmap;                     //인코딩된 이미지를 담을 변수.
//    private Bitmap bitmap;                          //인코딩된 이미지를 담을 변수2
//
//    FirebaseDatabase database;      //firebase db 연동 부분
//    DatabaseReference myRef;
//
//    //gps시도하기 이건 실패!
//    private boolean valid = false;
//    private Float latitude, longitude;
//    Geocoder geocoder;
//    String currentPhotoPath;    //이미지 파일 경로
//    String imageFileName;       //이미지 파일 이름 jpg앞부분
//
//    //gps관련 2번째(사진에서 말고 현재 위치 가져오는 방식임!)
//    final static String TAG = "MSP03";
//    final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
//    TextView logView;
//    TextView gps;
//    TextView network;
//    LocationManager lm;
//    double lat;
//    double lng;
//    double nowLat;
//    double nowLng;
//
//    TextView location2;
//
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_text_recognition);
//
//        logView = (TextView)findViewById(R.id.location);    //gps2
//        gps = (TextView)findViewById(R.id.gps);
//        network = (TextView)findViewById(R.id.network);
//
//        location2 = (TextView)findViewById(R.id.location2);
//
//        // LocationManager 참조 객체
//        lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
//
//        // GPS 프로바이더 사용 가능 여부
//        if (lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
//            gps.setText("GPS Provider: Available");
//        }
//
//        // 네트워크 프로바이더 사용 가능 여부
//        if (lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
//            network.setText("Network Provider: Available");
//        }
//
//        //해당 id 대응하기
//        captureImageBtn = findViewById(R.id.capture_image_btn);
//        detectTextBtn = findViewById(R.id.detect_text_image_btn);
//        imageView = findViewById(R.id.image_view);
//        textView = findViewById(R.id.text_display);
//
//        captureImageBtn.setOnClickListener(new View.OnClickListener() { //검증사진촬영 버튼 누를시
//            @Override
//            public void onClick(View v) {
//
//                dispatchTakePictureIntent();
//                nowLat = lat;
//                nowLng = lng;
//                location2.setText("latitude: " + nowLat + ", longitude: " + nowLng);
//
//                textView.setText("'텍스트 인식하기'를 누르시오");
//            }
//        });
//
//        detectTextBtn.setOnClickListener(new View.OnClickListener() {  //텍스트 인식하기 버튼 누를시
//            @Override
//            public void onClick(View v) {
//                detectTextFromImage();
//
//            }
//
//        });
//
//    }
//
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//
//        //*******************************************************************
//        // Runtime permission check
//        //*******************************************************************
//        if (ContextCompat.checkSelfPermission(TextRecognitionActivity.this,
//                Manifest.permission.ACCESS_FINE_LOCATION)
//                != PackageManager.PERMISSION_GRANTED) {
//
//            // Should we show an explanation?
//            if (ActivityCompat.shouldShowRequestPermissionRationale(TextRecognitionActivity.this,
//                    Manifest.permission.ACCESS_FINE_LOCATION)) {
//
//                // Show an expanation to the user *asynchronously* -- don't block
//                // this thread waiting for the user's response! After the user
//                // sees the explanation, try again to request the permission.
//
//            } else {
//
//                // No explanation needed, we can request the permission.
//
//                ActivityCompat.requestPermissions(TextRecognitionActivity.this,
//                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
//                        MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
//            }
//        } else {
//            // ACCESS_FINE_LOCATION 권한이 있는 것이므로
//            // location updates 요청을 할 수 있다.
//
//            // GPS provider를 이용
//            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
//        }
//        //*********************************************************************
//    }   //gps2
//
//    @Override
//    public void onLocationChanged(@NonNull Location location) {
//        lat = location.getLatitude();
//        lng = location.getLongitude();
//
//        if(lat == 0){
//            captureImageBtn.setEnabled(false);  //캡쳐버튼 비활성
//        }
//        if(lat != 0){
//            captureImageBtn.setEnabled(true);   //캡쳐 버튼 활성
//        }
//
//        logView.setText("latitude: "+ lat +", longitude: "+ lng);
//    }   //gps2
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode,
//                                           String permissions[], int[] grantResults) {
//        switch (requestCode) {
//            case MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
//                // If request is cancelled, the result arrays are empty.
//                if (grantResults.length > 0
//                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//
//                    // permission was granted, yay! Do the
//                    // read_external_storage-related task you need to do.
//
//                    // ACCESS_FINE_LOCATION 권한을 얻었으므로
//                    // 관련 작업을 수행할 수 있다
//                    try {
//                        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
//                    } catch(SecurityException e) {
//                        Log.d(TAG, "SecurityException: permission required");
//                    }
//
//                } else {
//
//                    // permission denied, boo! Disable the
//                    // functionality that depends on this permission.
//
//                    // 권한을 얻지 못 하였으므로 location 요청 작업을 수행할 수 없다
//                    // 적절히 대처한다
//
//                }
//                return;
//            }
//
//            // other 'case' lines to check for other
//            // permissions this app might request
//        }
//    }   //gps2
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//
//        lm.removeUpdates(this);
//    }   //gps2
//
//    public void onStatusChanged(String provider, int status, Bundle extras) {
//
//    }   //gps2
//
//    public void onProviderEnabled(String provider) {
//
//    }   //gps2
//
//    public void onProviderDisabled(String provider) {
//
//    }   //gps2
//
//
//    private void dispatchTakePictureIntent() {
//        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//
//        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
//            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);   //이걸 위로올리면 텍스트인식만 되고, 아래로 내리면 이미지 인식만됨
//
//        }
//    }
//
//
//
//
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
//            Bundle extras = data.getExtras();     //여기부터 아래 3줄이 원본 if바로 밑
//            imageBitmap = (Bitmap) extras.get("data");  //Bitmap = 이미지를 인코딩 //얘네는 String 형태가 아님!
//            imageView.setImageBitmap(imageBitmap);      //이미지 띄우기.    //이거로 하면 화질 많이 안좋음
//
//        }
//    }
//
//    private void detectTextFromImage() {
//        FirebaseVisionImage firebaseVisionImage = FirebaseVisionImage.fromBitmap(imageBitmap);  //여기서 에러가 난다.
//        FirebaseVisionTextDetector firebaseVisionTextDetector = FirebaseVision.getInstance().getVisionTextDetector();
//        firebaseVisionTextDetector.detectInImage(firebaseVisionImage).addOnSuccessListener(new OnSuccessListener<FirebaseVisionText>() {
//            @Override
//            public void onSuccess(FirebaseVisionText firebaseVisionText) {
//                displayTextFromImage(firebaseVisionText);
//            }
//
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Toast.makeText(TextRecognitionActivity.this, "error : " + e.getMessage(), Toast.LENGTH_SHORT).show();
//
//                Log.d("error: ", e.getMessage());
//            }
//        });
//    }
//
//    private void displayTextFromImage(FirebaseVisionText firebaseVisionText) {
//        List<FirebaseVisionText.Block> blockList = firebaseVisionText.getBlocks();
//        if (blockList.size() == 0) {
//            Toast.makeText(this, "no Text found in image.", Toast.LENGTH_SHORT);  //이미지에 텍스트가 없을경우 표시
//        } else {
//            for (FirebaseVisionText.Block block : firebaseVisionText.getBlocks()) {
//                String text = block.getText();      //이 text가 이미지에서 인식하는 텍스트임. 이 데이터를 가공하면 될듯?
//                textView.setText(text);             //텍스트 띄우기.
//
//
//
//            }
//        }
//
////    @Override
////    protected void onCreate(Bundle savedInstanceState) {
////        super.onCreate(savedInstanceState);
////        setContentView(R.layout.activity_text_recognition);
//    }
//}