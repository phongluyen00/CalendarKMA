package com.example.retrofitrxjava;

import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Test123 extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        WebView webView;
        webView = (WebView)findViewById(R.id.help_webview);
        try {
            webView.setWebViewClient(new WebViewClient());
            webView.getSettings().setSupportZoom(true);
            webView.getSettings().setAllowContentAccess(true);
            webView.getSettings().setBuiltInZoomControls(true);
            webView.getSettings().setLoadWithOverviewMode(true);
            webView.getSettings().setUseWideViewPort(true);

            webView.loadUrl("http://actvn.edu.vn");
        } catch (Exception e) {
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
