package com.jiyun.android_lzj;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownFileActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView mTv;
    private Toolbar mToolbar;
    /**
     *
     * 开始下载
     */
    private Button mBt;
    /**
     * TextView
     */
    private TextView mTextview;
    private ProgressBar mProgressBar;
    private File sd;
    private long mProgress;
    private NotificationManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_down_file);
        //通知
        manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        //注册eventbus
        EventBus.getDefault().register(this);
        initView();
        //下载的判断
        initPanduan();
    }

    private void initPanduan() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                PackageManager.PERMISSION_GRANTED) {
            openSd();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            openSd();
        }
    }

    private void openSd() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            sd = Environment.getExternalStorageDirectory();
        }
    }



    private void initView() {
        mTv = (TextView) findViewById(R.id.tv);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mBt = (Button) findViewById(R.id.bt);
        mBt.setOnClickListener(this);
        mTextview = (TextView) findViewById(R.id.textview);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mTv.setText("下载");
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.bt:
                //点击开始下载任务
                UPdata();
                break;
        }
    }

    //    http下载
    private void UPdata() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(ApiService.getGetUrl);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    int responseCode = con.getResponseCode();
                    if (responseCode == 200) {
                        InputStream inputStream = con.getInputStream();
                        int max = con.getContentLength();
                        xaizaiFile(inputStream, sd + "/" + "qimokaoshi.apk", max);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    //自定义eventbus  内部类
    public class ProgressEvent {
        public int progress;
    }

    private static final String TAG = "Main2Activity";
    //自定义方法，设置下载进度的百分比
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUpData(ProgressEvent progressEvent) {

        mTextview.setText("当前下载进度："+progressEvent.progress + "%");

        Log.d(TAG, "onUpData: 当前进度值为："+progressEvent.progress+"%");
    }

    //注销eventbus
    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }



    private void xaizaiFile(InputStream inputStream, String s, int max) {
        long count = 0;
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(new File(s));
            byte[] bytes = new byte[1024 * 10];
            int length;
            while ((length = inputStream.read(bytes)) != -1) {
                fileOutputStream.write(bytes, 0, length);
                count += length;
                mProgress = count * 100 / max;
                mProgressBar.setProgress((int) mProgress);
                //下载的大小的强转
                ProgressEvent progressEvent = new ProgressEvent();
                progressEvent.progress = (int) mProgress;
                //发送eventbus
                EventBus.getDefault().post(progressEvent);
            }
            inputStream.close();
            fileOutputStream.close();
            //切换线程，在下载完成时发送通知
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    fasongtongzhi();

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void fasongtongzhi() {
        String channelId = "5";
        String channelName = "liuzhaojie";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT);
            manager.createNotificationChannel(channel);
        }
        Intent intent = new Intent(DownFileActivity.this, DownFileActivity.class);
        PendingIntent activity = PendingIntent.getActivity(DownFileActivity.this, 100, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        Notification build = new NotificationCompat.Builder(DownFileActivity.this, channelId)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentText("下载完成")
                .setContentTitle("下载详情：")
                .setAutoCancel(true)
                .build();
        manager.notify(100, build);
    }
}
