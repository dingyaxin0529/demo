package com.jiyun.android_lzj;

import com.jiyun.android_lzj.bean.HomeBean;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface ApiService {
    public String Url="http://gank.io/";
    @GET("api/data/%E7%A6%8F%E5%88%A9/20/2")
    Observable<HomeBean>getList();

    String getGetUrl = "http://cdn.banmi.com/banmiapp/apk/banmi_330.apk";



}
