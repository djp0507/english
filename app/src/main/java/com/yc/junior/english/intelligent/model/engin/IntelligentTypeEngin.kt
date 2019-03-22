package com.yc.junior.english.intelligent.model.engin

import android.content.Context
import com.alibaba.fastjson.TypeReference
import com.kk.securityhttp.domain.ResultInfo
import com.kk.securityhttp.engin.HttpCoreEngin
import com.yc.junior.english.intelligent.model.domain.URLConfig
import com.yc.junior.english.intelligent.model.domain.UnitInfoWrapper
import com.yc.junior.english.intelligent.model.domain.VGInfoWarpper
import com.yc.junior.english.main.hepler.UserInfoHelper
import rx.Observable
import yc.com.base.BaseEngine

/**
 * Created by zhangkai on 2017/12/4.
 */

//获取版本 获取年级 获取单元
class IntelligentTypeEngin(context: Context?) : BaseEngine(context) {
    fun getVersion(): Observable<ResultInfo<VGInfoWarpper>> {
        return HttpCoreEngin.get(mContext).rxpost(URLConfig.GET_VERSION, object :
                TypeReference<ResultInfo<VGInfoWarpper>>() {}.type, null,
                true, true, true) as Observable<ResultInfo<VGInfoWarpper>>
    }

    fun getGrade(vgInfo: VGInfoWarpper.VGInfo): Observable<ResultInfo<VGInfoWarpper>> {
        return HttpCoreEngin.get(mContext).rxpost(URLConfig.GET_GRADE, object :
                TypeReference<ResultInfo<VGInfoWarpper>>() {}.type, mutableMapOf("version_id" to vgInfo.versionId),
                true, true, true) as Observable<ResultInfo<VGInfoWarpper>>
    }

    fun getUnit(vgInfo: VGInfoWarpper.VGInfo): Observable<ResultInfo<UnitInfoWrapper>> {
        var uid = ""
        if (UserInfoHelper.getUserInfo() != null) {
            uid = UserInfoHelper.getUserInfo().uid
        }
        return HttpCoreEngin.get(mContext).rxpost(URLConfig.GET_UNIT, object :
                TypeReference<ResultInfo<UnitInfoWrapper>>() {}.type,
                mutableMapOf(
                        "version_id" to vgInfo.versionId,
                        "grade" to vgInfo.grade,
                        "part_type" to vgInfo.partType,
                        "user_id" to uid),
                true, true, true) as Observable<ResultInfo<UnitInfoWrapper>>
    }
}