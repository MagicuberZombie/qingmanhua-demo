package com.qf.demo.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.qf.demo.R;
import com.qf.demo.adapter.ComicListAdapter;
import com.qf.demo.adapter.CommentListAdapter;
import com.qf.demo.entities.CommentArr;
import com.qf.demo.entities.CommentData;
import com.qf.demo.factory.ReplyCommentDataFactory;
import com.qf.demo.http.HttpReqData;
import com.qf.demo.utils.Utils;
import com.qf.demo.view.SelectableRoundedImageView;

import java.util.ArrayList;
import java.util.List;

public class ReplyCommentActivity extends AppCompatActivity implements View.OnClickListener, ReplyCommentDataFactory.GetReplyCommentDataCallBack {

    private Button mBackBtn;
    private TextView mTitle;
    private SelectableRoundedImageView mPortrait;

    private TextView mAlias;
    private TextView mCommentTime;
    private TextView mLikeNum;
    private TextView mReplyNum;
    private TextView mComment;

    private ListView mReplyList;
    private CommentListAdapter adapter;
    private List<CommentData> commentArr = new ArrayList<>();

    private ImageView mSendComment;
    private String id;
    private String commentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reply_comment);
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
     * 初始化数据
     */
    private void initData() {
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        commentId = intent.getStringExtra("commentId");
        CommentData data = (CommentData) intent.getSerializableExtra("commentData");
        String userPic = data.getUser_pic();
        Glide.with(ReplyCommentActivity.this).load(userPic).asBitmap().into(mPortrait);
        mAlias.setText(data.getUser_nickname());
        mComment.setText(data.getOrder_comment_msg());
        String orderCommentTime = data.getOrder_comment_time();
        String time = Utils.getInstance().getTime(orderCommentTime);
        mCommentTime.setText(time);
        mLikeNum.setText(data.getHot_number());
        String reply = "回复" + "(" + data.getReply_number() + ")";
        mReplyNum.setText(reply);

        HttpReqData.getInstance().getReplyCommentList(id, commentId, "0");
        ReplyCommentDataFactory.getInstance().setCallBack(this);

        adapter = new CommentListAdapter(ReplyCommentActivity.this, commentArr, true);
        mReplyList.setAdapter(adapter);
    }

    /**
     * 设置监听
     */
    private void setListener() {
        mBackBtn.setOnClickListener(this);
        mSendComment.setOnClickListener(this);
    }

    /**
     * 寻找控件
     */
    private void initView() {
        mBackBtn = (Button) findViewById(R.id.btn_back);
        mTitle = (TextView) findViewById(R.id.tv_title_name);
        mTitle.setText("评论");
        mPortrait = (SelectableRoundedImageView) findViewById(R.id.iv_user_head);
        mAlias = (TextView) findViewById(R.id.tv_user_name);
        mComment = (TextView) findViewById(R.id.tv_comment_msg);
        mCommentTime = (TextView) findViewById(R.id.tv_time);
        mLikeNum = (TextView) findViewById(R.id.tv_good_num);
        mReplyNum = (TextView) findViewById(R.id.tv_reply_num);
        mSendComment = (ImageView) findViewById(R.id.iv_input_comment);
        mReplyList = (ListView) findViewById(R.id.lv_reply_comment);
    }

    //回复评论的请求码
    private static final int REQUEST_REPLY = 1;

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
            case R.id.iv_input_comment:
                Intent intent = new Intent(ReplyCommentActivity.this, InputCommentActivity.class);
                intent.putExtra("id", id);
                intent.putExtra("commentId", commentId);
                intent.putExtra("action", "reply");
                startActivityForResult(intent, REQUEST_REPLY);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_REPLY && resultCode == RESULT_OK) {
            HttpReqData.getInstance().getReplyCommentList(id, commentId, "0");
        }
    }

    /**
     * 获取到回复数据时回调
     *
     * @param arr
     */
    @Override
    public void getReplyComment(CommentArr arr) {
        initList(arr);
    }

    /**
     * 初始化话回复列表
     *
     * @param arr
     */
    private void initList(CommentArr arr) {
        if (commentArr.size() != 0) {
            commentArr.clear();
        }
        commentArr.addAll(arr.getComment_arr());
        adapter.notifyDataSetChanged();
    }

    /**
     * 获取回复数据失败时回调
     */
    @Override
    public void getError() {

    }
}
