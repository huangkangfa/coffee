package com.hkf.coffee.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hkf.coffee.ui.activity.BaseFragmentActivity;

/**
 * 基础的Fragment
 * 提供Fragment快捷功能
 * Created by huangkangfa on 16/3/25.
 */
public class BaseFragment extends Fragment {

    public BaseFragmentActivity mActivity;
    public LayoutInflater mInflater;
    public FragmentManager mFManager;
    public Bundle mBundle;
    public Fragment mFragment;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mActivity = (BaseFragmentActivity) getActivity();
        this.mFManager = mActivity.mFManager;
        this.mBundle = getArguments();
        this.mFragment = this;
    }


    public View setContentView(LayoutInflater inflater, int layoutResID, ViewGroup container) {
        return setContentView(inflater, layoutResID, container, false);
    }

    public View setContentView(LayoutInflater inflater, int layoutResID, ViewGroup container, boolean attachToRoot) {
        this.mInflater = inflater;
        View viewRoot = inflater.inflate(layoutResID, container, attachToRoot);
        return viewRoot;
    }
}
