package com.yc.english.base.view;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;

import com.yc.english.R;
import com.yc.english.base.presenter.BasePresenter;

import butterknife.BindView;

/**
 * Created by zhangkai on 2017/7/24.
 */

public abstract class FullScreenActivity<P extends BasePresenter> extends BaseActivity<P> {

    @BindView(R.id.toolbar)
    protected BaseToolBar mToolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (mToolbar == null) {
            throw new NullPointerException("error, please set com.yc.english.main.view.MainToolBar id -> toolbar.");
        }

        mToolbar.init(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (mToolbar.isHasMenu()) {
            getMenuInflater().inflate(R.menu.base_toolbar_menu, menu);
            mToolbar.setOnMenuItemClickListener();
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (mToolbar.isHasMenu()) {
            MenuItem menuItem = menu.findItem(R.id.action);
            menuItem.setTitle(mToolbar.getMenuTitle());
            if (mToolbar.getIconResid() != 0) {
                menuItem.setIcon(mToolbar.getIconResid());
            }
        }
        return super.onPrepareOptionsMenu(menu);
    }
}