package com.example.czy.forceofflinedemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends BaseActivity {

    private Button forceOfflineBtn;

    @Override
    protected int bindLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        forceOfflineBtn = bindView(R.id.force_offline_btn);
    }

    @Override
    protected void initData() {
        forceOfflineBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //发送广播
                Intent intent = new Intent("forceOffline");
                sendBroadcast(intent);
            }
        });
    }
}
