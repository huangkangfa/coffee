package com.hkf.coffee.exception;

/**
 * Created by huangkangfa on 2017/6/18.
 */

public class BaseException extends RuntimeException{
    public BaseException() {
        super();
    }

    public BaseException(String msg) {
        super(msg);
    }

    public BaseException(Throwable ex) {
        super(ex);
    }

    public BaseException(String msg, Throwable ex) {
        super(msg, ex);
    }
}
