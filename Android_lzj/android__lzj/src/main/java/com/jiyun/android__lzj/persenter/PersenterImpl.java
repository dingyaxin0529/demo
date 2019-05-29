package com.jiyun.android__lzj.persenter;

import com.jiyun.android__lzj.bean.HomeBean;
import com.jiyun.android__lzj.callback.CallBack;
import com.jiyun.android__lzj.iview.IView;
import com.jiyun.android__lzj.model.Model;
import com.jiyun.android__lzj.model.Modelmpl;

public class PersenterImpl implements Persenter{

    Model model;
    IView iView;

    public PersenterImpl( IView iView) {
        this.model = new Modelmpl();
        this.iView = iView;
    }

    @Override
    public void update() {
        model.updateData(new CallBack() {
            @Override
            public void updateSuccess(HomeBean bean) {
                iView.updateUI(bean);
            }

            @Override
            public void updateFailed(String result) {
                iView.updateError(result);
            }
        });
    }
}
