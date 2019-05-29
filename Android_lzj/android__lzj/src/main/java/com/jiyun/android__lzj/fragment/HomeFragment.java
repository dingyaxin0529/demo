package com.jiyun.android__lzj.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jiyun.android__lzj.Main2Activity;
import com.jiyun.android__lzj.R;
import com.jiyun.android__lzj.adapter.HomeAdapter;
import com.jiyun.android__lzj.bean.HomeBean;
import com.jiyun.android__lzj.iview.IView;
import com.jiyun.android__lzj.persenter.PersenterImpl;
import com.youth.banner.Banner;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements IView, HomeAdapter.SetOnClick {

    private static final String TAG = "HomeFragment";
    private View view;
    private XRecyclerView mRv;
    private ArrayList<HomeBean.NewslistBean> list;
    private HomeAdapter adapter;
    private PersenterImpl persenter;
    private View view1;
    private Banner banner;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_home, container, false);
        initView(inflate);
        initData();
        return inflate;
    }

    private void initData() {
        persenter = new PersenterImpl(this);
        persenter.update();


    }

    private void initView(View inflate) {
        mRv = (XRecyclerView) inflate.findViewById(R.id.rv);
        mRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRv.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));
        list = new ArrayList<>();
        adapter = new HomeAdapter(list, getActivity());
        mRv.setAdapter(adapter);
        adapter.OnClick(this);
        mRv.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                list.clear();
                initData();
                mRv.refreshComplete();
            }

            @Override
            public void onLoadMore() {
                initData();
                mRv.loadMoreComplete();
            }
        });

        view1 = LayoutInflater.from(getActivity()).inflate(R.layout.layout_banner, null);
        mRv.addHeaderView(view1);
        banner = view1.findViewById(R.id.banner);

    }

    @Override
    public void updateUI(HomeBean bean) {
        list.addAll(bean.getNewslist());
        adapter.setList(list);
        adapter.notifyDataSetChanged();
        banner.setImages(list).setImageLoader(new ImageLoader() {
            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {
                HomeBean.NewslistBean bean= (HomeBean.NewslistBean) path;
                Glide.with(getActivity()).load(bean.getPicUrl()).into(imageView);
            }
        }).start();
        Log.d("aa", "updateUI: "+bean.getNewslist());
    }

    @Override
    public void updateError(String result) {
        Log.d(TAG, "updateError: "+result);
    }

    @Override
    public void SetOn(int position, HomeBean.NewslistBean bean) {
        Intent intent = new Intent(getActivity(),Main2Activity.class);
        intent.putExtra("url",bean.getPicUrl());
        startActivity(intent);
    }
}
