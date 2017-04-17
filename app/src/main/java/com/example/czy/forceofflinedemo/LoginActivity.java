package com.example.czy.forceofflinedemo;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by CZY on 2017/4/17.
 */

public class LoginActivity extends BaseActivity {

    private EditText nameEt, passwordEt;
    private Button loginBtn;

    @Override
    protected int bindLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {

        nameEt = bindView(R.id.name_et);
        passwordEt = bindView(R.id.password_et);
        loginBtn = bindView(R.id.login_btn);

    }

    @Override
    protected void initData() {
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取用户名
                String name = String.valueOf(nameEt.getText());
                //获取密码
                String password = String.valueOf(passwordEt.getText());
                //如果用户名为user并且密码为123456，就认为登录成功
                if (name.equals("user") && password.equals("123456")) {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "用户名或密码错误，请重新输入", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
