package com.jkyeo.aspectjinterceptordemo;

import android.app.Activity;
import android.util.Log;

/**
 * @author 杨建宽
 * @date 2017/11/16
 * @mail yangjiankuan@lanjingren.com
 * @desc
 */

public class DemoModel {

    @NeedLogin(retry = true)
    public void demoTest(Activity activity, String msg) {
        Log.i("aspectj", "==== Action that needs login has been executed. MSG = " + msg);
    }
}
