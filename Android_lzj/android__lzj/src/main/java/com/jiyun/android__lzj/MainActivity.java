package com.jiyun.android__lzj;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.jiyun.android__lzj.adapter.VpAdapter;
import com.jiyun.android__lzj.fragment.HomeFragment;
import com.jiyun.android__lzj.fragment.WealFragment;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private TextView mTv;
    private Toolbar mTool;
    private ViewPager mVp;
    private TabLayout mTab;
    private NavigationView mNv;
    private ArrayList<Fragment> list;
    private VpAdapter adapter;
    private DrawerLayout mDl;

    //丁雅鑫  H1810A
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mDl = (DrawerLayout) findViewById(R.id.dl);
        mTv = (TextView) findViewById(R.id.tv);
        mTv.setText("资讯");
        mTool = (Toolbar) findViewById(R.id.tool);
        mTool.setTitle("");
        setSupportActionBar(mTool);
        /*ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDl, mTool, R.string.app_name, R.string.app_name);
        mDl.addDrawerListener(toggle);
        toggle.syncState();*/
        mTool.setNavigationIcon(R.drawable.ic_loading_rotate);
        mTool.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,Main3Activity.class));
            }
        });
        mVp = (ViewPager) findViewById(R.id.vp);
        mTab = (TabLayout) findViewById(R.id.tab);
        mNv = (NavigationView) findViewById(R.id.nv);
        list = new ArrayList<>();
        list.add(new HomeFragment());
        list.add(new WealFragment());
        adapter = new VpAdapter(getSupportFragmentManager(), list);
        mVp.setAdapter(adapter);
        mTab.addTab(mTab.newTab().setText("资讯").setIcon(R.drawable.selet1));
        mTab.addTab(mTab.newTab().setText("我的").setIcon(R.drawable.selet2));
        mVp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        mTv.setText("资讯");
                        break;
                    case 1:
                        mTv.setText("我的");
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mTab.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mVp));
        mVp.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTab));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(1,1,1,"上传图片");
        menu.add(2,2,2,"下载文件");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case 1:
                startActivity(new Intent(this,Main4Activity.class));
                break;
            case 2:
                startActivity(new Intent(this,Main5Activity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
