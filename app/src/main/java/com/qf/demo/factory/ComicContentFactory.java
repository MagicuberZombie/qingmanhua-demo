package com.qf.demo.factory;

import com.google.gson.Gson;
import com.lzy.okhttputils.callback.StringCallback;
import com.qf.demo.entities.ContentArr;
import com.qf.demo.entities.ContentData;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2016/11/9.
 */
public class ComicContentFactory extends StringCallback {
    private static ComicContentFactory mInstance;

    private ComicContentFactory() {

    }

    public static ComicContentFactory getInstance() {
        if (mInstance == null) {
            mInstance = new ComicContentFactory();
        }
        return mInstance;
    }

    @Override
    public void onSuccess(String s, Call call, Response response) {
        try {
            JSONObject json = new JSONObject(s.trim());
            int code = json.optInt("code");
            if (code == 200) {
                JSONObject data = json.optJSONObject("data");
                if (data != null) {
                    Gson gson = new Gson();
                    ContentArr contentArr = gson.fromJson(data.toString(), ContentArr.class);
                    callBack.getContentArr(contentArr);
                }
            } else {
                callBack.getError();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private GetContentArrCallBack callBack;

    public void setCallBack(GetContentArrCallBack callBack) {
        this.callBack = callBack;
    }

    public interface GetContentArrCallBack {
        void getContentArr(ContentArr arr);

        void getError();
    }
}
