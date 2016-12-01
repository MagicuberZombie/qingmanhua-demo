package com.qf.demo.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.qf.demo.R;
import com.qf.demo.entities.UserData;
import com.qf.demo.user.UserCenter;
import com.qf.demo.utils.Utils;

/**
 * 注册页面
 */
public class RegisterActivity extends AppCompatActivity
        implements View.OnClickListener, UserCenter.UserRegisterCallBack {

    private Button mRegBtn;
    private Button mBackLoginBtn;

    private EditText mAccountEt;
    private EditText mPasswordEt;

    private CheckBox mCheckBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();
    }

    /**
     * 控制初始化
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
        mRegBtn.setOnClickListener(this);
        mBackLoginBtn.setOnClickListener(this);
        UserCenter.getInstance().setUserRegisterCallBack(this);
    }

    /**
     * 初始化数据
     */
    private void initData() {

    }

    /**
     * 寻找控件
     */
    private void initView() {
        mRegBtn = (Button) findViewById(R.id.btn_reg);
        mBackLoginBtn = (Button) findViewById(R.id.btn_back_login);

        mAccountEt = (EditText) findViewById(R.id.et_act_reg);
        mPasswordEt = (EditText) findViewById(R.id.et_pwd_reg);

        mCheckBox = (CheckBox) findViewById(R.id.checkbox);
        initCheckBox();

        initEditText(mAccountEt, mPasswordEt);
    }

    /**
     * 设置checkbox控件内图标的大小
     */
    private void initCheckBox() {
        Drawable drawable = mCheckBox.getButtonDrawable();
        if (drawable != null) {
            System.out.println("设置");
            drawable.setBounds(0, 0, 100, 100);
            mCheckBox.setButtonDrawable(drawable);
        }
    }

    /**
     * 设置编辑控件内图标的大小
     */
    private void initEditText(EditText... edit) {
        for (int i = 0; i < edit.length; i++) {
            Drawable[] drawables = edit[i].getCompoundDrawables();
            drawables[0].setBounds(0, 0, 150, 150);
            edit[i].setCompoundDrawables(drawables[0], drawables[1], drawables[2]
                    , drawables[3]);
        }
    }

    /**
     * 处理点击事件
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_reg:
                register();
                break;
            case R.id.btn_back_login:
                finish();
                break;
        }
    }

    private void register() {
        System.out.println("注册");
        String account = mAccountEt.getText().toString();
        String password = mPasswordEt.getText().toString();
        boolean isNull = Utils.getInstance().textIsNull(account, password);
        if (isNull) {
            Utils.getInstance().toast("输入不能为空", RegisterActivity.this);
        } else {
            UserCenter.getInstance().userRegister(account, password, RegisterActivity.this);
        }
    }

    /**
     * 点击back回调
     *
     * @param view
     */
    public void clickBack(View view) {
        finish();
    }

    /**
     * 注册失败时回调
     */
    @Override
    public void onRegisterErr() {
        Intent intent = new Intent();
        intent.putExtra("account", "error");
        setResult(RESULT_OK, intent);
        finish();
    }

    /**
     * 注册成功时回调
     *
     * @param data
     */
    @Override
    public void onRegisterSuccess(UserData data) {
        Intent intent = new Intent();
        intent.putExtra("account", data.getAccount());
        setResult(RESULT_OK, intent);
    }
}
