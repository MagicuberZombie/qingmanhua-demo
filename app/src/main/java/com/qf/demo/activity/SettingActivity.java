package com.qf.demo.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.qf.demo.R;

/**
 * 系统设置页面
 */
public class SettingActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mExitLoginBtn;
    private Button mChangePwdBtn;
    private Button mBackBtn;

    private TextView mTitleName;

    private MyApp app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        app = (MyApp) getApplication();
        init();
    }

    /**
     * 控制初始化
     */
    private void init() {
        initView();
        setListener();
        initData();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        mTitleName.setText("设置");
    }

    /**
     * 设置监听
     */
    private void setListener() {
        mExitLoginBtn.setOnClickListener(this);
        mChangePwdBtn.setOnClickListener(this);
        mBackBtn.setOnClickListener(this);
    }

    /**
     * 寻找控件
     */
    private void initView() {
        mExitLoginBtn = (Button) findViewById(R.id.btn_exit_login);
        mChangePwdBtn = (Button) findViewById(R.id.btn_update_password);
        mTitleName = (TextView) findViewById(R.id.tv_title_name);
        mBackBtn = (Button) findViewById(R.id.btn_back);
    }

    /**
     * 处理点击事件
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_exit_login:
                app.getEditor().putBoolean("isLogin", false);
                app.getEditor().commit();
                setResult(RESULT_OK);
                break;
            case R.id.btn_update_password:
                Intent intent = new Intent(SettingActivity.this, ChangePasswordActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_back:
                break;
        }
        finish();
    }
}
