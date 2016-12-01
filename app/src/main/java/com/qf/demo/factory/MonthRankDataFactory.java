package com.qf.demo.factory;

import com.google.gson.Gson;
import com.lzy.okhttputils.callback.StringCallback;
import com.qf.demo.entities.MonthRankData;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 获取排行数据的Factory
 * Created by Administrator on 2016/11/5.
 */
public class MonthRankDataFactory extends StringCallback {
    private MonthRankData mData;

    private static MonthRankDataFactory mInstance = null;

    private MonthRankDataFactory() {

    }

    public static MonthRankDataFactory getInstance() {
        if (mInstance == null) {
            mInstance = new MonthRankDataFactory();
        }
        return mInstance;
    }

    @Override
    public void onSuccess(String s, Call call, Response response) {
        try {
            JSONObject json = new JSONObject(s.trim());
            if (json.optInt("code") == 200) {
                Gson gson = new Gson();
                mData = gson.fromJson(json.toString(), MonthRankData.class);
                callback.success(mData);
            } else {
                callback.error();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private FetchMonthRankDataCallBack callback;

    public void setCallback(FetchMonthRankDataCallBack callback) {
        this.callback = callback;
    }

    public interface FetchMonthRankDataCallBack {
        void success(MonthRankData data);

        void error();
    }


}
