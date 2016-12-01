package com.qf.demo.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.qf.demo.R;
import com.qf.demo.fragment.BookShelfFragment;
import com.qf.demo.fragment.MeFragment;
import com.qf.demo.fragment.RecommendPagerFragment;
import com.qf.demo.fragment.SearchFragment;
import com.qf.demo.http.HttpReqData;
import com.qf.demo.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity
        implements RadioGroup.OnCheckedChangeListener {

    List<Fragment> fragmentList = new ArrayList<>();
    RadioGroup mRadioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        init();
    }

    /**
     * 初始化工作
     */
    private void init() {
        initView();
        initData();
        setListener();
        initFragment();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        //请求数据 包含最新最热漫画 和推荐
        HttpReqData.getInstance().getReqMainData();
        //请求数据 包含漫画最新和排行
        HttpReqData.getInstance().getReqMonthRank();
    }

    /**
     * 初始化碎片
     */
    private void initFragment() {
        fragmentList.add(new RecommendPagerFragment());
        fragmentList.add(new BookShelfFragment());
        fragmentList.add(new SearchFragment());
        fragmentList.add(new MeFragment());

        showFragment(0);
    }

    /**
     * 设置监听
     */
    private void setListener() {
        mRadioGroup.setOnCheckedChangeListener(this);
    }

    /**
     * 初始化视图
     */
    private void initView() {
        mRadioGroup = (RadioGroup) findViewById(R.id.main_radio);
        initRadioGroup();
    }

    /**
     * 调整视图
     */
    private void initRadioGroup() {
        for (int i = 0; i < mRadioGroup.getChildCount(); i++) {
            RadioButton rb = (RadioButton) mRadioGroup.getChildAt(i);
            Drawable[] drawables = rb.getCompoundDrawables();
            drawables[1].setBounds(0, 0, 130, 130);
            rb.setCompoundDrawables(drawables[0], drawables[1], drawables[2], drawables[3]);
        }
    }

    /**
     * 控制碎片的切换
     *
     * @param group
     * @param checkedId
     */
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        for (int i = 0; i < group.getChildCount(); i++) {
            RadioButton rb = (RadioButton) group.getChildAt(i);
            if (rb.isChecked()) {
                showFragment(i);
                break;
            }
        }
    }

    private int lastShownIndex = -1;

    /**
     * 根据序号显示碎片
     *
     * @param i
     */
    private void showFragment(int i) {
        Fragment fragment = fragmentList.get(i);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (lastShownIndex != -1) {
            ft.hide(fragmentList.get(lastShownIndex));
        }
        if (fragment.isAdded()) {
            ft.show(fragment);
        } else {
            ft.add(R.id.fragment_container, fragment);
        }
        ft.commit();
        lastShownIndex = i;
    }

    /**
     * 按返回键时的提示
     *
     * @param keyCode
     * @param event
     * @return
     */
    private int clickNum = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && clickNum == 0) {
            Utils.getInstance().toast("再按一次就退出了哦", HomeActivity.this);
            clickNum++;
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
