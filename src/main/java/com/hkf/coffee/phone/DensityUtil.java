package com.hkf.coffee.phone;

import android.util.TypedValue;

import com.hkf.coffee.BaseApplication;

/**
 * 单位转换类
 *
 * dp转px
 * sp转px
 * px转dp
 * px转sp
 *
 */
public class DensityUtil {

    /**
     * cannot be instantiated
     */
    private DensityUtil() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * dp转px
     *
     * @param dpVal
     * @return
     */
    public static int dp2px(float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, BaseApplication.getContext().getResources().getDisplayMetrics());
    }

    /**
     * sp转px
     *
     * @param spVal
     * @return
     */
    public static int sp2px(float spVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                spVal, BaseApplication.getContext().getResources().getDisplayMetrics());
    }

    /**
     * px转dp
     *
     * @param pxVal
     * @return
     */
    public static float px2dp( float pxVal) {
        final float scale = BaseApplication.getContext().getResources().getDisplayMetrics().density;
        return (pxVal / scale);
    }

    /**
     * px转sp
     *
     * @param pxVal
     * @return
     */
    public static float px2sp(float pxVal) {
        return (pxVal / BaseApplication.getContext().getResources().getDisplayMetrics().scaledDensity);
    }
}