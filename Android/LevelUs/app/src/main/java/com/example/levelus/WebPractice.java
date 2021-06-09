package com.example.levelus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebPractice extends AppCompatActivity {

    private WebView webView;
    private WebSettings webSettings;
    private String uid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_practice);


        webView = findViewById(R.id.webView);
        webView.setWebViewClient(new WebViewClient());

        Intent intent = getIntent();

        uid = intent.getExtras().getString("uid");


        webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        webView.loadUrl("http://3.35.9.194:5000/signup/"+uid);



        Intent GoToLoginActivity = new Intent(WebPractice.this, LoginActivity.class);
        GoToLoginActivity.putExtra("uid",uid);
        startActivity(GoToLoginActivity);

        finish();

    }
}