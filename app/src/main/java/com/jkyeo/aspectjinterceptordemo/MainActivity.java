package com.jkyeo.aspectjinterceptordemo;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.demoButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                demoTest("hello, aspectj.");
                new DemoModel().demoTest(MainActivity.this, "hello, aspectj in model.");
            }
        });
    }

    @NeedLogin(retry = true)
    private void demoTest(String msg) {
        Log.i("aspectj", "==== Action that needs login has been executed. MSG = " + msg);
    }
}