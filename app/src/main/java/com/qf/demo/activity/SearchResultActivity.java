package com.qf.demo.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qf.demo.R;
import com.qf.demo.adapter.RecommendListAdapter;
import com.qf.demo.db.DBManager;
import com.qf.demo.entities.ComicData;
import com.qf.demo.entities.HistoryTag;
import com.qf.demo.entities.SearchResult;
import com.qf.demo.factory.SearchResultFactory;
import com.qf.demo.http.HttpReqData;

import java.util.ArrayList;
import java.util.List;

public class SearchResultActivity extends AppCompatActivity implements
        View.OnClickListener, SearchResultFactory.GetSearchResultCallBack, AdapterView.OnItemClickListener {

    private ListView mResultList;
    private TextView mLeftTv;
    private TextView mRightTv;
    private TextView mKeyWordTv;

    private EditText mSearchEt;
    private Button mSearchBtn;
    private Button mBackBtn;

    private List<ComicData> mResultData = new ArrayList<>();
    private RecommendListAdapter adapter;

    private RelativeLayout mRelayout;
    private ImageView mDeleteIv;

    private MyApp app;
    private String keyword;

    private DBManager mDBManager;
    private String uid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
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
        mSearchBtn.setOnClickListener(this);
        mDeleteIv.setOnClickListener(this);
        mResultList.setOnItemClickListener(this);
    }

    /**
     * 初始化控件
     */
    private void initData() {
        mDBManager = DBManager.getInstance(SearchResultActivity.this);
        SearchResultFactory.getInstance().setCallBack(this);
        adapter = new RecommendListAdapter(SearchResultActivity.this, mResultData);
        mResultList.setAdapter(adapter);
        Intent intent = getIntent();
        keyword = intent.getStringExtra("keyword");
        mLeftTv.setText("对\"");
        mKeyWordTv.setText(keyword);
        mRightTv.setText("\"的搜索结果如下:");
    }

    /**
     * 寻找控件
     */
    private void initView() {
        mRelayout = (RelativeLayout) findViewById(R.id.relative_back);
        mRelayout.setVisibility(View.VISIBLE);

        mSearchEt = (EditText) findViewById(R.id.et_search);
        mBackBtn = (Button) findViewById(R.id.btn_back);
        mSearchBtn = (Button) findViewById(R.id.btn_search);

        mResultList = (ListView) findViewById(R.id.result_list);

        mLeftTv = (TextView) findViewById(R.id.left_text);
        mRightTv = (TextView) findViewById(R.id.right_text);
        mKeyWordTv = (TextView) findViewById(R.id.keyword);

        mDeleteIv = (ImageView) findViewById(R.id.iv_delete);
    }

    /**
     * 处理点击事件
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        uid = app.getPreferences().getString("id", "0");
        switch (v.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.btn_search:
                keyword = mSearchEt.getText().toString();
                HistoryTag tag = new HistoryTag();
                tag.setTag(keyword);
                mDBManager.insertTag(tag);
                HttpReqData.getInstance().getSearchResult(uid, keyword, "0");
                break;
            case R.id.iv_delete:
                mSearchEt.setText("");
                break;
        }
    }

    /**
     * 获取数据成功时回调
     *
     * @param result
     */
    @Override
    public void getSearchResult(SearchResult result) {
        if (mResultData.size() != 0) {
            mResultData.clear();
        }
        mResultData.addAll(result.getComic_arr());
        adapter.notifyDataSetChanged();
        mKeyWordTv.setText(keyword);
    }

    /**
     * 没有相关数据时回调
     *
     * @param result
     */
    @Override
    public void getNoResult(SearchResult result) {

    }

    /**
     * 数据获取失败时回调
     */
    @Override
    public void getError() {

    }

    /**
     * 点击漫画条目
     *
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(SearchResultActivity.this, CataLogActivity.class);
        String comicId = mResultData.get(position).getId();
        HttpReqData.getInstance().getReqComicInfo(uid, comicId);
        intent.putExtra("id", uid);
        startActivity(intent);
    }
}
