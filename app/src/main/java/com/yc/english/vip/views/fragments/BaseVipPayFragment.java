package com.yc.english.vip.views.fragments;


import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.SizeUtils;
import com.jakewharton.rxbinding.view.RxView;
import com.yc.english.R;
import com.yc.english.base.view.BaseFragment;
import com.yc.english.vip.model.bean.GoodsType;
import com.yc.english.weixin.model.domain.CourseInfo;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import rx.functions.Action1;

/**
 * Created by wanglin  on 2017/11/27 14:46.
 */

public class BaseVipPayFragment extends BaseFragment {


    @BindView(R.id.baseItemView_ceping)
    BasePayItemView baseItemViewCeping;
    @BindView(R.id.tv_vip_three_month)
    TextView tvVipThreeMonth;
    @BindView(R.id.tv_vip_six_month)
    TextView tvVipSixMonth;
    @BindView(R.id.tv_vip_tween_month)
    TextView tvVipTweenMonth;
    @BindView(R.id.tv_vip_forever)
    TextView tvVipForever;
    @BindView(R.id.iv_vip_price)
    ImageView ivVipPrice;
    @BindView(R.id.vip_current_price)
    TextView vipCurrentPrice;
    @BindView(R.id.vip_price_unit)
    TextView vipPriceUnit;
    @BindView(R.id.tv_vip_original_price)
    TextView tvVipOriginalPrice;
    @BindView(R.id.ll_vip_ali)
    LinearLayout llVipAli;
    @BindView(R.id.ll_vip_wx)
    LinearLayout llVipWx;
    @BindView(R.id.ll_first_content)
    LinearLayout llFirstContent;
    @BindView(R.id.baseItemView_union)
    BasePayItemView baseItemViewUnion;
    @BindView(R.id.rootView)
    LinearLayout rootView;
    @BindView(R.id.ll_right_container)
    LinearLayout llRightContainer;
    @BindView(R.id.ll_month_container)
    LinearLayout llMonthContainer;
    @BindView(R.id.baseItemView_weike)
    BasePayItemView baseItemViewWeike;
    @BindView(R.id.baseItemView_teach)
    BasePayItemView baseItemViewTeach;

    private int mType;//1.提分辅导2.VIP会员3.单词购买
    private int generalType;//点读 微课
    private CourseInfo courserInfo;


    @Override
    public void init() {
        baseItemViewCeping.setVisibility(mType == 2 ? View.GONE : View.VISIBLE);

        if (mType == 3) {
            llFirstContent.setVisibility(View.GONE);
            baseItemViewUnion.setVisibility(View.GONE);
            llMonthContainer.setVisibility(View.GONE);
            if (getGeneralType() == GoodsType.GENERAL_TYPE_WEIKE) {
                baseItemViewCeping.setContentAndIcon("同步微课", 0);
            } else if (getGeneralType() == GoodsType.GENERAL_TYPE_DIANDU) {
                baseItemViewCeping.setContentAndIcon("教材点读", 0);
            } else if (getGeneralType() == GoodsType.GENERAL_TYPE_INDIVIDUALITY_PLAN) {
                baseItemViewCeping.setContentAndIcon("个性学习计划", 0);
            }

            LinearLayout.MarginLayoutParams layoutParams = (LinearLayout.MarginLayoutParams) llRightContainer.getLayoutParams();
            layoutParams.topMargin = SizeUtils.dp2px(15);

            llRightContainer.setLayoutParams(layoutParams);
            rootView.setGravity(Gravity.TOP);

            if (getCourserInfo() != null) {
                vipCurrentPrice.setText(String.valueOf(getCourserInfo().getMPrice()));
                tvVipOriginalPrice.setText(String.format(getString(R.string.original_price), String.valueOf(getCourserInfo().getPrice())));
            }
        }
        if (mType == 2) {
            baseItemViewWeike.setContentAndIcon("微课免费看", 0);
            baseItemViewTeach.setContentAndIcon("名师辅导课", R.mipmap.vip_common_teach);
        }
        setTextStyle(tvVipThreeMonth);
        llVipAli.setBackgroundResource(R.drawable.vip_item_select_time);
        tvVipOriginalPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);  // 设置中划线并加清晰

        initListener();

    }

    private void initListener() {
        RxView.clicks(tvVipThreeMonth).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                setTextStyle(tvVipThreeMonth);

            }
        });
        RxView.clicks(tvVipSixMonth).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {

                setTextStyle(tvVipSixMonth);

            }
        });
        RxView.clicks(tvVipTweenMonth).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                setTextStyle(tvVipTweenMonth);
            }
        });
        RxView.clicks(tvVipForever).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                setTextStyle(tvVipForever);
            }
        });
        RxView.clicks(llVipAli).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                llVipWx.setBackgroundResource(R.drawable.vip_item_unselect_time);
                llVipAli.setBackgroundResource(R.drawable.vip_item_select_time);
            }
        });
        RxView.clicks(llVipWx).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                llVipAli.setBackgroundResource(R.drawable.vip_item_unselect_time);
                llVipWx.setBackgroundResource(R.drawable.vip_item_select_time);
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.base_vip_right;
    }


    private void restoreTextViewBg() {

        tvVipThreeMonth.setBackgroundResource(R.drawable.vip_item_unselect_time);
        tvVipSixMonth.setBackgroundResource(R.drawable.vip_item_unselect_time);
        tvVipTweenMonth.setBackgroundResource(R.drawable.vip_item_unselect_time);
        tvVipForever.setBackgroundResource(R.drawable.vip_item_unselect_time);
        tvVipThreeMonth.setTextColor(ContextCompat.getColor(getActivity(), R.color.black_333));
        tvVipSixMonth.setTextColor(ContextCompat.getColor(getActivity(), R.color.black_333));
        tvVipTweenMonth.setTextColor(ContextCompat.getColor(getActivity(), R.color.black_333));
        tvVipForever.setTextColor(ContextCompat.getColor(getActivity(), R.color.black_333));

    }

    public void setType(int type) {
        this.mType = type;
    }

    private void setTextStyle(TextView tv) {
        restoreTextViewBg();
        tv.setBackgroundResource(R.drawable.vip_item_select_time);
        tv.setTextColor(ContextCompat.getColor(getActivity(), R.color.group_blue_21b5f8));
    }


    public void setGeneralType(int generalType) {
        this.generalType = generalType;
    }

    public int getGeneralType() {
        return generalType;
    }


    public void setCourserInfo(CourseInfo courserInfo) {
        this.courserInfo = courserInfo;
    }

    public CourseInfo getCourserInfo() {
        return courserInfo;
    }
}
