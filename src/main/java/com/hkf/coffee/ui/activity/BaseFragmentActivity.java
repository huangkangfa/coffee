package com.hkf.coffee.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * Fragment基础Activity
 * 提供FragmentActivity快捷功能
 * Created by huangkangfa on 16/3/25.
 */
public class BaseFragmentActivity extends BaseActivity {
    public FragmentManager mFManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFManager = getSupportFragmentManager();
    }

    /**
     * 添加Fragment
     * @param isAddBackStack 是否添加回退栈
     */
    public FragmentTransaction addFragment(int contentId, Fragment fragment, String tag, boolean isAddBackStack) {
        FragmentTransaction transaction = mFManager.beginTransaction();
        transaction.add(contentId, fragment, tag);
        if (isAddBackStack) {
            transaction.addToBackStack(tag);
        }
        return transaction;
    }

    /**
     * 覆盖Fragment
     * @param isAddBackStack 是否添加回退栈
     */
    public FragmentTransaction replaceFragment(int contentId, Fragment fragment, String tag, boolean isAddBackStack) {
        FragmentTransaction transaction = mFManager.beginTransaction();
        transaction.replace(contentId, fragment, tag);
        if (isAddBackStack) {
            transaction.addToBackStack(tag);
        }
        return transaction;
    }

}
