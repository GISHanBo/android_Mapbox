package com.example.myapplication;

import android.content.Context;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;


public class WebMapView {
    private WebView webView;
    private boolean hasMap;//是否已有地图

    public WebMapView(WebView webView) {
        this.webView = webView;
        initWebSetting();
    }

    /**
     * 初始化设置
     */
    private void initWebSetting() {
        hasMap = false;
        WebSettings webSettings = webView.getSettings();
        webSettings.setDefaultTextEncodingName("utf-8");
//        webSettings.setAllowFileAccessFromFileURLs(true);
//        webSettings.setAllowUniversalAccessFromFileURLs(true);
        webSettings.setBuiltInZoomControls(true);// 隐藏缩放按钮
        webSettings.setUseWideViewPort(true);// 可任意比例缩放
        webSettings.setLoadWithOverviewMode(true);// setUseWideViewPort方法设置webview推荐使用的窗口。setLoadWithOverviewMode方法是设置webview加载的页面的模式。
        webSettings.setJavaScriptEnabled(true);
        webView.addJavascriptInterface(new HmlInterfase(), "android");
        webSettings.setAppCacheEnabled(true);//缓存
        webSettings.setDomStorageEnabled(true);
        webSettings.setSupportMultipleWindows(true);// 新加
        webSettings.setUseWideViewPort(true); // 关键点
        webSettings.setAllowFileAccess(true); // 允许访问文件
        webSettings.setSupportZoom(true); // 支持缩放

        webView.setDrawingCacheEnabled(true);
        webView.buildDrawingCache();
        webView.buildLayer();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        webView.setWebViewClient(new MWebView());
    }

    /**
     * 加载HTML地图
     *
     * @param htmlName
     */
    public void loadMapHtml(String htmlName) {
        hasMap = true;
        webView.loadUrl("file:///android_asset/" + htmlName);
    }

    /**
     * 执行html中的方法
     *
     * @param method
     */
    public void loadHtmlMethod(String method) {
        if (!hasMap) {
            try {
                throw new NoMapException("未定义地图");
            } catch (NoMapException e) {
                e.printStackTrace();
            }
            return;
        }
        webView.loadUrl("javascript:" + method);
    }

    public class HmlInterfase {
        @JavascriptInterface
        public void report() {
            new AlertDialog.Builder(webView.getContext()).setTitle("警告").setMessage("不支持WebGL").show();
        }
    }
}
