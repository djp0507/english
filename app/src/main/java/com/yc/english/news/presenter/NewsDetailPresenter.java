package com.yc.english.news.presenter;

import android.content.Context;

import com.kk.securityhttp.domain.ResultInfo;
import com.yc.english.base.helper.ResultInfoHelper;
import com.yc.english.group.utils.EngineUtils;
import com.yc.english.main.hepler.UserInfoHelper;
import com.yc.english.news.bean.CourseInfoWrapper;
import com.yc.english.news.contract.NewsDetailContract;
import com.yc.english.weixin.model.engin.EnginHelper;

import rx.Subscriber;
import rx.Subscription;
import yc.com.base.BaseEngine;
import yc.com.base.BasePresenter;

/**
 * Created by wanglin  on 2017/9/7 10:27.
 */

public class NewsDetailPresenter extends BasePresenter<BaseEngine, NewsDetailContract.View> implements NewsDetailContract.Presenter {
    public NewsDetailPresenter(Context context, NewsDetailContract.View view) {
        super(context, view);
    }

    @Override
    public void loadData(boolean forceUpdate, boolean showLoadingUI) {

    }

    @Override
    public void getWeixinInfo(String news_id) {
        mView.showLoading();
        Subscription subscription = EnginHelper.getWeixinInfo(mContext, news_id).subscribe(new Subscriber<ResultInfo<CourseInfoWrapper>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                mView.showNoNet();
            }

            @Override
            public void onNext(final ResultInfo<CourseInfoWrapper> newsInfoWrapper) {
                ResultInfoHelper.handleResultInfo(newsInfoWrapper, new ResultInfoHelper.Callback() {
                    @Override
                    public void resultInfoEmpty(String message) {
                        mView.showNoNet();
                    }

                    @Override
                    public void resultInfoNotOk(String message) {
                        mView.showNoNet();
                    }

                    @Override
                    public void reulstInfoOk() {
                        if (newsInfoWrapper != null && newsInfoWrapper.data != null) {
                            mView.hide();
                            mView.showCourseResult(newsInfoWrapper.data);
                        } else {
                            mView.showNoData();
                        }
                    }
                });


            }
        });
        mSubscriptions.add(subscription);
    }


    @Override
    public void getWeiKeDetail(String news_id, String userId) {
        mView.showLoading();
        Subscription subscription = EnginHelper.getWeiKeDetail(mContext, news_id, userId).subscribe(new Subscriber<ResultInfo<CourseInfoWrapper>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                mView.showNoNet();
            }

            @Override
            public void onNext(final ResultInfo<CourseInfoWrapper> newsInfoWrapper) {
                ResultInfoHelper.handleResultInfo(newsInfoWrapper, new ResultInfoHelper.Callback() {
                    @Override
                    public void resultInfoEmpty(String message) {
                        mView.showNoNet();
                    }

                    @Override
                    public void resultInfoNotOk(String message) {
                        mView.showNoNet();
                    }

                    @Override
                    public void reulstInfoOk() {
                        if (newsInfoWrapper != null && newsInfoWrapper.data != null) {
                            mView.hide();
                            mView.showCourseResult(newsInfoWrapper.data);
                        } else {
                            mView.showNoData();
                        }
                    }
                });


            }
        });
        mSubscriptions.add(subscription);
    }

    public void statisticsStudyTotal(String news_id) {
        if (UserInfoHelper.getUserInfo() == null) {
            return;
        }
        Subscription subscription = EngineUtils.statisticsStudyTotal(mContext, UserInfoHelper.getUserInfo().getUid(), news_id).subscribe(new Subscriber<ResultInfo<String>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ResultInfo<String> stringResultInfo) {

            }
        });
        mSubscriptions.add(subscription);

    }
}
