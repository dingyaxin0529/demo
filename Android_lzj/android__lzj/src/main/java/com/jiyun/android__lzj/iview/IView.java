package com.jiyun.android__lzj.iview;

import com.jiyun.android__lzj.bean.HomeBean;

public interface IView {
    void updateUI(HomeBean bean);
    void updateError(String result);
}
