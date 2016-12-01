package com.qf.demo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.qf.demo.R;

/**
 * 修改个人信息的页面
 */
public class ChangeUserInfoActivity extends AppCompatActivity implements
        View.OnClickListener {

    private ImageView mPortrait;
    private TextView mAlias;
    private TextView mDesc;
    private TextView mCity;
    private TextView mQq;
    private TextView mWeixin;
    private TextView mWeibo;
    private TextView mEmail;
    private TextView mMobile;

    private TextView mTitleName;

    private RelativeLayout mRelPortrait;
    private RelativeLayout mRelAlias;
    private RelativeLayout mRelDesc;
    private RelativeLayout mRelCity;
    private RelativeLayout mRelQq;
    private RelativeLayout mRelWeixin;
    private RelativeLayout mRelEmail;
    private RelativeLayout mRelMobile;
    private RelativeLayout mRelWeibo;

    private Button mBackBtn;

    private MyApp app;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_user_info);
        app = (MyApp) getApplication();
        init();
    }

    /**
     * 初始化工作
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
        mBackBtn.setOnClickListener(this);

        mRelQq.setOnClickListener(this);
        mRelWeibo.setOnClickListener(this);
        mRelPortrait.setOnClickListener(this);
        mRelWeixin.setOnClickListener(this);
        mRelEmail.setOnClickListener(this);
        mRelAlias.setOnClickListener(this);
        mRelDesc.setOnClickListener(this);
        mRelCity.setOnClickListener(this);
        mRelMobile.setOnClickListener(this);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        setTextView();
        mTitleName.setText("编辑个人信息");
    }

    /**
     * 给文本控件设置数据
     */
    private void setTextView() {
        String portrait = app.getPreferences().getString("portrait", "");
        Glide.with(ChangeUserInfoActivity.this).load(portrait)
                .asBitmap().skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(mPortrait);
        String alias = app.getPreferences().getString("alias", "");
        mAlias.setText(alias);
        String desc = app.getPreferences().getString("desc", "");
        mDesc.setText(desc);
        String city = app.getPreferences().getString("city", "");
        mCity.setText(city);
        String qq = app.getPreferences().getString("qq", "");
        mQq.setText(qq);
        String weixin = app.getPreferences().getString("weixin", "");
        mWeixin.setText(weixin);
        String weibo = app.getPreferences().getString("weibo", "");
        mWeibo.setText(weibo);
        String email = app.getPreferences().getString("email", "");
        mEmail.setText(email);
        String mobile = app.getPreferences().getString("mobile", "");
        mMobile.setText(mobile);
    }

    /**
     * 寻找控件
     */
    private void initView() {
        mPortrait = (ImageView) findViewById(R.id.iv_change_head);
        mAlias = (TextView) findViewById(R.id.tv_change_nickname);
        mDesc = (TextView) findViewById(R.id.tv_change_profile);
        mCity = (TextView) findViewById(R.id.tv_city);
        mQq = (TextView) findViewById(R.id.tv_qq);
        mWeixin = (TextView) findViewById(R.id.tv_weixin);
        mWeibo = (TextView) findViewById(R.id.tv_weibo);
        mEmail = (TextView) findViewById(R.id.tv_email);
        mMobile = (TextView) findViewById(R.id.tv_mobile);
        mBackBtn = (Button) findViewById(R.id.btn_back);

        mTitleName = (TextView) findViewById(R.id.tv_title_name);

        mRelAlias = (RelativeLayout) findViewById(R.id.relative_change_name);
        mRelCity = (RelativeLayout) findViewById(R.id.relative_city);
        mRelDesc = (RelativeLayout) findViewById(R.id.relative_change_info);
        mRelEmail = (RelativeLayout) findViewById(R.id.relative_email);
        mRelMobile = (RelativeLayout) findViewById(R.id.relative_mobile);
        mRelQq = (RelativeLayout) findViewById(R.id.relative_qq);
        mRelWeixin = (RelativeLayout) findViewById(R.id.relative_weixin);
        mRelWeibo = (RelativeLayout) findViewById(R.id.relative_weibo);
        mRelPortrait = (RelativeLayout) findViewById(R.id.relative_change_head);
    }

    //给不同的修改设置编号
    public static final int CHANGE_ALIAS = 0;
    public static final int CHANGE_EMAIL = 1;
    public static final int CHANGE_CITY = 2;
    public static final int CHANGE_MOBILE = 3;
    public static final int CHANGE_WEIXIN = 4;
    public static final int CHANGE_WEIBO = 5;
    public static final int CHANGE_QQ = 6;
    public static final int CHANGE_DESC = 7;

    private static final int REQUEST_CHANGE = 8;
    private static final int REQUEST_CHANGE_PORTRAIT = 9;

    /**
     * 处理点击事件
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(ChangeUserInfoActivity.this, InputUserInfoActivity.class);
        switch (v.getId()) {
            case R.id.btn_back:
                setResult(RESULT_OK);
                finish();
                return;
            case R.id.relative_change_head:
                Intent changeHeadIntent = new Intent(ChangeUserInfoActivity.this, ChangePortraitActivity
                        .class);
                startActivityForResult(changeHeadIntent, REQUEST_CHANGE_PORTRAIT);
                return;
            case R.id.relative_change_info:
                intent.putExtra("item", CHANGE_DESC);
                break;
            case R.id.relative_city:
                intent.putExtra("item", CHANGE_CITY);
                break;
            case R.id.relative_mobile:
                intent.putExtra("item", CHANGE_MOBILE);
                break;
            case R.id.relative_qq:
                intent.putExtra("item", CHANGE_QQ);
                break;
            case R.id.relative_email:
                intent.putExtra("item", CHANGE_DESC);
                break;
            case R.id.relative_weibo:
                intent.putExtra("item", CHANGE_WEIBO);
                break;
            case R.id.relative_weixin:
                intent.putExtra("item", CHANGE_WEIXIN);
                break;
            case R.id.relative_change_name:
                intent.putExtra("item", CHANGE_ALIAS);
                break;
        }
        startActivityForResult(intent, REQUEST_CHANGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CHANGE && resultCode == RESULT_OK) {
            setTextView();
        }
        if (requestCode == REQUEST_CHANGE_PORTRAIT && resultCode == RESULT_OK) {
            setTextView();
        }
    }

    /**
     * 按键时回调
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {//按返回键时
            setResult(RESULT_OK);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
