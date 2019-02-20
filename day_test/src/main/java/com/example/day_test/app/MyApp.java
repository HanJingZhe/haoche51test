package com.example.day_test.app;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.tencent.android.tpush.XGIOperateCallback;
import com.tencent.android.tpush.XGPushConfig;
import com.tencent.android.tpush.XGPushManager;

/**
 * @author peng_wang
 * @date 2019/2/16
 */
public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        init();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    private void init() {
        //开启 debug日志
        XGPushConfig.enableDebug(this,true);

        //信鸽push
        XGPushConfig.enableOtherPush(getApplicationContext(), true);
        XGPushConfig.setHuaweiDebug(true);
        XGPushConfig.setMiPushAppId(getApplicationContext(), "9321fad2ee770");
        XGPushConfig.setMiPushAppKey(getApplicationContext(), "8451339f60fda1a4d46e2a57eb36374c");
        XGPushConfig.setMzPushAppId(this, "9321fad2ee770");
        XGPushConfig.setMzPushAppKey(this, "8451339f60fda1a4d46e2a57eb36374c");

        XGPushManager.registerPush(this, new XGIOperateCallback() {
            @Override
            public void onSuccess(Object data, int flag) {
                //token在设备卸载重装的时候有可能会变
                Log.d("TPush", "注册成功，设备token为：" + data);
            }
            @Override
            public void onFail(Object data, int errCode, String msg) {
                Log.d("TPush", "注册失败，错误码：" + errCode + ",错误信息：" + msg);
            }
        });

    }
}
