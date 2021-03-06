package com.yc.english.intelligent.view.activitys

import android.content.Intent
import android.os.Parcelable
import android.support.v7.widget.GridLayoutManager
import android.view.View
import com.alibaba.fastjson.JSON
import com.hwangjr.rxbus.RxBus
import com.jakewharton.rxbinding.view.RxView
import com.umeng.analytics.MobclickAgent
import com.yc.english.R
import com.yc.english.base.utils.SimpleCacheUtils
import com.yc.english.intelligent.contract.IntelligentHandInContract
import com.yc.english.intelligent.model.domain.QuestionInfoWrapper
import com.yc.english.intelligent.presenter.IntelligentHandInPresenter
import com.yc.english.intelligent.utils.getLevel1QuestionInfo
import com.yc.english.intelligent.view.adpaters.IntelligentHandInAdapter
import com.yc.english.main.model.domain.Constant
import kotlinx.android.synthetic.main.intelligent_avtivity_hand_in.*
import yc.com.base.BaseActivity
import yc.com.base.StatusBarCompat
import yc.com.blankj.utilcode.util.SPUtils
import yc.com.blankj.utilcode.util.ToastUtils
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * Created by zhangkai on 2017/11/28.
 */
class IntelligentHandInActivity : BaseActivity<IntelligentHandInPresenter>(), IntelligentHandInContract.View {
    override fun isStatusBarMateria(): Boolean {
        return true
    }

    var adapter: IntelligentHandInAdapter? = null
    lateinit var questionInfos: List<QuestionInfoWrapper.QuestionInfo>

    override fun init() {
        MobclickAgent.onEvent(this, "intelligent_handin", "智能评测-交卷")
        mPresenter = IntelligentHandInPresenter(this, this)
        mToolbar.mIndexTextView.visibility = View.GONE
        StatusBarCompat.light(this)
        StatusBarCompat.compat(this, mToolbar, mToolbar.mToolbar, mToolbar.mStatubar)
        RxView.clicks(mToolbar.mBackBtn).throttleFirst(200, TimeUnit
                .MILLISECONDS).subscribe {
            finish()
        }

        RxView.clicks(mSubmitBtn).throttleFirst(200, TimeUnit
                .MILLISECONDS).subscribe {
            mPresenter.submitAnswers(questionInfos, IntelligentQuestionsActivity.getInstance()?.usedTime()
                    ?: "")
        }

        mToolbar.mTimeTextView.text = IntelligentQuestionsActivity.getInstance()?.usedTime() ?: ""
        questionInfos = getLevel1QuestionInfo(IntelligentQuestionsActivity.getInstance()?.questionInfos!!)
        adapter = IntelligentHandInAdapter(questionInfos)
        val gridLayoutManager = GridLayoutManager(this, 5)
        gridLayoutManager.setSpanSizeLookup(object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (adapter!!.getItemViewType(position) == 0) 5 else 1
            }
        })
        mRecyclerView.adapter = adapter
        mRecyclerView.layoutManager = gridLayoutManager

        adapter!!.setOnItemClickListener { adapter, _, position ->
            if (adapter.getItemViewType(position) == 0) return@setOnItemClickListener
            val questionInfo = adapter.data[position] as QuestionInfoWrapper.QuestionInfo
            IntelligentQuestionsActivity.getInstance()?.next(questionInfo.actIndex, questionInfo.frgIndex)
            finish()
        }
    }

    override fun showSuccess(msg: String) {
        runOnUiThread {
            finish()
            SimpleCacheUtils.writeCache(this, IntelligentQuestionsActivity.getInstance()?.getResultKey()
                    ?: "error", JSON
                    .toJSONString(questionInfos))
            SPUtils.getInstance().put(IntelligentQuestionsActivity.getInstance()?.getFinishTimeKey()
                    ?: "error", mToolbar
                    .mTimeTextView.text.toString())
            SPUtils.getInstance().put(IntelligentQuestionsActivity.getInstance()?.getFinishKey()
                    ?: "error", 1)
            IntelligentQuestionsActivity.getInstance()?.isResultIn = true
            RxBus.get().post(Constant.RESULT_ANS, "from result")
            val intent = Intent(this@IntelligentHandInActivity, IntelligentResultActivity::class.java)
            intent.putParcelableArrayListExtra("questionInfos", questionInfos as ArrayList<out Parcelable>)
            startActivity(intent)
        }
    }


    override fun showFail(msg: String) {
        ToastUtils.showShort(msg)
    }

    override fun getLayoutId() = R.layout.intelligent_avtivity_hand_in

}