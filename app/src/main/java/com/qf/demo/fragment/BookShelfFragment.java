package com.qf.demo.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;

import com.qf.demo.R;
import com.qf.demo.activity.ManageBookActivity;
import com.qf.demo.adapter.MyFragmentPagerAdapter;
import com.qf.demo.view.MyViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * 书柜的Fragment
 */
public class BookShelfFragment extends Fragment
        implements RadioGroup.OnCheckedChangeListener,
        ViewPager.OnPageChangeListener, View.OnClickListener {

    private MyViewPager mViewPager;
    private RadioGroup mRadioTitle;
    private ImageView mLeftSelected;
    private ImageView mRightSelected;

    private Button mManageBtn;
    private List<Fragment> fragmentList = new ArrayList<>();

    //选择的是历史还是收藏
    private int type = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book_shelf, container, false);
        initView(view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setListener();
        initData();
        initAdapter();
    }

    /**
     * 初始化适配器
     */
    private void initAdapter() {
        mViewPager.setAdapter(new MyFragmentPagerAdapter(getChildFragmentManager(), fragmentList));
    }

    /**
     * 设置监听
     */
    private void setListener() {
        mRadioTitle.setOnCheckedChangeListener(this);
        mViewPager.addOnPageChangeListener(this);
        mManageBtn.setOnClickListener(this);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        fragmentList.add(new HistoryFragment());
        fragmentList.add(new SubFragment());
        mViewPager.setCurrentItem(0);
    }

    /**
     * 寻找控件
     *
     * @param view
     */
    private void initView(View view) {
        mViewPager = (MyViewPager) view.findViewById(R.id.view_page);
        mViewPager.setOffscreenPageLimit(3);
        mRadioTitle = (RadioGroup) view.findViewById(R.id.radio_title);
        mLeftSelected = (ImageView) view.findViewById(R.id.left_selected);
        mRightSelected = (ImageView) view.findViewById(R.id.right_selected);
        mManageBtn = (Button) view.findViewById(R.id.btn_manage_book);
    }

    /**
     * 控制切换效果
     *
     * @param group
     * @param checkedId
     */
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.left_radio:
                mViewPager.setCurrentItem(0);
                mLeftSelected.setVisibility(View.VISIBLE);
                mRightSelected.setVisibility(View.INVISIBLE);
                type = 0;
                break;
            case R.id.right_radio:
                mViewPager.setCurrentItem(1);
                mLeftSelected.setVisibility(View.INVISIBLE);
                mRightSelected.setVisibility(View.VISIBLE);
                type = 1;
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {


    }

    /**
     * RadioButton 的变化与ViewPager同步
     *
     * @param position
     */
    @Override
    public void onPageSelected(int position) {
        if (position == 0) {
            mRadioTitle.check(R.id.left_radio);
        } else {
            mRadioTitle.check(R.id.right_radio);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    /**
     * 处理点击事件 打开管理页面
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getActivity(), ManageBookActivity.class);
        intent.putExtra("type", type);
        startActivity(intent);
    }
}
