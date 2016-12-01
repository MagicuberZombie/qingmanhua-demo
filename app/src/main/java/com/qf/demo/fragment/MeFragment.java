package com.qf.demo.fragment;


import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.qf.demo.activity.ChangeUserInfoActivity;
import com.qf.demo.activity.LoginActivity;
import com.qf.demo.activity.MyApp;
import com.qf.demo.R;
import com.qf.demo.activity.SettingActivity;
import com.qf.demo.utils.Utils;
import com.qf.demo.view.SelectableRoundedImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class MeFragment extends Fragment implements View.OnClickListener {

    private RelativeLayout mTitle;
    private RelativeLayout mHead;
    private Button mMessageBtn;
    private Button mGameCenterBtn;
    private Button mSetupBtn;
    private Button mFeedBackBtn;
    private Button mContactUsBtn;
    private Button mAboutBtn;
    private TextView mAlias;
    private TextView mAccountSource;

    private SelectableRoundedImageView mPortrait;

    public MyApp app;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_me, container, false);
        initView(view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        app = (MyApp) getActivity().getApplication();
        initData();
        setListener();
    }

    /**
     * 设置监听
     */
    private void setListener() {
        mHead.setOnClickListener(this);
        mSetupBtn.setOnClickListener(this);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        boolean isLogin = app.getPreferences().getBoolean("isLogin", false);
        if (isLogin) {
            String portrait = app.getPreferences().getString("portrait", "");
            Glide.with(getActivity()).load(portrait).asBitmap()
                    .diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).into(mPortrait);

            String alias = app.getPreferences().getString("alias", "");
            mAlias.setText(alias);

            mAccountSource.setText("轻漫画账号登录");

        }

    }

    /**
     * 寻找控件
     *
     * @param view
     */
    private void initView(View view) {
        mTitle = (RelativeLayout) view.findViewById(R.id.relative_me_title);
        mHead = (RelativeLayout) view.findViewById(R.id.relative_head);
        mMessageBtn = (Button) view.findViewById(R.id.btn_mymessage);
        mFeedBackBtn = (Button) view.findViewById(R.id.btn_feedback);
        mGameCenterBtn = (Button) view.findViewById(R.id.btn_game_center);
        mAboutBtn = (Button) view.findViewById(R.id.btn_about);
        mContactUsBtn = (Button) view.findViewById(R.id.btn_contact_us);
        mSetupBtn = (Button) view.findViewById(R.id.btn_setup);
        mPortrait = (SelectableRoundedImageView) view.findViewById(R.id.iv_head);
        mAlias = (TextView) view.findViewById(R.id.tv_name);
        mAccountSource = (TextView) view.findViewById(R.id.tv_act_source);

        initButton(mMessageBtn, mFeedBackBtn, mGameCenterBtn, mAboutBtn, mContactUsBtn, mSetupBtn);
        initTitle(mTitle);
    }

    /**
     * 设置标题控件
     *
     * @param mTitle
     */
    private void initTitle(RelativeLayout mTitle) {
        TextView textView = (TextView) mTitle.findViewById(R.id.tv_title_name);
        Button button = (Button) mTitle.findViewById(R.id.btn_back);
        textView.setText("个人中心");
        button.setVisibility(View.INVISIBLE);
    }

    /**
     * 调整Button的Drawable图标大小
     *
     * @param buttons
     */
    private void initButton(Button... buttons) {
        for (int i = 0; i < buttons.length; i++) {
            Button button = buttons[i];
            Drawable[] drawables = button.getCompoundDrawables();
            drawables[0].setBounds(0, 0, 100, 100);
            drawables[2].setBounds(0, 0, 100, 100);
            button.setCompoundDrawables(drawables[0], drawables[1], drawables[2], drawables[3]);
        }
    }

    private static final int REQUEST_LOGIN = 0;
    private static final int REQUEST_USERINFO = 1;
    private static final int REQUEST_SETTING = 2;

    /**
     * 跳转页面有返回时调用
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_LOGIN:
                if (resultCode == Activity.RESULT_OK) {
                    if (data != null) {
                        String from = data.getStringExtra("from");
                        Utils.getInstance().toast("登录成功", getActivity());
                        String portrait = app.getPreferences().getString("portrait", "");
                        Glide.with(getActivity()).load(portrait).asBitmap()
                                .diskCacheStrategy(DiskCacheStrategy.NONE).into(mPortrait);
                        String alias = app.getPreferences().getString("alias", "");
                        mAlias.setText(alias);
                        if (from.equals("qingman")) {
                            mAccountSource.setText("轻漫画账号登录");
                        } else if (from.equals("qq")) {
                            mAccountSource.setText("QQ账号登录");

                        } else if (from.equals("weixin")) {
                            mAccountSource.setText("微信账号登录");
                        }
                    }
                }
                break;
            case REQUEST_SETTING:
                if (resultCode == Activity.RESULT_OK) {
                    boolean isLogin = app.getPreferences().getBoolean("isLogin", false);
                    System.out.println(isLogin);
                    if (!isLogin) {
                        mPortrait.setImageResource(R.drawable.user_head_icon);
                        mAccountSource.setText("");
                        mAlias.setText("登录/注册");
                    }
                }
                break;
            case REQUEST_USERINFO:
                if (resultCode == Activity.RESULT_OK) {
                    String portrait = app.getPreferences().getString("portrait", "");
                    Glide.with(getActivity()).load(portrait).asBitmap()
                            .diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).into(mPortrait);
                    String alias = app.getPreferences().getString("alias", "");
                    mAlias.setText(alias);
                    mAccountSource.setText("轻漫画账号登录");
                }
        }
    }

    /**
     * 处理点击事件
     */
    @Override
    public void onClick(View v) {
        Class<?> toClass = LoginActivity.class;
        int request = 0;
        switch (v.getId()) {
            case R.id.relative_head:
                boolean isLogin = app.getPreferences().getBoolean("isLogin", false);
                if (!isLogin) {
                    toClass = LoginActivity.class;
                    request = REQUEST_LOGIN;
                } else {
                    toClass = ChangeUserInfoActivity.class;
                    request = REQUEST_USERINFO;
                }
                break;
            case R.id.btn_setup:
                toClass = SettingActivity.class;
                request = REQUEST_SETTING;
                break;
        }
        Intent intent = new Intent(getActivity(), toClass);
        startActivityForResult(intent, request);
    }
}
