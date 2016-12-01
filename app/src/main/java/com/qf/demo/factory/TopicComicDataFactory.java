package com.qf.demo.factory;

import com.google.gson.Gson;
import com.lzy.okhttputils.callback.StringCallback;
import com.qf.demo.entities.TopicComicData;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by ZombieFan on 2016/11/13.
 */
public class TopicComicDataFactory extends StringCallback {
    private static TopicComicDataFactory mInstance;

    private TopicComicDataFactory() {

    }

    public static TopicComicDataFactory getInstance() {
        if (mInstance == null) {
            mInstance = new TopicComicDataFactory();
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
                    TopicComicData topicComicData = gson.fromJson(data.toString(), TopicComicData.class);
                    callBack.getTopicComic(topicComicData);
                }
            } else {
                callBack.getError();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private GetTopicComicCallBack callBack;

    public void setCallBack(GetTopicComicCallBack callBack) {
        this.callBack = callBack;
    }

    public interface GetTopicComicCallBack {
        void getTopicComic(TopicComicData data);

        void getError();
    }
}
