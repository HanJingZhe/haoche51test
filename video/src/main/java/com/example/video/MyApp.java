package com.example.video;

import android.app.Application;

/**
 * @author 王鹏
 * @date 2018/12/14
 */
public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AgoraManager.getInstance().init(this);
    }
}
