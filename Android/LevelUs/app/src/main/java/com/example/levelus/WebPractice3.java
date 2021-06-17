package com.example.levelus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebPractice3 extends AppCompatActivity {


    private WebView webView3;
    private WebSettings webSettings;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_practice3);

        webView3 = findViewById(R.id.webView3);
        webView3.setWebViewClient(new WebViewClient());


        webSettings = webView3.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView3.loadUrl("http://3.35.9.194:5000/quest_category/");

        finish();
    }
}