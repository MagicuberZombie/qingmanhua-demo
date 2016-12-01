package com.qf.demo.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.lzy.okhttputils.callback.StringCallback;
import com.qf.demo.R;
import com.qf.demo.http.HttpReqData;
import com.qf.demo.utils.Utils;

import okhttp3.Call;
import okhttp3.Response;

public class InputCommentActivity extends Activity implements View.OnClickListener {

    private Button mSendBtn;
    private EditText mCommentEt;

    private String id;
    private String comicId;
    private String orderId;

    private boolean isReply = false;
    private String commentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_comment);
        init();
    }

    /**
     * 初始化方法
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
        if (intent.getStringExtra("action").equals("reply")) {
            commentId = intent.getStringExtra("commentId");
            isReply = true;
        } else if (intent.getStringExtra("action").equals("comment")) {
            comicId = intent.getStringExtra("comicId");
            orderId = intent.getStringExtra("orderId");
            isReply = false;
        }
    }

    /**
     * 设置监听
     */
    private void setListener() {
        mSendBtn.setOnClickListener(this);
    }

    /**
     * 寻找控件
     */
    private void initView() {
        mSendBtn = (Button) findViewById(R.id.btn_commend);
        mCommentEt = (EditText) findViewById(R.id.et_comic_commend);
    }

    /**
     * 处理点击事件
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        String comment = mCommentEt.getText().toString();
        if (!comment.equals("")) {
            if (!isReply) {
                HttpReqData.getInstance().sendCommentData(id, comicId, orderId,
                        comment, new GetMessage());
            } else {
                HttpReqData.getInstance().replyComment(id, commentId, comment, new GetMessage());
            }
        } else {
            Utils.getInstance().toast("输入不能为空", InputCommentActivity.this);
        }
    }

    private class GetMessage extends StringCallback {

        @Override
        public void onSuccess(String s, Call call, Response response) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Utils.getInstance().toast("发送成功", InputCommentActivity.this);
                    finish();
                }
            });
        }
    }

}
