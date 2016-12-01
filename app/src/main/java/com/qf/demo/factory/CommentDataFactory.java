package com.qf.demo.factory;

import com.google.gson.Gson;
import com.lzy.okhttputils.callback.StringCallback;
import com.qf.demo.entities.CommentArr;
import com.qf.demo.entities.CommentData;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;
import okhttp3.Response;

public class CommentDataFactory extends StringCallback {
    private static CommentDataFactory mInstance;

    private CommentDataFactory() {

    }

    public static CommentDataFactory getInstance() {
        if (mInstance == null) {
            mInstance = new CommentDataFactory();
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
                    callBack.getCommentData(commentArr);
                }
            } else {
                callBack.getError();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private GetCommentArrCallBack callBack = null;

    public void setCallBack(GetCommentArrCallBack callBack) {
        this.callBack = callBack;
    }

    public interface GetCommentArrCallBack {
        void getCommentData(CommentArr arr);

        void getError();
    }
}
