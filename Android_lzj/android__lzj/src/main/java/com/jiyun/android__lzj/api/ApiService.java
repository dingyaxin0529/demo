package com.jiyun.android__lzj.api;

import com.jiyun.android__lzj.bean.HomeBean;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface ApiService {

    public String Url="http://api.tianapi.com/";
    @GET("wxnew?key=52b7ec3471ac3bec6846577e79f20e4c&num=20&page=10")
    Observable<HomeBean>getList();
}
