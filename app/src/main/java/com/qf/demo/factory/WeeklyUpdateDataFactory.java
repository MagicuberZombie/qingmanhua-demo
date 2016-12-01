package com.qf.demo.factory;

import com.google.gson.Gson;
import com.lzy.okhttputils.callback.StringCallback;
import com.qf.demo.entities.WeeklyUpdateData;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2016/11/11.
 */
public class WeeklyUpdateDataFactory extends StringCallback {
    private static WeeklyUpdateDataFactory mInstance = null;

    private WeeklyUpdateDataFactory() {

    }

    public static WeeklyUpdateDataFactory getInstance() {
        if (mInstance == null) {
            mInstance = new WeeklyUpdateDataFactory();
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
                    WeeklyUpdateData weeklyUpdateData = gson.fromJson(data.toString(), WeeklyUpdateData.class);
                    callBack.getWeekly(weeklyUpdateData);
                }
            } else {
                callBack.getError();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private GetWeeklyDataCallBack callBack;

    public void setCallBack(GetWeeklyDataCallBack callBack) {
        this.callBack = callBack;
    }

    public interface GetWeeklyDataCallBack {
        void getWeekly(WeeklyUpdateData data);

        void getError();
    }
}
