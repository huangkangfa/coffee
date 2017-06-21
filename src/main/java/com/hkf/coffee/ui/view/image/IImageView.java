package com.hkf.coffee.ui.view.image;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by Administrator on 2017/3/29 0029.
 */
public interface IImageView {
    void initFrame(Canvas canvas, Paint framePaint);
    void initBackground(Canvas canvas, Paint backgroundPaint);
    void initAnimation(Canvas canvas, Paint mPaint);
}
