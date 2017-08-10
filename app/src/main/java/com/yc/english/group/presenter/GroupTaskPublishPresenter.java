package com.yc.english.group.presenter;

import android.content.Context;

import com.blankj.utilcode.util.LogUtils;
import com.kk.securityhttp.domain.ResultInfo;
import com.yc.english.base.presenter.BasePresenter;
import com.yc.english.group.contract.GroupTaskPublishContract;
import com.yc.english.group.model.bean.ClassInfoList;
import com.yc.english.group.model.bean.ClassInfoWarpper;
import com.yc.english.group.model.bean.TaskInfoWrapper;
import com.yc.english.group.model.bean.TaskUploadInfo;
import com.yc.english.group.model.engin.GroupTaskPublishEngine;
import com.yc.english.group.utils.EngineUtils;
import com.yc.english.main.hepler.UserInfoHelper;

import java.io.File;

import rx.Subscriber;
import rx.Subscription;

/**
 * Created by wanglin  on 2017/8/7 10:41.
 */

public class GroupTaskPublishPresenter extends BasePresenter<GroupTaskPublishEngine, GroupTaskPublishContract.View> implements GroupTaskPublishContract.Presenter {
    public GroupTaskPublishPresenter(Context context, GroupTaskPublishContract.View view) {
        super(context, view);
        mEngin = new GroupTaskPublishEngine(context);
    }

    @Override
    public void loadData(boolean forceUpdate, boolean showLoadingUI) {
        if (!forceUpdate) return;
        String uid = UserInfoHelper.getUserInfo().getUid();
        getGroupList(mContext, uid, "1");
    }

    @Override
    public void publishTask(String class_ids, String publisher, String desp, String imgesUrl, String voiceUrl, String docsUrl) {
        mView.showLoadingDialog("正在发布作业，请稍候！");
        Subscription subscription = mEngin.publishTask(class_ids, publisher, desp, imgesUrl, voiceUrl, docsUrl).subscribe(new Subscriber<ResultInfo<TaskInfoWrapper>>() {
            @Override
            public void onCompleted() {
                mView.dismissLoadingDialog();
            }

            @Override
            public void onError(Throwable e) {
                mView.dismissLoadingDialog();
                LogUtils.e("onError",e.getMessage());
            }

            @Override
            public void onNext(final ResultInfo<TaskInfoWrapper> taskInfoWrapperResultInfo) {
                handleResultInfo(taskInfoWrapperResultInfo, new Runnable() {
                    @Override
                    public void run() {
                        mView.showTaskDetail(taskInfoWrapperResultInfo.data.getInfo());
                    }
                });
            }
        });
        mSubscriptions.add(subscription);
    }

    @Override
    public void getGroupInfo(Context context, String id) {
        Subscription subscription = EngineUtils.queryGroupById(context, id, "").subscribe(new Subscriber<ResultInfo<ClassInfoWarpper>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(final ResultInfo<ClassInfoWarpper> classInfoWarpperResultInfo) {

                handleResultInfo(classInfoWarpperResultInfo, new Runnable() {
                    @Override
                    public void run() {
                        mView.showGroupInfo(classInfoWarpperResultInfo.data.getInfo());
                    }
                });

            }
        });
        mSubscriptions.add(subscription);
    }

    @Override
    public void uploadFile(File file,String fileName,String name) {
        Subscription subscription = mEngin.uploadFile(file,fileName,name).subscribe(new Subscriber<ResultInfo<TaskUploadInfo>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(final ResultInfo<TaskUploadInfo> taskUploadInfoResultInfo) {
                handleResultInfo(taskUploadInfoResultInfo, new Runnable() {
                    @Override
                    public void run() {
                        mView.showUploadReslut(taskUploadInfoResultInfo.data.getFile_path());
                    }
                });
            }
        });
        mSubscriptions.add(subscription);
    }

    private void getGroupList(Context context, String user_id, String is_admin) {
        Subscription subscription = EngineUtils.getMyGroupList(context, user_id, is_admin).subscribe(new Subscriber<ResultInfo<ClassInfoList>>() {
            @Override

            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(final ResultInfo<ClassInfoList> classInfoListResultInfo) {
                handleResultInfo(classInfoListResultInfo, new Runnable() {
                    @Override
                    public void run() {
                        mView.showMyGroupList(classInfoListResultInfo.data.getList());
                    }
                });
            }
        });
        mSubscriptions.add(subscription);
    }

}
