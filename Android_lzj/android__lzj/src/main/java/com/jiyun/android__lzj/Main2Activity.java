package com.jiyun.android__lzj;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class Main2Activity extends AppCompatActivity {

    private WebView mWv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        initView();

    }

    private void initView() {
        mWv = (WebView) findViewById(R.id.wv);
        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        WebSettings settings = mWv.getSettings();
        settings.setJavaScriptEnabled(true);
        mWv.loadUrl(url);
        mWv.setWebViewClient(new WebViewClient());
        mWv.getSettings().setUseWideViewPort(true);
         mWv.getSettings().setSupportZoom(true);
        mWv.getSettings().setBuiltInZoomControls(true);
        mWv.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        mWv.getSettings().setLoadWithOverviewMode(true);
    }
}
