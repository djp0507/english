package com.yc.english.vip.contract;

import com.yc.english.base.presenter.IPresenter;
import com.yc.english.base.view.IDialog;
import com.yc.english.base.view.INoNet;
import com.yc.english.base.view.IView;
import com.yc.english.pay.alipay.OrderInfo;

/**
 * Created by wanglin  on 2017/12/1 14:42.
 */

public interface VipBuyContract {

    interface View extends IView,IDialog,INoNet {

        void showOrderInfo(OrderInfo data, String s, String money);
        void shareAllow();
    }

    interface Presenter extends IPresenter {
    }
}
