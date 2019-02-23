package com.example.video;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class FunDanceRoomActivity extends AppCompatActivity {

    private PartyRoomLayout mPartyRoomLayout;

    private TextView mChannel;

    private ImageButton mEndCall;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fun_dance_room);

        mPartyRoomLayout = (PartyRoomLayout) findViewById(R.id.fd_party_room_layout);

        mChannel = (TextView) findViewById(R.id.fd_channel);
        String channel = getIntent().getStringExtra("Channel");
        mChannel.setText(channel);

        mEndCall = (ImageButton) findViewById(R.id.fd_end_call);
        mEndCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //AgoraManager里面封装了挂断的API, 退出频道
                AgoraManager.getInstance().leaveChannel();
                finish();
            }
        });

        //设置前置摄像头预览并开启
        AgoraManager.getInstance()
                .setupLocalVideo(getApplicationContext())
                .setOnPartyListener(mOnPartyListener)
                .joinChannel(channel)
                .startPreview();
        //将摄像头预览的SurfaceView加入PartyRoomLayout
        mPartyRoomLayout.addView(AgoraManager.getInstance().getLocalSurfaceView());

    }

    private AgoraManager.OnPartyListener mOnPartyListener = new AgoraManager.OnPartyListener() {
        @Override
        public void onJoinChannelSuccess(String channel, final int uid) {
        }

        @Override
        public void onGetRemoteVideo(final int uid) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //注意SurfaceView要创建在主线程
                    AgoraManager.getInstance().setupRemoteVideo(FunDanceRoomActivity.this, uid);
                    mPartyRoomLayout.addView(AgoraManager.getInstance().getSurfaceView(uid));
                }
            });
        }

        @Override
        public void onLeaveChannelSuccess() {
        }

        @Override
        public void onUserOffline(final int uid) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //从PartyRoomLayout移除远程视频的SurfaceView
                    mPartyRoomLayout.removeView(AgoraManager.getInstance().getSurfaceView(uid));
                    //清除缓存的SurfaceView
                    AgoraManager.getInstance().removeSurfaceView(uid);
                }
            });
        }
    };

    /**
     * 返回时退出频道
     */
    @Override
    public void onBackPressed() {
        AgoraManager.getInstance().leaveChannel();
        finish();
    }
}
