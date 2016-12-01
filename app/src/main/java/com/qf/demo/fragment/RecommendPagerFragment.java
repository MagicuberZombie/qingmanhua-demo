package com.qf.demo.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioGroup;

import com.qf.demo.R;
import com.qf.demo.adapter.MyFragmentPagerAdapter;
import com.qf.demo.view.MyViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * 用于控制显示推荐和排行的碎片
 */
public class RecommendPagerFragment extends Fragment
        implements RadioGroup.OnCheckedChangeListener, ViewPager.OnPageChangeListener {


    private MyViewPager mViewPager;
    private RadioGroup mRadioTitle;
    private ImageView mLeftSelected;
    private ImageView mRightSelected;
    private List<Fragment> fragmentList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recommend_pager, container, false);
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

    private void initAdapter() {
        mViewPager.setAdapter(new MyFragmentPagerAdapter(getChildFragmentManager(), fragmentList));
    }

    private void setListener() {
        mRadioTitle.setOnCheckedChangeListener(this);
        mViewPager.addOnPageChangeListener(this);
    }

    private void initData() {
        fragmentList.add(new RecommendFragment());
        fragmentList.add(new RankFragment());
        mViewPager.setCurrentItem(0);
    }

    private void initView(View view) {
        mViewPager = (MyViewPager) view.findViewById(R.id.view_page);
        mViewPager.setOffscreenPageLimit(3);
        mRadioTitle = (RadioGroup) view.findViewById(R.id.radio_title);
        mLeftSelected = (ImageView) view.findViewById(R.id.left_selected);
        mRightSelected = (ImageView) view.findViewById(R.id.right_selected);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.left_radio:
                mViewPager.setCurrentItem(0);
                mLeftSelected.setVisibility(View.VISIBLE);
                mRightSelected.setVisibility(View.INVISIBLE);
                break;
            case R.id.right_radio:
                mViewPager.setCurrentItem(1);
                mLeftSelected.setVisibility(View.INVISIBLE);
                mRightSelected.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {


    }

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
}
