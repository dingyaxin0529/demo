package com.jiyun.android_lzj.fragment;


import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jiyun.android_lzj.ApiService;
import com.jiyun.android_lzj.DownFileActivity;
import com.jiyun.android_lzj.Main2Activity;
import com.jiyun.android_lzj.MainActivity;
import com.jiyun.android_lzj.R;
import com.jiyun.android_lzj.adapter.HomeAdapter;
import com.jiyun.android_lzj.bean.HomeBean;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {


    private View view;
    private RecyclerView mRv;
    private ArrayList<HomeBean.ResultsBean> list = new ArrayList<>();
    private HomeAdapter adapter;
    private NotificationManager manager;
    private MyReceive mMyReceive;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_home, container, false);
        manager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
        initView(inflate);
        initData();
        return inflate;
    }

    private static final String TAG = "HomeFragment";
    private void initData() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiService.Url)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService apiService = retrofit.create(ApiService.class);
        Observable<HomeBean> observable = apiService.getList();
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<HomeBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(HomeBean homeBean) {


                        List<HomeBean.ResultsBean> results = homeBean.getResults();
                        list.addAll(results);
                        adapter.setList(HomeFragment.this.list);
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void onResume() {
        super.onResume();
        mMyReceive = new MyReceive();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("con.xiaotu");
        getActivity().registerReceiver(mMyReceive,intentFilter);
    }


    //自定义服务，将传值的position获取到，然后发送通知
    public class MyReceive extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            int position = intent.getIntExtra("position", 1);
            fasongtongzhi(position);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        getActivity().unregisterReceiver(mMyReceive);
    }

    private void fasongtongzhi(int position) {
        NotificationManager manager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
        String channelId="aaa";
        CharSequence channelName="sss";
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT);
            manager.createNotificationChannel(channel);
        }
        Intent intent=new Intent(getActivity(),Main2Activity.class);
        /*HomeBean.ResultsBean bean = new HomeBean.ResultsBean();
        intent.putExtra("aa",bean.getUrl());
        getActivity().startActivity(intent);*/
        PendingIntent activity = PendingIntent.getActivity(getContext(), 200, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        Notification build = new NotificationCompat.Builder(getContext(),channelId)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("上传")
                .setContentText("第"+position+"个子条目")
                .setDefaults(Notification.DEFAULT_ALL)
                .setContentIntent(activity)
                .setAutoCancel(true)
                .build();
        manager.notify(1,build);
    }
    private void initView(View inflate) {
        mRv = (RecyclerView) inflate.findViewById(R.id.rv);
        mRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRv.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));
        list = new ArrayList<>();
        adapter = new HomeAdapter(list, getActivity());
        mRv.setAdapter(adapter);
        adapter.OnClick(new HomeAdapter.SetOnClick() {
            @Override
            public void SetOn(int position, HomeBean.ResultsBean bean) {
                startActivity(new Intent(getActivity(),DownFileActivity.class));
            }
        });
        adapter.LongClick(new HomeAdapter.SetLongClick() {
            @Override
            public void SetLong(int position, HomeBean.ResultsBean bean) {

            }
        });
    }
}
