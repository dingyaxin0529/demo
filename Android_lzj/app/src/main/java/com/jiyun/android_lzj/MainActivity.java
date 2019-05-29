package com.jiyun.android_lzj;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jiyun.android_lzj.fragment.HomeFragment;
import com.jiyun.android_lzj.fragment.WealFragment;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView mTbTitle;
    private Toolbar mToolbar;
    private LinearLayout mLinear;
    /**
     * 列表
     */
    private Button mBtn1;
    /**
     * 收藏
     */
    private Button mBtn2;
    private ArrayList<Fragment> list;
    private FragmentManager mManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initView();

    }

    private void initData() {
        list = new ArrayList<>();
        list.add(new HomeFragment());
        list.add(new WealFragment());
    }

    private void initView() {
        mTbTitle = (TextView) findViewById(R.id.tb_title);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mLinear = (LinearLayout) findViewById(R.id.linear);
        mBtn1 = (Button) findViewById(R.id.btn1);
        mBtn1.setOnClickListener(this);
        mBtn2 = (Button) findViewById(R.id.btn2);
        mBtn2.setOnClickListener(this);
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        mToolbar.setSubtitle("副标题");
        mToolbar.setNavigationIcon(R.mipmap.ic_launcher);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = mManager.beginTransaction();
        fragmentTransaction.replace(R.id.linear, list.get(0));
        fragmentTransaction.commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.btn1:
                mTbTitle.setText("A页面");
                FragmentTransaction ft1 = mManager.beginTransaction();
                ft1.replace(R.id.linear, list.get(0));
                ft1.commit();
                break;
            case R.id.btn2:
                mTbTitle.setText("B页面");
                FragmentTransaction ft2 = mManager.beginTransaction();
                ft2.replace(R.id.linear, list.get(1));
                ft2.commit();
                break;
        }
    }
}
