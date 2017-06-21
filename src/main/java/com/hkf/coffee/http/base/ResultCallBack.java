package com.hkf.coffee.http.base;

import android.text.TextUtils;

import com.hkf.coffee.jars.nohttp.OnResponseListener;
import com.hkf.coffee.jars.nohttp.Response;
import com.hkf.coffee.others.json.JsonUtil;

/**
 * 提供请求结果快捷功能
 * Created by wanglei on 16/3/29.
 */
public abstract class ResultCallBack implements OnResponseListener<String> {

    @Override
    public void onStart(int what) {
    }

    @Override
    public void onSucceed(int what, Response<String> response) {
        String resultStr = response.get();
        Result result = null;
        if (!TextUtils.isEmpty(resultStr)) {
            result = JsonUtil.getObj(resultStr, Result.class);
        }
        if (result == null) {
            result = new Result();
            result.setRetCode(RetCode.WRONG_DATA);
            result.setErrorMsg("数据异常");
        } else {
            result.setJson(resultStr);
        }
        onSuccess(what, result);

    }

    @Override
    public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
        Result result = new Result();
        result.setRetCode("E"+responseCode);
        result.setErrorMsg("网络异常");
        onSuccess(what,result);
    }

    @Override
    public void onFinish(int what) {
    }

    public abstract void onSuccess(int what, Result result);

}
