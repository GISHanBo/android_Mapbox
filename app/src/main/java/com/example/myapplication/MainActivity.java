package com.example.myapplication;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        grantPermission();
    }

    private void grantPermission() {
        //请求本地文件读写权限，
        //必须要求Internet权限
        //如果html放在本地，必须要有文件读写权限
        String[] permission = {
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.INTERNET};
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                    ActivityCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, permission, 1);
            } else {
                loadMapBox();
            }
        } else {
            new AlertDialog.Builder(MainActivity.this).setTitle("警告").setMessage("Android系统版本过低，可能无法显示").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    loadMapBox();
                }
            }).show();
        }
    }

    private void loadMapBox() {
        WebView webView = findViewById(R.id.mapView);
        WebMapView webMapView = new WebMapView(webView);
//        为了方便测试和打包将html放在了android assets文件夹中
        webMapView.loadMapHtml("index.html");
//                访问远程url地址
//        webMapView.loadUrl("http://gykj123.cn:9022/hnandroid/#/");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            boolean state = true;
            for (int i = 0; i < permissions.length; i++) {
                Log.i("MainActivity", "申请的权限为：" + permissions[i] + ",申请结果：" + grantResults[i]);
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    state = false;
                    break;
                }
            }
            if (state) {
                loadMapBox();
            } else {
                new AlertDialog.Builder(MainActivity.this).setTitle("警告").setMessage("授权失败，请自行给APP赋予权限").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       grantPermission();
                    }
                }).show();
            }
        }
    }
}
