package com.jiyun.android__lzj.callback;

import com.jiyun.android__lzj.bean.HomeBean;

public interface CallBack {
    void updateSuccess(HomeBean bean);
    void updateFailed(String result);
}
