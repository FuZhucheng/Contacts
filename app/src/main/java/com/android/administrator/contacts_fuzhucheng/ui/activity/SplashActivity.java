package com.android.administrator.contacts_fuzhucheng.ui.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.android.administrator.contacts_fuzhucheng.R;

public class SplashActivity extends AppCompatActivity {

    private Handler mMainHandler = new Handler() {

        public void handleMessage(Message msg) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.setClass(getApplication(), MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }
    };

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_splash);
        mMainHandler.sendEmptyMessageDelayed(0, 2000);
    }

    // much easier to handle key events
    @Override
    public void onBackPressed() {
    }
}


