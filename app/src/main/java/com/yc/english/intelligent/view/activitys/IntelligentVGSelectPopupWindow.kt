package com.yc.english.intelligent.view.activitys

import android.app.Activity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import butterknife.BindView
import com.alibaba.fastjson.JSON
import com.hwangjr.rxbus.RxBus
import com.jakewharton.rxbinding.view.RxView
import com.kk.securityhttp.net.contains.HttpConfig
import com.yc.english.EnglishApp
import com.yc.english.R
import com.yc.english.base.view.BasePopupWindow
import com.yc.english.intelligent.model.domain.VGInfoWarpper
import com.yc.english.intelligent.model.engin.IntelligentTypeEngin
import com.yc.english.intelligent.view.adpaters.IntelligentVGAdpater
import com.yc.english.main.model.domain.Constant
import yc.com.blankj.utilcode.util.SPUtils
import java.util.concurrent.TimeUnit


/**
 * Created by zhangkai on 2017/11/28.
 */
class IntelligentVGSelectPopupWindow(context: Activity) : BasePopupWindow(context) {
    override fun getAnimationID() = 0
    override fun getLayoutId() = R.layout.intelligent_ppw_vg_select

    @BindView(R.id.rv_version)
    lateinit var mVersionRecyclerView: RecyclerView
    @BindView(R.id.rv_grade)
    lateinit var mGradeRecyclerView: RecyclerView


    lateinit var mVersionAdapter: IntelligentVGAdpater
    lateinit var mGradeAdapter: IntelligentVGAdpater
    lateinit var typeEngin: IntelligentTypeEngin


    override fun init() {
        typeEngin = IntelligentTypeEngin(mContext)

        mVersionAdapter = IntelligentVGAdpater()
        mVersionAdapter.defaultInfo = JSON.parseObject(SPUtils.getInstance().getString(Constant.DEFAULT_VERSION_KEY, ""),
                VGInfoWarpper.VGInfo::class.java)
        mVersionRecyclerView.adapter = mVersionAdapter
        mVersionRecyclerView.layoutManager = GridLayoutManager(mContext, 3)


        mGradeAdapter = IntelligentVGAdpater()
        mGradeAdapter.defaultInfo = JSON.parseObject(SPUtils.getInstance().getString(Constant.DEFAULT_GRADE_KEY, ""),
                VGInfoWarpper.VGInfo::class.java)
        mGradeRecyclerView.adapter = mGradeAdapter
        mGradeRecyclerView.layoutManager = GridLayoutManager(mContext, 3)

        mVersionAdapter.setOnItemClickListener { adapter, view, position ->

            val vgInfo = mVersionAdapter.data.get(position)
            if ((mVersionAdapter.defaultInfo?.id ?: 0) == vgInfo.id) {
                return@setOnItemClickListener
            }
            SPUtils.getInstance().put(Constant.DEFAULT_VERSION_KEY, JSON.toJSONString(vgInfo))
            if (vgInfo.name!!.contains("PEP")) {
                SPUtils.getInstance().put("period", "0")
            } else {
                SPUtils.getInstance().put("period", "1")
            }
            getGrade(vgInfo)
            mVersionAdapter.defaultInfo = vgInfo
            adapter.notifyDataSetChanged()
        }


        mGradeAdapter.setOnItemClickListener { adapter, view, position ->
            val vgInfo = mGradeAdapter.data.get(position)
            if ((mGradeAdapter.defaultInfo?.id ?: 0) == vgInfo.id) {
                return@setOnItemClickListener
            }
            mGradeAdapter.defaultInfo = vgInfo
            SPUtils.getInstance().put(Constant.DEFAULT_GRADE_KEY, JSON.toJSONString(vgInfo))
            adapter.notifyDataSetChanged()
            RxBus.get().post(Constant.GET_UNIT, "from select")
            SPUtils.getInstance().remove(Constant.INTELLIGENT_POS)
            dismiss()
        }

        RxView.clicks(contentView).throttleFirst(200, TimeUnit
                .MILLISECONDS).subscribe {
            dismiss()
        }


        getVersionInfo()


    }


    fun getVersionInfo() {
        typeEngin.getVersion().subscribe {
            val code = it?.code ?: -1
            if (code == HttpConfig.STATUS_OK) {
                if (mVersionAdapter.defaultInfo == null) {
                    getGrade(it.data?.list?.get(0)!!)
                    SPUtils.getInstance().put(Constant.DEFAULT_VERSION_KEY, JSON.toJSONString(it.data?.list?.get(0)!!))
                } else {
                    getGrade(mVersionAdapter.defaultInfo!!)
                }
                it.data.list!!.map {
                    if (it.name!!.contains("PEP")) {
                        it.alias = "小学"
                    } else {
                        it.alias = "初中"

                    }
                }

                mVersionAdapter.setNewData(it.data?.list)
            }
        }
    }

    fun getGrade(vgInfo: VGInfoWarpper.VGInfo) {


        EnglishApp.get().setHttpDefaultParams()
        typeEngin.getGrade(vgInfo).subscribe {
            val code = it?.code ?: -1
            if (code == HttpConfig.STATUS_OK && it.data?.list != null && it.data?.list!!.size > 0) {
                mGradeAdapter.setNewData(it.data?.list)
            }
        }
    }
}