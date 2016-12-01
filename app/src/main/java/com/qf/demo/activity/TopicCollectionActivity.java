package com.qf.demo.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.qf.demo.R;
import com.qf.demo.adapter.TopicCollectionAdapter;
import com.qf.demo.entities.TopicCollection;
import com.qf.demo.entities.TopicCollectionData;
import com.qf.demo.factory.TopicCollectionFactory;
import com.qf.demo.http.HttpReq;
import com.qf.demo.http.HttpReqData;

import java.util.ArrayList;
import java.util.List;

/**
 * 专题合集页面
 */
public class TopicCollectionActivity extends AppCompatActivity implements TopicCollectionFactory.GetTopicCollectionCallBack, View.OnClickListener, AdapterView.OnItemClickListener {

    private ListView mCollection;
    private TextView mTitle;
    private Button mBackBtn;

    private TopicCollectionAdapter adapter;
    private List<TopicCollectionData> mListData = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_collection);
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
        mCollection.setOnItemClickListener(this);
    }

    /**
     * 初始化工作
     */
    private void initData() {
        mTitle.setText("专题合集");

        TopicCollectionFactory.getInstance().setCallBack(this);
        adapter = new TopicCollectionAdapter(TopicCollectionActivity.this,
                mListData);
        mCollection.setAdapter(adapter);
    }

    /**
     * 初始化视图
     */
    private void initView() {
        mCollection = (ListView) findViewById(R.id.list_collection);
        mTitle = (TextView) findViewById(R.id.tv_title_name);
        mBackBtn = (Button) findViewById(R.id.btn_back);
    }

    /**
     * 成功获取数据时回调
     *
     * @param collection
     */
    @Override
    public void getTopicCollection(TopicCollection collection) {
        mListData.addAll(collection.getTopic_list());
        adapter.notifyDataSetChanged();
    }

    /**
     * 获取数据失败时回调
     */
    @Override
    public void getError() {

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
     * 点击专题
     *
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String topicId = mListData.get(position).getId();
        String topicName = mListData.get(position).getTopic_name();
        HttpReqData.getInstance().getTopicComicList(topicId);
        Intent intent = new Intent(TopicCollectionActivity.this, TopicComicListActivity.class);
        intent.putExtra("title", topicName);
        startActivity(intent);
    }
}
