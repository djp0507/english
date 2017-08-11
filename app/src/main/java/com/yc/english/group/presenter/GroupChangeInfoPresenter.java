package com.yc.english.group.presenter;

import android.content.Context;
import android.text.TextUtils;

import com.kk.securityhttp.domain.ResultInfo;
import com.yc.english.base.presenter.BasePresenter;
import com.yc.english.group.contract.GroupChangeInfoContract;
import com.yc.english.group.model.bean.RemoveGroupInfo;
import com.yc.english.group.model.engin.GroupChangeInfoEngine;
import com.yc.english.group.rong.models.CodeSuccessResult;
import com.yc.english.group.rong.models.GroupInfo;
import com.yc.english.group.utils.EngineUtils;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by wanglin  on 2017/8/4 14:17.
 * 修改个人信息
 */

public class GroupChangeInfoPresenter extends BasePresenter<GroupChangeInfoEngine, GroupChangeInfoContract.View> implements GroupChangeInfoContract.Presenter {
    public GroupChangeInfoPresenter(Context context, GroupChangeInfoContract.View view) {
        super(context, view);
        mEngin = new GroupChangeInfoEngine(context);
    }

    @Override
    public void loadData(boolean forceUpdate, boolean showLoadingUI) {

    }


    @Override
    public void changeGroupInfo(Context context,String class_id, String name, String face, String vali_type) {
        if (!TextUtils.isEmpty(name)) mView.showLoadingDialog("正在修改班级名称，请稍候！");
        Subscription subscription = EngineUtils.changeGroupInfo(context,class_id, name, face, vali_type).subscribe(new Subscriber<ResultInfo<RemoveGroupInfo>>() {
            @Override
            public void onCompleted() {
                mView.dismissLoadingDialog();
            }

            @Override
            public void onError(Throwable e) {
                mView.dismissLoadingDialog();
            }

            @Override
            public void onNext(final ResultInfo<RemoveGroupInfo> stringResultInfo) {
                handleResultInfo(stringResultInfo, new Runnable() {
                    @Override
                    public void run() {
                        RemoveGroupInfo groupInfo = stringResultInfo.data;
                        mView.showChangeResult();

                    }
                });
            }
        });
        mSubscriptions.add(subscription);
    }


}