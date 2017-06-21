package com.hkf.coffee.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;

import com.hkf.coffee.ui.ActivityManager;

/**
 * APP基础Activity
 * 提供Activity快捷功能
 * Created by huangkangfa on 16/3/23.
 */
public class BaseActivity extends AppCompatActivity {
    public BaseActivity mActivity;
    public LayoutInflater mInflater;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityManager.getActivity().add(this);
        mActivity = this;
        mInflater = LayoutInflater.from(this);
    }

    @Override
    protected void onDestroy() {
        ActivityManager.getActivity().remove(this);
        super.onDestroy();
    }

}
