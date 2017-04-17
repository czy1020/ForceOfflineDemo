package com.example.czy.forceofflinedemo;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by CZY on 2017/4/17.
 */

public abstract class BaseActivity extends AppCompatActivity {

    private ActivityController activityController;
    private ForceOfflineReceiver receiver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //绑定布局
        setContentView(bindLayout());
        //初始化组件
        initView();
        //初始化数据
        initData();
        //创建出管理所有Activity类的对象
        activityController = new ActivityController();

        activityController.addActivity(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter("forceOffline");
        receiver = new ForceOfflineReceiver();
        registerReceiver(receiver, intentFilter);
    }

    @Override
    protected void onPause() {
        if (receiver != null) {
            unregisterReceiver(receiver);
            receiver = null;
        }
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        activityController.removeActivity(this);
        super.onDestroy();
    }

    /**
     * 绑定布局
     *
     * @return
     */
    protected abstract int bindLayout();

    /**
     * 初始化组件
     */
    protected abstract void initView();

    /**
     * 初始化数据
     */
    protected abstract void initData();

    /**
     * 绑定组件
     *
     * @param resId 组件id
     * @param <T>   强制转换类型
     * @return
     */
    protected <T extends View> T bindView(int resId) {
        return (T) findViewById(resId);
    }

    /**
     * 管理所有Activity的类
     */
    private class ActivityController {

        private List<Activity> activities = new ArrayList<>();

        /**
         * 添加Activity
         *
         * @param activity
         */
        private void addActivity(Activity activity) {
            activities.add(activity);
        }

        /**
         * 移除Activity
         *
         * @param activity
         */
        private void removeActivity(Activity activity) {
            activities.remove(activity);
        }

        /**
         * 结束所有Activity
         */
        private void finishAll() {
            for (Activity activity : activities) {
                if (!activity.isFinishing()) {
                    activity.finish();
                }
            }
        }

    }

    /**
     * 广播接收器
     */
    private class ForceOfflineReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(final Context context, Intent intent) {

            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("下线通知");
            builder.setMessage("您的账号在另一地点登录，您被迫下线了。如果这不是您本人的操作，那么您的密码可能已经泄露，建议您修改密码。");
            builder.setPositiveButton("重新登录", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(context, LoginActivity.class);
                    startActivity(intent);
                    //调用结束所有Activity的方法
                    activityController.finishAll();
                }
            });
            builder.setNegativeButton("退出", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    activityController.finishAll();
                }
            });
            builder.setCancelable(false);
            builder.show();
        }
    }


}
