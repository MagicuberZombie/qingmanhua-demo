package com.qf.demo.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.qf.demo.R;
import com.qf.demo.activity.CataLogActivity;
import com.qf.demo.activity.MyApp;
import com.qf.demo.adapter.RecommendListAdapter;
import com.qf.demo.entities.ComicData;
import com.qf.demo.entities.MonthRankData;
import com.qf.demo.factory.MonthRankDataFactory;
import com.qf.demo.http.HttpReqData;

import java.util.ArrayList;
import java.util.List;

/**
 * 排行页面
 */
public class RankFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener {


    private ImageView mTop;
    private ImageView mBottom;
    private ListView mRankList;
    private RecommendListAdapter adapter;
    private List<ComicData> mData = new ArrayList<>();
    private MonthRankData monthRankData;
    private MyApp app;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rank, container, false);
        initView(view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        app = (MyApp) getActivity().getApplication();
        initData();
        setListener();
        setAdapter();
    }

    /**
     * 记录点击次数
     */
    private int clickNum = 0;

    /**
     * 设置监听
     */
    private void setListener() {
        mBottom.setOnClickListener(this);
    }

    /**
     * 初始化适配器
     */
    private void setAdapter() {

    }

    /**
     * 初始化数据
     */
    private void initData() {
        adapter = new RecommendListAdapter(getActivity(), mData);
        mRankList.setAdapter(adapter);
        MonthRankDataFactory.getInstance().setCallback(new MonthRankDataFactory
                .FetchMonthRankDataCallBack() {
            @Override
            public void success(MonthRankData data) {
                monthRankData = data;
                initRankList();
            }

            @Override
            public void error() {
            }
        });
    }

    /**
     * 初始化排行列表
     */
    private void initRankList() {
        mData.addAll(monthRankData.getRank_comic_info());
        adapter.notifyDataSetChanged();
    }

    /**
     * 寻找控件
     *
     * @param view
     */
    private void initView(View view) {
        mTop = (ImageView) view.findViewById(R.id.iv_top);
        mBottom = (ImageView) view.findViewById(R.id.iv_bottom);
        mRankList = (ListView) view.findViewById(R.id.lv_list);
        mRankList.setOnItemClickListener(this);
    }

    /**
     * 处理点击事件 点击控制最热和排行的切换
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        clickNum++;
        if (clickNum % 2 == 1) {
            mData.clear();
            mData.addAll(monthRankData.getNewest_comic_list());
            adapter.notifyDataSetChanged();
            mTop.setImageResource(R.drawable.top_new_pressed);
            mBottom.setImageResource(R.drawable.crunchies_on);
        } else {
            mData.clear();
            mData.addAll(monthRankData.getRank_comic_info());
            adapter.notifyDataSetChanged();
            mTop.setImageResource(R.drawable.crunchies_pressed);
            mBottom.setImageResource(R.drawable.top_new_on);
        }
    }

    /**
     * 点击漫画条目阅读
     *
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getActivity(), CataLogActivity.class);
        String uid = app.getPreferences().getString("id", "0");
        RecommendListAdapter.ViewHolder holder = (RecommendListAdapter.ViewHolder) view.getTag();
        String comicId = (String) holder.comicName.getTag();
        HttpReqData.getInstance().getReqComicInfo(uid, comicId);
        startActivity(intent);
    }
}
