package com.the_liuchao.interview;

import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity {

    Handler handler = new Handler();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_flash);
        final Runnable loadLayoutRunnable = new Runnable() {;
            public void run() {
                setContentView(R.layout.activity_main);
                initView();
            }
        };
        handler.postDelayed(loadLayoutRunnable, 5000);
    }

    /**
     * 初始化视图方法
     */
    private void initView() {
    }
}
