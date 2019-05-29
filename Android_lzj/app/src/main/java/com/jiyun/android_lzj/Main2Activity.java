package com.jiyun.android_lzj;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Main2Activity extends AppCompatActivity implements View.OnClickListener {

    /**
     * 上传
     */
    private Button mBtn;
    private TextView mTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        initView();
    }

    private void initView() {
        mBtn = (Button) findViewById(R.id.btn);
        mBtn.setOnClickListener(this);
        mTv = (TextView) findViewById(R.id.tv);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.btn:
                initData();
                break;
        }
    }

    private static final String TAG = "Main3Activity";
    private void initData() {
        OkHttpClient okHttpClient = new OkHttpClient();
        String filePath=Environment.getExternalStorageDirectory()+File.separator+"yy.png";
        File file=new File(filePath);
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/png"), file);
        MultipartBody build1 = new MultipartBody.Builder()
                .addFormDataPart("key","1810A")
                .addFormDataPart("file",file.getName(),requestBody)
                .setType(MultipartBody.FORM)
                .build();
        Request build = new Request.Builder()
                .url("http://yun918.cn/study/public/file_upload.php")
                .post(build1)
                .build();
        okHttpClient.newCall(build).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: "+e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String string = response.body().string();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mTv.setText(string);
                    }
                });
            }
        });
    }
}
