package com.example.myapplication;

import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MWebView extends WebViewClient {
    @Override
    public void onPageFinished(WebView view, String url) {
        Log.e("WebView", "加载完成");
        super.onPageFinished(view, url);
    }
}
