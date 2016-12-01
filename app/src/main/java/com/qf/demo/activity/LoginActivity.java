package com.qf.demo.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.qf.demo.R;
import com.qf.demo.entities.UserData;
import com.qf.demo.user.UserCenter;
import com.qf.demo.utils.Utils;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;

public class LoginActivity extends AppCompatActivity implements
        View.OnClickListener, UserCenter.UserLoginCallBack,
        UserCenter.OtherLoginCallBack {

    private EditText accountEt;
    private EditText passwordEt;
    private Button loginBtn;
    private Button registerBtn;
    private MyApp app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        app = (MyApp) getApplication();
        init();
    }

    /**
     * 初始化的控制
     */
    private void init() {
        initView();
        initData();
        setListener();
    }

    /**
     * 设置监听
     */
    private void setListener() {
        loginBtn.setOnClickListener(this);
        registerBtn.setOnClickListener(this);
        UserCenter.getInstance().setUserLoginListener(this);
        UserCenter.getInstance().setOtherLoginCallBack(this);
    }

    /**
     * 数据的初始化
     */
    private void initData() {

    }

    /**
     * 寻找控件
     */
    private void initView() {
        accountEt = (EditText) findViewById(R.id.et_act_login);
        passwordEt = (EditText) findViewById(R.id.et_pwd_login);
        initEditText(accountEt, passwordEt);
        loginBtn = (Button) findViewById(R.id.btn_login);
        registerBtn = (Button) findViewById(R.id.btn_reg);
    }

    /**
     * 调整编辑控件Drawable图标的大小
     *
     * @param edit
     */
    private void initEditText(EditText... edit) {
        for (int i = 0; i < edit.length; i++) {
            Drawable[] drawables = edit[i].getCompoundDrawables();
            drawables[0].setBounds(0, 0, 150, 150);
            edit[i].setCompoundDrawables(drawables[0], drawables[1], drawables[2], drawables[3]);
        }
    }

    //ForResult的请求码
    private static final int REQUEST_REGISTER = 1;

    /**
     * 处理点击事件
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login://点击的是登录
                String account = accountEt.getText().toString();
                String password = passwordEt.getText().toString();
                if (Utils.getInstance().textIsNull(account, password)) {
                    Toast.makeText(LoginActivity.this, "输入不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                UserCenter.getInstance().userLogin(account, password, LoginActivity.this);
                break;
            case R.id.btn_reg://点击的是注册
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivityForResult(intent, REQUEST_REGISTER);
                break;
        }
    }

    /**
     * 有返回结果时调用 返回成功注册的账号
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_REGISTER && resultCode == RESULT_OK && data != null) {
            String account = data.getStringExtra("account");
            if (account.equals("error")) {
                Utils.getInstance().toast("注册失败", LoginActivity.this);
            } else {
                Utils.getInstance().toast("注册成功", LoginActivity.this);
                accountEt.setText(account);
            }
        }

    }

    /**
     * 登录完成时调用
     *
     * @param userData
     */
    @Override
    public void onLoginComplete(UserData userData) {
        initShare(userData);
        Intent intent = new Intent();
        intent.putExtra("from", "qingman");
        setResult(RESULT_OK, intent);
        finish();
    }

    /**
     * 初始化SharePreference
     *
     * @param userData
     */
    private void initShare(UserData userData) {
        app.getEditor().putBoolean("isLogin", userData.isLogin());
        app.getEditor().putString("id", userData.getUser_id());
        app.getEditor().putString("alias", userData.getUser_nickname());
        app.getEditor().putString("portrait", userData.getUser_pic());
        app.getEditor().putString("token", userData.getToken());
        app.getEditor().putString("session", userData.getSession());
        app.getEditor().putString("city", userData.getCity());
        app.getEditor().putString("qq", userData.getQq());
        app.getEditor().putString("weibo", userData.getWeibo());
        app.getEditor().putString("weixin", userData.getWeixin());
        app.getEditor().putString("email", userData.getEmail());
        app.getEditor().putString("mobile", userData.getMobile());
        app.getEditor().putString("desc", userData.getUser_desc());
        app.getEditor().commit();
    }

    /**
     * 登录失败时调用
     *
     * @param str
     */
    @Override
    public void onLoginErr(String str) {
    }

    private String target;

    /**
     * 处理第三方登录
     */
    public void otherLogin(View view) {
        switch (view.getId()) {
            case R.id.iv_qq:
                qqLogin();
                target = "qq";
                break;
            case R.id.iv_weixin:
                weixinLogin();
                target = "weixin";
                break;
        }
    }

    /**
     * 微信的第三方登录
     */
    private void weixinLogin() {
        Platform weixin = ShareSDK.getPlatform(Wechat.NAME);
        weixin.setPlatformActionListener(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                String userName = platform.getDb().getUserName();
                String userId = platform.getDb().getUserId();
                String userIcon = platform.getDb().getUserIcon();
                System.out.println(userName);
                UserCenter.getInstance().otherLogin(userId, userName, userIcon, "weixin", LoginActivity.this);
            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
            }

            @Override
            public void onCancel(Platform platform, int i) {
            }
        });

        weixin.showUser(null);
    }

    /**
     * QQ的第三方登录
     */
    private void qqLogin() {
        Platform qq = ShareSDK.getPlatform(QQ.NAME);
        qq.setPlatformActionListener(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                String userName = platform.getDb().getUserName();
                String userId = platform.getDb().getUserId();
                String userIcon = platform.getDb().getUserIcon();
                UserCenter.getInstance().otherLogin(userId, userName, userIcon, "qq", LoginActivity.this);
            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
            }

            @Override
            public void onCancel(Platform platform, int i) {
            }
        });

        qq.showUser(null);
    }

    /**
     * 使用第三方登录成功
     *
     * @param data
     */
    @Override
    public void otherLoginSuccess(UserData data) {
        System.out.println("成功");
        initShare(data);
        Intent intent = new Intent();
        intent.putExtra("from", target);
        setResult(RESULT_OK, intent);
        finish();
    }

    /**
     * 使用第三方登录失败
     */
    @Override
    public void otherLoginError() {
        System.out.println("失败");
    }

    /**
     * 点击返回图标
     *
     * @param view
     */
    public void backUserCenter(View view) {
        finish();
    }
}
