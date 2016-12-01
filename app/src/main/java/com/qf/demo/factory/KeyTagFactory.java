package com.qf.demo.factory;

import com.google.gson.Gson;
import com.lzy.okhttputils.callback.StringCallback;
import com.qf.demo.entities.KeyTagList;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by ZombieFan on 2016/11/13.
 */
public class KeyTagFactory extends StringCallback {
    private static KeyTagFactory mInstance = null;

    private KeyTagFactory() {

    }

    public static KeyTagFactory getInstance() {
        if (mInstance == null) {
            mInstance = new KeyTagFactory();
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
                    KeyTagList keyTagList = gson.fromJson(data.toString(), KeyTagList.class);
                    callBack.getKeyTag(keyTagList);
                }
            } else {
                callBack.getError();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private GetKeyTagCallBack callBack = null;

    public void setCallBack(GetKeyTagCallBack callBack) {
        this.callBack = callBack;
    }

    public interface GetKeyTagCallBack {
        void getKeyTag(KeyTagList list);

        void getError();
    }
}
