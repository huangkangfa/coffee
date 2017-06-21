package com.hkf.coffee.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hkf.coffee.R;
import com.hkf.coffee.dialog.base.LoadingDialog;
import com.hkf.coffee.dialog.base.OnClick;
import com.hkf.coffee.exception.BaseException;

/**
 * Created by Administrator on 2017/5/5 0005.
 */
public class DialogManager {
    private Context context;

    private ProgressDialog progressDialog;  //左边进度右边文字的等待框
    private LoadingDialog loadingDialog;  //上边进度下边文字的等待框
    private Dialog dialog;      //提示框

    public DialogManager(Context context){
        this.context=context;
    }

    /**
     * 展示加载框
     */
    public void showLoadingDialog(){
        if(loadingDialog==null)
            throw new BaseException("showLoadingDialog   空指针异常。弹框对象为空！");
        loadingDialog.show();
    }

    /**
     * 展示加载提示框
     */
    public void showProgressDialog(){
        if(progressDialog==null)
            throw new BaseException("showProgressDialog   空指针异常。弹框对象为空！");
        progressDialog.show();
    }

    /**
     * 隐藏加载框
     */
    public void hideLoadingDialog(){
        loadingDialog.dismiss();
    }

    /**
     * 隐藏加载提示框
     */
    public void hideProgressDialog(){
        progressDialog.dismiss();
    }
    /**
     * 隐藏提示框
     */
    public void hideAlertDialog(){
        dialog.cancel();
    }

    /**
     * NoID 提示 （只有一个确定按钮）
     * @param text 提示内容
     */
    public void showAlertDialog(String text,boolean isCanceledOnTouchOutside) {
        dialog = new AlertDialog.Builder(context).setView(new EditText(context)).create();
        dialog.setCanceledOnTouchOutside(isCanceledOnTouchOutside);
        dialog.show();

        Window window = dialog.getWindow();
        window.setBackgroundDrawableResource(R.drawable.bg_transparent);
        window.setContentView(R.layout.alert_ks_no_id);

        LinearLayout llSure;
        TextView tvAlert;
        llSure = (LinearLayout) window.findViewById(R.id.llSure);
        tvAlert = (TextView) window.findViewById(R.id.tvAlert);
        tvAlert.setText(text);

        llSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
    }
    /**
     * NoID 提示 (带取消确定2个按钮)
     * @param text 提示内容
     */
    public void showAlertDialog(String text,boolean isCanceledOnTouchOutside, final OnClick listener) {
        dialog = new AlertDialog.Builder(context).setView(new EditText(context)).create();
        dialog.setCanceledOnTouchOutside(isCanceledOnTouchOutside);
        dialog.show();

        Window window = dialog.getWindow();
        window.setBackgroundDrawableResource(R.drawable.bg_transparent);
        window.setContentView(R.layout.alert_ks_ordinary);

        LinearLayout llSure1,llSure2;
        TextView tvAlert;
        llSure1 = (LinearLayout) window.findViewById(R.id.llSure1);
        llSure2 = (LinearLayout) window.findViewById(R.id.llSure2);
        tvAlert = (TextView) window.findViewById(R.id.tvAlert);
        tvAlert.setText(text);

        llSure1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onCancel(v);
                dialog.cancel();
            }
        });
        llSure2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick(view);
                dialog.cancel();
            }
        });
    }

    /**
     * 设置等待框
     * @param content
     */
    public void setLaodingDialog(String content){
        loadingDialog=new LoadingDialog(context,content,R.style.CustomDialog);
        loadingDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK&& event.getRepeatCount() == 0) {
                    if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0){
                        loadingDialog.dismiss();
                        return true;
                    }
                }
                return false;
            }
        });
    }

    /**
     * 设置ProgressDialog
     * @param Message
     * @param Cancelable
     * @param CanceledOnTouchOutside
     */
    public void setProgressDialog(String Message, boolean Cancelable, boolean CanceledOnTouchOutside) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(Message);
        progressDialog.setCancelable(Cancelable);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);// 设置进度条的形式为圆形转动的进度条
        progressDialog.setCanceledOnTouchOutside(CanceledOnTouchOutside);
    }

    public void setProgress(String msg){
        progressDialog.setMessage(msg);
    }
}
