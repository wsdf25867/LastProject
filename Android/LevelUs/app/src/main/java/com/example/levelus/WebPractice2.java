package com.example.levelus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebPractice2 extends AppCompatActivity {

    private String uid;
    private WebView webView2;
    private WebSettings webSettings;
    private WebView webView3;
    private WebSettings webSettings2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_practice2);

        webView2 = findViewById(R.id.webView2);
        webView2.setWebViewClient(new WebViewClient());

        Intent intent = getIntent();

        uid = intent.getExtras().getString("uid");

        webSettings = webView2.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView2.loadUrl("http://3.35.9.194:5000/refresh/"+uid);

        webView3 = findViewById(R.id.webView3);
        webView3.setWebViewClient(new WebViewClient());
        webSettings2 = webView3.getSettings();
        webSettings2.setJavaScriptEnabled(true);
        webView3.loadUrl("http://3.35.9.194:5000/quest_category/");

        finish();
    }
}