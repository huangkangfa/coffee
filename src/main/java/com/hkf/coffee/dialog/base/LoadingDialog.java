package com.hkf.coffee.dialog.base;

/**
 * Created by Administrator on 2017/5/3 0003.
 */

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

import com.hkf.coffee.R;


/**
 * 加载提醒对话框
 */
public class LoadingDialog extends ProgressDialog {
    private String content="";  //提示内容

    public LoadingDialog(Context context) {
        super(context);
    }

    public LoadingDialog(Context context, int theme) {
        super(context, theme);
    }
    public LoadingDialog(Context context, String content, int theme) {
        super(context, theme);
        this.content=content;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(getContext());
    }

    private void init(Context context) {
        //设置不可取消，点击其他区域不能取消，实际中可以抽出去封装供外包设置
        setCancelable(false);
        setCanceledOnTouchOutside(false);

        setContentView(R.layout.custom_loading);
        ((TextView)findViewById(R.id.tv_load_dialog)).setText("".equals(content)?"请稍后":content);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getWindow().setAttributes(params);
    }

    @Override
    public void show() {
        super.show();
    }
}
