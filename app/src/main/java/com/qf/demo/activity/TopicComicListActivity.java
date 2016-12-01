
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
import com.qf.demo.adapter.RecommendListAdapter;
import com.qf.demo.entities.ComicData;
import com.qf.demo.entities.TopicComicData;
import com.qf.demo.factory.TopicComicDataFactory;
import com.qf.demo.http.HttpReqData;

import java.util.ArrayList;
import java.util.List;

/**
 * 专题漫画列表页面
 */
public class TopicComicListActivity extends AppCompatActivity implements
        TopicComicDataFactory.GetTopicComicCallBack, View.OnClickListener, AdapterView.OnItemClickListener {

    private ListView mTopicList;
    private TextView mTitle;
    private Button mBackBtn;

    private MyApp app;

    private RecommendListAdapter adapter;
    private List<ComicData> mComicData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_comic_list);
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
        mTopicList.setOnItemClickListener(this);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        TopicComicDataFactory.getInstance().setCallBack(this);
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        mTitle.setText(title);

        adapter = new RecommendListAdapter(TopicComicListActivity.this, mComicData);
        mTopicList.setAdapter(adapter);
    }

    /**
     * 寻找控件
     */
    private void initView() {
        mTopicList = (ListView) findViewById(R.id.list_topic);
        mTitle = (TextView) findViewById(R.id.tv_title_name);
        mBackBtn = (Button) findViewById(R.id.btn_back);
    }

    /**
     * 获取数据成功时调用
     *
     * @param data
     */
    @Override
    public void getTopicComic(TopicComicData data) {
        mComicData.addAll(data.getComic_arr());
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
     * 点击漫画条目看漫画
     *
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(TopicComicListActivity.this, CataLogActivity.class);
        String uid = app.getPreferences().getString("id", "0");
        String comicId = mComicData.get(position).getId();
        HttpReqData.getInstance().getReqComicInfo(uid, comicId);
        intent.putExtra("id", uid);
        startActivity(intent);
    }
}
