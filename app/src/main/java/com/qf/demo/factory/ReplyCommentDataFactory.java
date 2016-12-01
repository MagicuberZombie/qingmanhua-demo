package com.qf.demo.factory;

import com.google.gson.Gson;
import com.lzy.okhttputils.callback.StringCallback;
import com.qf.demo.entities.CommentArr;
import com.qf.demo.entities.CommentData;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2016/11/10.
 */
public class ReplyCommentDataFactory extends StringCallback {
    private static ReplyCommentDataFactory mInstance;

    private ReplyCommentDataFactory() {

    }

    public static ReplyCommentDataFactory getInstance() {
        if (mInstance == null) {
            mInstance = new ReplyCommentDataFactory();
        }
        return mInstance;
    }

    @Override
    public void onSuccess(String s, Call call, Response response) {
        try {
            JSONObject json = new JSONObject(s.trim());
            if (json.optInt("code") == 200) {
                JSONObject data = json.optJSONObject("data");
                if (data != null) {
                    Gson gson = new Gson();
                    CommentArr commentArr = gson.fromJson(data.toString(), CommentArr.class);
                    callBack.getReplyComment(commentArr);
                }
            } else {
                callBack.getError();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private GetReplyCommentDataCallBack callBack = null;

    public void setCallBack(GetReplyCommentDataCallBack callBack) {
        this.callBack = callBack;
    }

    public interface GetReplyCommentDataCallBack {
        void getReplyComment(CommentArr arr);

        void getError();
    }
}
