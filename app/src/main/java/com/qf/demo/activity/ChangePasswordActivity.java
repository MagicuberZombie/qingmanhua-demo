package com.qf.demo.activity;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.qf.demo.R;
import com.qf.demo.user.UserCenter;
import com.qf.demo.utils.Utils;

/**
 * 修改密码的页面
 */
public class ChangePasswordActivity extends AppCompatActivity implements View.OnClickListener, TextWatcher, UserCenter.UserChangePwdCallBack {

    private TextView mTitleName;
    private Button mBackBtn;

    private EditText mOldPwdEt;
    private EditText mNewPwdEt;
    private EditText mConfirmPwdEt;

    private ImageView mOldPwdTrue;
    private ImageView mNewPwdTrue;
    private ImageView mConfirmPwdTrue;

    private Button mConfirmBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        init();
    }

    /**
     * 初始化工作
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
        mTitleName.setText("修改密码");
    }

    /**
     * 设置监听
     */
    private void setListener() {
        mConfirmBtn.setOnClickListener(this);
        mBackBtn.setOnClickListener(this);
        mConfirmPwdEt.addTextChangedListener(this);

        UserCenter.getInstance().setUserChangePwdCallBack(this);
    }

    /**
     * 初始化控件
     */
    private void initView() {
        mTitleName = (TextView) findViewById(R.id.tv_title_name);
        mBackBtn = (Button) findViewById(R.id.btn_back);

        mOldPwdEt = (EditText) findViewById(R.id.et_old_password);
        mNewPwdEt = (EditText) findViewById(R.id.et_new_password);
        mConfirmPwdEt = (EditText) findViewById(R.id.et_confirm_password);

        mConfirmBtn = (Button) findViewById(R.id.btn_confirm);

        mOldPwdTrue = (ImageView) findViewById(R.id.iv_old_password);
        mNewPwdTrue = (ImageView) findViewById(R.id.iv_new_password);
        mConfirmPwdTrue = (ImageView) findViewById(R.id.iv_confirm_password);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.btn_confirm:
                String oldPwd = mOldPwdEt.getText().toString();
                String newPwd = mNewPwdEt.getText().toString();
                String confirmPwd = mConfirmPwdEt.getText().toString();
                if (Utils.getInstance().textIsNull(oldPwd, newPwd, confirmPwd)) {
                    Utils.getInstance().toast("输入不能为空", ChangePasswordActivity.this);
                } else {
                    UserCenter.getInstance().editPwd(oldPwd, newPwd);
                }
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        String newPwd = mNewPwdEt.getText().toString();
        if (!TextUtils.isEmpty(newPwd)) {
            if (!s.toString().equals(newPwd) && s.length() == newPwd.length()) {
                Utils.getInstance().toast("密码不一致", ChangePasswordActivity.this);
            }
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
    }


    private Handler mHandler = new MyHandler();

    private class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0x123:
                    Utils.getInstance().toast("密码修改成功", ChangePasswordActivity.this);
                    break;
                case 0x100:
                    Utils.getInstance().toast("密码修改失败", ChangePasswordActivity.this);
                    break;
            }
        }
    }

    /**
     * 修改密码成功时回调
     */
    @Override
    public void onChangePwdComplete() {
        mHandler.sendEmptyMessage(0x123);
        finish();
    }

    /**
     * 修改密码失败时回调
     *
     * @param str
     */
    @Override
    public void onChangePwdErr(String str) {
        mHandler.sendEmptyMessage(0x100);
    }
}
