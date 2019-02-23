package com.example.score;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int REQUEST_CODE_CHOOSE = 100; // 选择图片的请求码
    private VideoView videoView;
    private RecyclerView rvBm;
    private ImageView ivPausse;
    private View viewClick;
    private SeekBar seekBar;
    private TextView tvTime;

    private int max;

    private List<Bitmap> bitmaps = new ArrayList<>();
    private BmAdapter adapter;
    private LinearLayoutManager manager;

    private Handler mhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {//seekbar
                int currentPosition = videoView.getCurrentPosition();
                if (currentPosition <= max) {
                    seekBar.setProgress(currentPosition);
                    tvTime.setText(videoView.getCurrentPosition() / 1000 + ":" + max/1000);
                    mhandler.sendEmptyMessage(1);
                }
            } else {
                Bitmap bm = (Bitmap) msg.obj;
                bitmaps.add(bm);
                adapter.notifyDataSetChanged();
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ivPausse = findViewById(R.id.iv_pause);  //暂停时显示的图片
        viewClick = findViewById(R.id.view_click);//控制点击暂停的View
        seekBar = findViewById(R.id.seek_bar);  //播放进度seekbar
        videoView = findViewById(R.id.videoView);//播放器
        rvBm = findViewById(R.id.rv_bm);  //逐帧截图list
        tvTime = findViewById(R.id.tv_time);// 显示视频长度
        findViewById(R.id.tv_click).setOnClickListener(this);//点击选择视频源

        //动态权限
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 999);

        // 横向布局显示
        manager = new LinearLayoutManager(this, OrientationHelper.HORIZONTAL, false);
        rvBm.setLayoutManager(manager);
        adapter = new BmAdapter(R.layout.item, bitmaps);
        rvBm.setAdapter(adapter);

        addHeaderView();


        seekBar.setOnSeekBarChangeListener(seekBarListener);
        //rvBm.addOnScrollListener(scrollChangeListener);
    }

    private void addHeaderView() {
        View view1 = View.inflate(this, R.layout.air_layout, null);
        View view2 = View.inflate(this, R.layout.air_layout, null);
        adapter.addHeaderView(view1);
        adapter.addFooterView(view2);
    }

    @Override
    public void onClick(View v) {
        Matisse.from(MainActivity.this)
                //选择视频和图片
                .choose(MimeType.ofVideo())
                //是否只显示选择的类型的缩略图，就不会把所有图片视频都放在一起，而是需要什么展示什么
                .showSingleMediaType(true)
                .countable(true)
                //这两行要连用 是否在选择图片中展示照相 和适配安卓7.0 FileProvider
                //有序选择图片 123456...
                .countable(true)
                //最大选择数量为9
                //界面中缩略图的质量
                .thumbnailScale(0.8f)
                //蓝色主题
                .theme(R.style.Matisse_Zhihu)
                //Glide加载方式
                .imageEngine(new GlideEngine())
                //请求码
                .forResult(REQUEST_CODE_CHOOSE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
            //路径 同样视频地址也是这个 根据requestCode
            final Uri uri = Matisse.obtainResult(data).get(0);
            videoView.setVideoURI(uri);
            videoView.requestFocus();//让VideoView获取焦点
            videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    max = videoView.getDuration(); //视频毫秒值
                    seekBar.setMax(max);
                    tvTime.setText(0 + ":" + max/1000);
                    mhandler.sendEmptyMessage(1);//发送消息 控制seekbar
                }
            });
            videoView.start(); //开始播放
            //控制视频播放暂停
            viewClick.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (videoView.isPlaying()) {
                        videoView.pause();
                        ivPausse.setVisibility(View.VISIBLE);
                    } else {
                        videoView.start();
                        ivPausse.setVisibility(View.GONE);
                    }
                }
            });

            //启动线程
            ThreadRunPic threadRunPic = new ThreadRunPic(uri);
            threadRunPic.start();
        }
    }


    //old
    class Masync extends AsyncTask<String, Void, List<Bitmap>> {

        @Override
        protected List<Bitmap> doInBackground(String... strings) {
            List<Bitmap> bms = new ArrayList<>();
            int max = Integer.parseInt(strings[0]);
            Uri uri = Uri.parse(strings[1]);
            MediaMetadataRetriever retriever = new MediaMetadataRetriever();
            retriever.setDataSource(MainActivity.this, uri);

            //获取视频的宽
            String w = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH);
            //获取视频的高
            String h = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT);
            //获取视频的时长，ms
            String s = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);

            Log.i("王鹏", "w: " + w + "\nh:" + h + "\ns:" + s);// 视频规格1920*1080 时长62111  62秒


            for (int i = 1; i < max + 1; i++) {
                Bitmap frameAtTime = retriever.getFrameAtTime(i * 1000 * 1000, MediaMetadataRetriever.OPTION_CLOSEST_SYNC);
                bms.add(frameAtTime);
            }
            retriever.release();//释放资源
            return bms;
        }

        @Override
        protected void onPostExecute(List<Bitmap> bms) {
            super.onPostExecute(bms);
            bitmaps.clear();
            bitmaps.addAll(bms);
            adapter.notifyDataSetChanged();
        }
    }

    class BmAdapter extends BaseQuickAdapter<Bitmap, BaseViewHolder> {

        public BmAdapter(int layoutResId, @Nullable List<Bitmap> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, Bitmap item) {
            ImageView ivItem = helper.getView(R.id.iv_item);
            ivItem.setImageBitmap(item);
        }
    }

    // seekbar的滑动监听
    SeekBar.OnSeekBarChangeListener seekBarListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            if (fromUser) {
                videoView.seekTo(progress);
            } else {

            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };

    //recyclerview 滑动监听
    RecyclerView.OnScrollListener scrollChangeListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            seekBar.setProgress(dx);
        }
    };

    //线程取帧
    class ThreadRunPic extends Thread {
        MediaMetadataRetriever retriever;

        public ThreadRunPic(Uri uri) {
            retriever = new MediaMetadataRetriever();
            retriever.setDataSource(MainActivity.this, uri);
        }

        @Override
        public void run() {
            super.run();
            int endTime = Integer.parseInt(retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION));
            int l = endTime / 1000;
            Bitmap atTimeBm;
            for (int i = 1; i <= l; i++) {
                atTimeBm = retriever.getFrameAtTime(i * 1000 * 1000);
                Message obtain = Message.obtain();
                obtain.what = 2;
                obtain.obj = atTimeBm;
                mhandler.sendMessage(obtain);
            }
            retriever.release();//释放资源
        }
    }

}
