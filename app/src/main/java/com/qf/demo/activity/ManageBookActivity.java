package com.qf.demo.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.qf.demo.R;
import com.qf.demo.adapter.ManageListAdapter;
import com.qf.demo.db.DBManager;
import com.qf.demo.entities.History;
import com.qf.demo.entities.SubData;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * 历史和收藏数据的管理页面
 */
public class ManageBookActivity extends AppCompatActivity implements View.OnClickListener
        , ManageListAdapter.ClickDeleteListener {

    private TextView mTitle;
    private Button mBackBtn;

    private DBManager mDBManager;
    private List<History> mListData = new ArrayList<>();

    private ListView mManageList;
    private ManageListAdapter adapter;
    private TextView mSelectedChoice;
    private int type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_book);
        init();
    }

    /**
     * 初始化工作
     */
    private void init() {
        initView();
        initData();
        setListener();
        setAdapter();
    }

    /**
     * 设置适配器
     */
    private void setAdapter() {
        adapter = new ManageListAdapter(ManageBookActivity.this, mListData);
        mManageList.setAdapter(adapter);
    }

    /**
     * 设置监听
     */
    private void setListener() {
        mBackBtn.setOnClickListener(this);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        mDBManager = DBManager.getInstance(ManageBookActivity.this);
        Intent intent = getIntent();
        type = intent.getIntExtra("type", 0);
        if (type == 0) {
            mListData = mDBManager.queryAll();
            mTitle.setText("历史");
        } else if (type == 1) {
            List<SubData> subDataList = mDBManager.queryForRealSub();//取巧的思路 下次避免
            subToHistory(subDataList);
            mTitle.setText("收藏");
        }
    }

    /**
     * 取巧的办法 下次不要这么写
     */
    private void subToHistory(List<SubData> subDataList) {
        mListData.clear();
        List<History> list = new ArrayList<>();
        for (int i = 0; i < subDataList.size(); i++) {
            History item = new History();
            SubData data = subDataList.get(i);
            item.setIsSub(data.isSub());
            item.setComicCover(data.getComicCover());
            item.setComicAuthor(data.getComicAuthor());
            item.setOrderId(data.getOrderId());
            item.setComicId(data.getComicId());
            item.setComicName(data.getComicName());
            item.setComicPagNum(data.getComicName());
            list.add(item);
        }
        mListData.addAll(list);
    }

    /**
     * 寻找视图
     */
    private void initView() {
        mTitle = (TextView) findViewById(R.id.tv_title_name);
        mBackBtn = (Button) findViewById(R.id.btn_back);
        mManageList = (ListView) findViewById(R.id.list_manage);
        mSelectedChoice = (TextView) findViewById(R.id.select_choice);
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

    private int clickNum = 0;

    /**
     * 处理下面选项的点击事件
     *
     * @param view
     */
    public void myClick(View view) {
        switch (view.getId()) {
            case R.id.choice_delete:
                deleteCheckedItem();
                break;
            case R.id.choice_exit:
                finish();
                break;
            case R.id.select_choice:
                clickNum++;
                if (clickNum % 2 == 1) {
                    mSelectedChoice.setText("取消全选");
                    adapter.setAllChecked(true);
                } else {
                    mSelectedChoice.setText("全选");
                    adapter.setAllChecked(false);
                }
                break;
        }
    }

    /**
     * 删除被check的项
     */
    private void deleteCheckedItem() {
        for (int i = 0; i < mManageList.getCount(); i++) {
            View view = adapter.getView(i, null, null);
            ManageListAdapter.ViewHolder holder = (ManageListAdapter.ViewHolder)
                    view.getTag();
            if (holder.mCheckBox.isChecked()) {
                String comicId = (String) holder.mCheckBox.getTag();
                mDBManager.deleteOneData(comicId, type);
            }
        }
        resetList();
    }

    /**
     * 删除数据后更新列表
     */
    private void resetList() {
        mListData.clear();
        if (type == 0) {
            mListData.addAll(mDBManager.queryAll());
        } else if (type == 1) {
            List<SubData> subDataList = mDBManager.queryForRealSub();
            subToHistory(subDataList);
        }
        adapter.notifyDataSetChanged();
    }

    /**
     * 点击删除键
     *
     * @param view
     */
    @Override
    public void click(View view) {
        String comicId = (String) view.getTag();
        mDBManager.deleteOneData(comicId, type);
        resetList();
    }
}
