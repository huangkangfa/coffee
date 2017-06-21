package com.hkf.coffee.ui.view;

import android.content.Context;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;
import android.view.View;

/**
 * 防止重复点击按钮
 * Created by huangkangfa on 2017/3/15 0015.
 */
public class ButtonRecur extends AppCompatButton {
    private long firstClickTime,lastClickTime;  //当前点击时间戳    上一次点击的时间戳
    public long MAXTIME=500;  //时间间隔
    private OnKsClickListener listener;  //频繁点击的监听对象

    public ButtonRecur(Context context) {
        this(context,null);
    }
    public ButtonRecur(Context context, AttributeSet attrs) {
        super(context, attrs);
        firstClickTime=System.currentTimeMillis();
        lastClickTime=firstClickTime;
    }

    /**
     * 设置频繁点击的处理
     * @param listener
     */
    public void setOnKsClickListener(OnKsClickListener listener){
        this.listener=listener;
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isFastDoubleClick()||((ButtonRecur)view).listener==null){
                    ((ButtonRecur)view).listener.doTooFast();
                }else ((ButtonRecur)view).listener.doClick(view);
            }
        });
    }


    /**
     * 判断是否为连续点击
     * @return boolean
     */
    private boolean isFastDoubleClick() {
        firstClickTime = System.currentTimeMillis();
        long intervalTime = firstClickTime - lastClickTime;
        if (0 < intervalTime && intervalTime < MAXTIME) {
            return true;
        }
        lastClickTime = firstClickTime;
        return false;
    }

    /**
     * 防止频繁操作监听
     */
    public interface OnKsClickListener{
        void doTooFast();
        void doClick(View v);
    }
}
