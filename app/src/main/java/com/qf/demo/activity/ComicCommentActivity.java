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
import com.qf.demo.adapter.CommentListAdapter;
import com.qf.demo.entities.CommentArr;
import com.qf.demo.entities.CommentData;
import com.qf.demo.factory.CommentDataFactory;
import com.qf.demo.http.HttpReqData;

import java.util.ArrayList;
import java.util.List;

/**
 * 评论页面
 */
public class ComicCommentActivity extends AppCompatActivity
        implements CommentDataFactory.GetCommentArrCallBack, View.OnClickListener, AdapterView.OnItemClickListener {

    private ListView mCommentList;
    private TextView mTitle;
    private Button mBackBtn;
    private String id;
    private String comicId;
    private List<CommentData> commentArr = new ArrayList<>();
    private CommentListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comic_comment);
        init();
    }

    /**
     * 初始化工作
     */
    private void init() {
        intiView();
        initData();
        setLister();
    }

    /**
     * 设置监听
     */
    private void setLister() {
        mBackBtn.setOnClickListener(this);
        mCommentList.setOnItemClickListener(this);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        comicId = intent.getStringExtra("comicId");
        HttpReqData.getInstance().getReqCommentData(id, comicId, "0");

        CommentDataFactory.getInstance().setCallBack(this);
        adapter = new CommentListAdapter
                (ComicCommentActivity.this, commentArr,false);
        mCommentList.setAdapter(adapter);
    }

    /**
     * 寻找控件
     */
    private void intiView() {
        mCommentList = (ListView) findViewById(R.id.lv_comment);
        mTitle = (TextView) findViewById(R.id.tv_title_name);
        mTitle.setText("评论");
        mBackBtn = (Button) findViewById(R.id.btn_back);
    }

    /**
     * 获取到评论数据时回调
     *
     * @param arr
     */
    @Override
    public void getCommentData(CommentArr arr) {
        if (commentArr.size() != 0) {
            commentArr.clear();
        }
        commentArr.addAll(arr.getComment_arr());
        adapter.notifyDataSetChanged();
    }

    /**
     * 获取评论数据失败时回调
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

    //评论的请求码
    private static final int REQUEST_SEND = 1;

    /**
     * 点击编辑评论
     *
     * @param view
     */
    public void clickSendComment(View view) {
        Intent intent = new Intent(ComicCommentActivity.this, InputCommentActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("comicId", comicId);
        intent.putExtra("orderId", "0");
        intent.putExtra("action", "comment");
        startActivityForResult(intent, REQUEST_SEND);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_SEND && resultCode == RESULT_OK) {//发送评论成功后刷新页面
            HttpReqData.getInstance().getReqCommentData(id, comicId, "0");
        }
    }


    /**
     * 点击评论回复或者点赞
     *
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        CommentListAdapter.ViewHolder holder = (CommentListAdapter.ViewHolder) view.getTag();
        String commentId = (String) holder.mComment.getTag();
        Intent intent = new Intent(ComicCommentActivity.this, ReplyCommentActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("commentId", commentId);
        CommentData commentData = commentArr.get(position);
        intent.putExtra("commentData", commentData);
        startActivity(intent);
    }
}
