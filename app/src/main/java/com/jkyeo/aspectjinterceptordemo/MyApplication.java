package com.jkyeo.aspectjinterceptordemo;

import android.app.Application;

import rx_activity_result2.RxActivityResult;

/**
 * @author 杨建宽
 * @date 2017/11/16
 * @mail yangjiankuan@lanjingren.com
 * @desc
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        RxActivityResult.register(this);
    }
}
