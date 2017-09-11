package com.yc.english.union.contract;

import com.yc.english.base.presenter.IPresenter;
import com.yc.english.base.view.IDialog;
import com.yc.english.base.view.IFinish;
import com.yc.english.base.view.ILoading;
import com.yc.english.base.view.INoData;
import com.yc.english.base.view.INoNet;
import com.yc.english.base.view.IView;
import com.yc.english.group.model.bean.ClassInfo;
import com.yc.english.group.model.bean.StudentInfo;

import java.util.List;

/**
 * Created by wanglin  on 2017/9/7 12:23.
 */

public interface UnionListContract {
    interface View extends IView, IFinish {

//        void showUnionList(List<ClassInfo> data, int isLoadMore, boolean isFitst);

        void showMemberList(List<StudentInfo> list);


        void showUnionList1(List<ClassInfo> list);
    }

    interface Presenter extends IPresenter {

    }
}
