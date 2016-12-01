package com.qf.demo.factory;

import com.google.gson.Gson;
import com.lzy.okhttputils.callback.StringCallback;
import com.qf.demo.entities.TopicCollection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by ZombieFan on 2016/11/13.
 */
public class TopicCollectionFactory extends StringCallback {
    private static TopicCollectionFactory mInstance = null;

    private TopicCollectionFactory() {

    }

    public static TopicCollectionFactory getInstance() {
        if (mInstance == null) {
            mInstance = new TopicCollectionFactory();
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
                    TopicCollection topicCollection = gson.fromJson(data.toString(), TopicCollection.class);
                    callBack.getTopicCollection(topicCollection);
                }
            } else {
                callBack.getError();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private GetTopicCollectionCallBack callBack = null;

    public void setCallBack(GetTopicCollectionCallBack callBack) {
        this.callBack = callBack;
    }

    public interface GetTopicCollectionCallBack {
        void getTopicCollection(TopicCollection collection);

        void getError();
    }
}
