package com.example.testdownload;

import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.azhon.appupdate.manager.DownloadManager;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private String TAG = "王鹏";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.tv_upload).setOnClickListener(this);
        findViewById(R.id.button).setOnClickListener(this);
        findViewById(R.id.button2).setOnClickListener(this);
        findViewById(R.id.button3).setOnClickListener(this);
        findViewById(R.id.button4).setOnClickListener(this);
        findViewById(R.id.button5).setOnClickListener(this);
        findViewById(R.id.button6).setOnClickListener(this);
        findViewById(R.id.button7).setOnClickListener(this);
        findViewById(R.id.button8).setOnClickListener(this);
        findViewById(R.id.button9).setOnClickListener(this);
        findViewById(R.id.button10).setOnClickListener(this);
        findViewById(R.id.button11).setOnClickListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_upload:
                upload();
                break;
            case R.id.button:
                cGetFilesDir();
                break;
            case R.id.button2:
                cGetFilesDir();
                break;
            case R.id.button3:
                cGetFilesDir();
                break;
            case R.id.button4:
                cGetFilesDir();
                break;
            case R.id.button5:
                cGetFilesDir();
                break;
            case R.id.button6:
                cGetFilesDir();
                break;
            case R.id.button7:
                cGetFilesDir();
                break;
            case R.id.button8:
                cGetFilesDir();
                break;
            case R.id.button9:
                cGetFilesDir();
                break;
            case R.id.button10:
                cGetFilesDir();
                break;
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void cGetFilesDir() {
        Log.d(TAG, "cGetFilesDir: " + getFilesDir().toString());
        Log.d(TAG, "cGetFilesDir: " + getCacheDir().toString());
        Log.d(TAG, "cGetFilesDir: " + getExternalCacheDir().toString());
        //Log.d(TAG, "cGetFilesDir: "+getExternalFilesDir().toString());
        Log.d(TAG, "cGetFilesDir: " + getObbDir().toString());
        Log.d(TAG, "cGetFilesDir: " + getNoBackupFilesDir().toString());
        Log.d(TAG, "cGetFilesDir: " + getCodeCacheDir().toString());
        Log.d(TAG, "cGetFilesDir: " + getPackageCodePath().toString());
        Log.d(TAG, "cGetFilesDir: " + getPackageResourcePath().toString());


       /* Log.d(TAG, "cGetFilesDir: "+getCacheDir().toString());
        Log.d(TAG, "cGetFilesDir: "+getCacheDir().toString());
        Log.d(TAG, "cGetFilesDir: "+getCacheDir().toString());
        Log.d(TAG, "cGetFilesDir: "+getCacheDir().toString());
        Log.d(TAG, "cGetFilesDir: "+getCacheDir().toString());
        Log.d(TAG, "cGetFilesDir: "+getCacheDir().toString());
        Log.d(TAG, "cGetFilesDir: "+getCacheDir().toString());
        Log.d(TAG, "cGetFilesDir: "+getCacheDir().toString());
        Log.d(TAG, "cGetFilesDir: "+getCacheDir().toString());
        Log.d(TAG, "cGetFilesDir: "+getCacheDir().toString());*/
        Environment.getExternalStorageState();//获取存储状态
        Environment.getDataDirectory();//： /data
        Environment.getDownloadCacheDirectory();//： /cache
        Environment.getRootDirectory();//： /system
        Environment.getExternalStorageDirectory();

    }

    private void upload() {
        DownloadManager mManager = DownloadManager.getInstance(this);
        mManager.setApkName("qulianwu.apk")
                .setApkUrl("https://raw.githubusercontent.com/azhon/AppUpdate/master/apk/appupdate.apk")
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .download();

    }
}
