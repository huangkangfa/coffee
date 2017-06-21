package com.hkf.coffee.ui.view.image;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

/**
 * Created by Administrator on 2017/3/29 0029.
 */
public class ImageViewAnim extends AppCompatImageView implements IImageView{
    public final static int RECT=0; //矩形
    public final static int CIRCLE=1; //圆形
    public final static int ROUNDRECT=2; //圆角

    private boolean animationIsOpen=false;  //点击动画是否开启
    private boolean hasFrame=true;  //是否有边框

    private Paint animationPaint,backgroundPaint,framePaint; //动画画笔、背景画笔
    private String color1="#ffffff";  //默认动画画笔颜色
    private float color1With=10f;
    private String color2="#44000000"; //默认背景画笔颜色
    private String color3="#ffffff"; //默认边框颜色
    private float color3With=5f;

    private int type=0;  //点击后控件背景显示形状
    private float radius=0f;  //矩形圆角幅度   仅仅ROUNDRECT类型下使用

    private int startAngle=0; //这个参数的作用是设置圆弧是从哪个角度来顺时针绘画的
    private int sweepAngle=355;//这个参数的作用是设置圆弧扫过的角度
    private boolean useCenter=true;//这个参数的作用是设置我们的圆弧在绘画的时候，是否经过圆形

    private int w,h;  //控件的宽高

    public ImageViewAnim(Context context) {
        this(context,null);
    }

    public ImageViewAnim(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    private void initPaint() {
        animationPaint = new Paint();
        animationPaint.setAntiAlias(true);                       //设置画笔为无锯齿
        animationPaint.setColor(Color.parseColor(color1));       //设置画笔颜色
        animationPaint.setStrokeWidth(color1With);             //线宽
        animationPaint.setStyle(Paint.Style.STROKE);
        animationPaint.setStrokeCap(Paint.Cap.ROUND);
//        animationPaint.setPathEffect ( new DashPathEffect( new float [ ] { 20,40 }, 0 ) ) ; //先画长度为20的实线，再间隔长度为2的空白，之后一直重复这个单元。这个数组的长度只要大于等于2就行，你可以设置多个数值，产生不同效果，最后这个0指的是与起始位置的偏移量

        backgroundPaint=new Paint();
        backgroundPaint.setAntiAlias(true);
        backgroundPaint.setColor(Color.parseColor(color2));

        framePaint=new Paint();
        framePaint.setAntiAlias(true);
        framePaint.setColor(Color.parseColor(color3));
        framePaint.setStrokeWidth(color3With);
        framePaint.setStyle(Paint.Style.STROKE);
        framePaint.setStrokeCap(Paint.Cap.ROUND);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        w=getMeasuredWidth();
        h=getMeasuredHeight();
        if(hasFrame){
            initFrame(canvas,framePaint);
        }
        if(animationIsOpen){
            /**
             * 画背景
             */
            initBackground(canvas,backgroundPaint);
            /**
             * 画中间的缓冲动画
             */
            initAnimation(canvas,animationPaint);
            invalidate();
        }
    }

    /**
     * 设置动画是否开启
     */
    public void setAnimationOpen(boolean flag){
        animationIsOpen=flag;
        invalidate();
    }

    /**
     * 设置是否有边框
     */
    public void setHasFrame(boolean flag){
        hasFrame=flag;
        invalidate();
    }

    /**
     * 更改画笔颜色
     */
    public void changePaintColor(String frameP,String bk,String p){
        color1=p;
        color2=bk;
        color3=frameP;
        animationPaint.setColor(Color.parseColor(color1));
        backgroundPaint.setColor(Color.parseColor(color2));
        framePaint.setColor(Color.parseColor(color3));
        invalidate();
    }

    /**
     * 设置控件背景形状  默认是矩形
     * @param type
     * @param radius
     */
    public void setType(int type,float radius){
        this.type=type;
        this.radius=radius;
        invalidate();
    }

    //初始化边框
    @Override
    public void initFrame(Canvas canvas, Paint framePaint) {
        float temp=color3With/2;
        switch (type){
            case RECT:
                canvas.drawRect(temp,temp,w-temp,h-temp,framePaint);
                break;
            case CIRCLE:
                canvas.drawCircle(w/2,h/2,Math.min(w, h)/2-temp,framePaint);
                break;
            case ROUNDRECT:
                canvas.drawRoundRect(new RectF(temp, temp,w-temp, h-temp),radius,radius,framePaint);
                break;
        }
    }

    //初始化点击后的背景效果
    @Override
    public void initBackground(Canvas canvas, Paint backgroundPaint) {
        switch (type){
            case RECT:
                canvas.drawRect(0,0,w,h,backgroundPaint);
                break;
            case CIRCLE:
                canvas.drawCircle(w/2,h/2,Math.min(w, h)/2,backgroundPaint);
                break;
            case ROUNDRECT:
                canvas.drawRoundRect(new RectF(0f, 0f,w, h),radius,radius,backgroundPaint);
                break;
        }
    }

    //初始化点击后的核心动画
    @Override
    public void initAnimation(Canvas canvas, Paint mPaint) {
        float x = (w - h / 2) / 2;
        float y = h / 4;
        RectF oval = new RectF( x, y,w - x, h - y);
        canvas.drawArc(oval,startAngle,sweepAngle,useCenter,mPaint);
        startAngle=startAngle+8;
        try {
            Thread.sleep(15);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
