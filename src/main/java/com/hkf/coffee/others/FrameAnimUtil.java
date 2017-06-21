package com.hkf.coffee.others;

import android.graphics.drawable.AnimationDrawable;
import android.widget.ImageView;

/**
 * 帧动画操作工具
 * Created by huangkangfa on 2017/5/12 0012.
 */
public class FrameAnimUtil {
    AnimationDrawable ani;
    public FrameAnimUtil(ImageView img, int src){
        img.setImageResource(src);
        ani= (AnimationDrawable) img.getDrawable();
    }
    //开启动画
    public void start(){
        ani.start();
    }
    //关闭动画
    public void stop(){
        ani.stop();
    }
}
