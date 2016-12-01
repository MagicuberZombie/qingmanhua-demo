package com.qf.demo.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.qf.demo.R;
import com.qf.demo.entities.UserData;
import com.qf.demo.user.UserCenter;

/**
 * 输入修改信息的页面
 */
public class InputUserInfoActivity extends Activity
        implements UserCenter.ChangeUserInfoCallBack {

    private Button mSendBtn;
    private EditText mChangeInfoEt;
    private String info;
    private MyApp app;
    private UserData userData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_user_info);
        app = (MyApp) getApplication();
        init();
    }

    /**
     * 控制初始化
     */
    private void init() {
        initData();
        setListener();
        changeInfo();
    }


    /**
     * 设置监听
     */
    private void setListener() {
        UserCenter.getInstance().setChangeUserInfoCallBack(this);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        userData = UserCenter.getInstance().getUserData();
        initUserData();
    }

    /**
     * 初始化用户中心类里面的UserData成员
     */
    private void initUserData() {
        userData.setUser_id(app.getPreferences().getString("id", ""));
        userData.setUser_desc(app.getPreferences().getString("desc", ""));
        userData.setWeibo(app.getPreferences().getString("weibo", ""));
        userData.setWeixin(app.getPreferences().getString("weixin", ""));
        userData.setQq(app.getPreferences().getString("qq", ""));
        userData.setCity(app.getPreferences().getString("city", ""));
        userData.setEmail(app.getPreferences().getString("email", ""));
        userData.setMobile(app.getPreferences().getString("mobile", ""));
        userData.setUser_nickname(app.getPreferences().getString("alias", ""));
    }

    /**
     * 修改用户数据
     */
    private void changeInfo() {
        mSendBtn = (Button) findViewById(R.id.btn_send);
        mChangeInfoEt = (EditText) findViewById(R.id.et_info);
        Intent intent = getIntent();
        final int item = intent.getIntExtra("item", 0);
        mSendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                info = mChangeInfoEt.getText().toString();
                switch (item) {
                    case ChangeUserInfoActivity.CHANGE_ALIAS:
                        userData.setUser_nickname(info);
                        break;
                    case ChangeUserInfoActivity.CHANGE_CITY:
                        userData.setCity(info);
                        break;
                    case ChangeUserInfoActivity.CHANGE_EMAIL:
                        userData.setEmail(info);
                        break;
                    case ChangeUserInfoActivity.CHANGE_MOBILE:
                        userData.setMobile(info);
                        break;
                    case ChangeUserInfoActivity.CHANGE_WEIBO:
                        userData.setWeibo(info);
                        break;
                    case ChangeUserInfoActivity.CHANGE_WEIXIN:
                        userData.setWeixin(info);
                        break;
                    case ChangeUserInfoActivity.CHANGE_QQ:
                        userData.setQq(info);
                        break;
                    case ChangeUserInfoActivity.CHANGE_DESC:
                        userData.setUser_desc(info);
                        break;
                }
                System.out.println(info);
                UserCenter.getInstance().setUserData(userData);
                UserCenter.getInstance().editUserInfo();
                mChangeInfoEt.setText("");
            }
        });
    }

    /**
     * 修改数据后更新SharePreference
     */
    private void resetData() {
        UserData userData = UserCenter.getInstance().getUserData();
        System.out.println(userData);
        app.getEditor().putString("city", userData.getCity());
        app.getEditor().putString("alias", userData.getUser_nickname());
        app.getEditor().putString("email", userData.getEmail());
        app.getEditor().putString("qq", userData.getQq());
        app.getEditor().putString("weixin", userData.getWeixin());
        app.getEditor().putString("weibo", userData.getWeibo());
        app.getEditor().putString("mobile", userData.getMobile());
        app.getEditor().putString("desc", userData.getUser_desc());
        app.getEditor().commit();
    }


    /**
     * 修改成功时调用
     *
     * @param rel
     */
    @Override
    public void changeSuccess(String rel) {
        resetData();
        setResult(RESULT_OK);
        finish();
    }

    /**
     * 修改失败时回调
     */
    @Override
    public void changeError() {

    }
}
