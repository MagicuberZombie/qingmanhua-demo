package com.qf.demo.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.qf.demo.R;
import com.qf.demo.adapter.MyAnimatedAdapter;
import com.qf.demo.entities.ComicData;
import com.qf.demo.entities.WeeklyUpdateData;
import com.qf.demo.factory.WeeklyUpdateDataFactory;
import com.qf.demo.http.HttpReqData;
import com.qf.demo.view.AnimatedExpandableListView;

import java.util.ArrayList;
import java.util.List;

/**
 * 一周追番的推荐
 */
public class WeeklyUpdateActivity extends AppCompatActivity implements
        View.OnClickListener, WeeklyUpdateDataFactory.GetWeeklyDataCallBack,
        ExpandableListView.OnGroupClickListener {

    private Button mBackBtn;
    private TextView mTitle;

    private AnimatedExpandableListView mListView;
    private AnimatedExpandableListView.AnimatedExpandableListAdapter adapter;

    private List<List<ComicData>> mWeeklyData = new ArrayList<>();

    private MyApp app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weekly_update);
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
        mListView.setOnGroupClickListener(this);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        HttpReqData.getInstance().getWeeklyUpdate();
        WeeklyUpdateDataFactory.getInstance().setCallBack(this);

    }

    /**
     * 寻找控件
     */
    private void initView() {
        mBackBtn = (Button) findViewById(R.id.btn_back);
        mTitle = (TextView) findViewById(R.id.tv_title_name);
        mTitle.setText("一周追番");
        mListView = (AnimatedExpandableListView) findViewById(R.id.exp_list_view);
    }

    /**
     * 处理点击事件
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                finish();
                break;
        }
    }

    /**
     * 获取到一周追番数据时回调
     *
     * @param data
     */
    @Override
    public void getWeekly(WeeklyUpdateData data) {
        String id = app.getPreferences().getString("id", "0");
        mWeeklyData = data.getWeek_update_comic_arr();
        adapter = new MyAnimatedAdapter(WeeklyUpdateActivity.this
                , mWeeklyData, data.getWeek_update_pic_arr(),id);
        mListView.setAdapter(adapter);
    }

    /**
     * 获取数据失败时回调
     */
    @Override
    public void getError() {

    }

    /**
     * 处理点击Group事件
     *
     * @param parent
     * @param v
     * @param groupPosition
     * @param id
     * @return
     */
    @Override
    public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
        if (mListView.isGroupExpanded(groupPosition)) {//处理展开和关闭的逻辑
            mListView.collapseGroupWithAnimation(groupPosition);
        } else {
            mListView.expandGroupWithAnimation(groupPosition);
        }
        return true;
    }

}
