package com.qf.demo.factory;

import com.google.gson.Gson;
import com.lzy.okhttputils.callback.StringCallback;
import com.qf.demo.entities.ComicInfo;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2016/11/8.
 */
public class ComicInfoFactory extends StringCallback {
    private static ComicInfoFactory mInstance = null;

    private ComicInfoFactory() {

    }

    public static ComicInfoFactory getInstance() {
        if (mInstance == null) {
            mInstance = new ComicInfoFactory();
        }
        return mInstance;
    }

    @Override
    public void onSuccess(String s, Call call, Response response) {
        try {
            JSONObject json = new JSONObject(s.trim());
            int code = json.optInt("code", 0);
            if (code == 200) {
                JSONObject data = json.optJSONObject("data");
                if (data != null) {
                    JSONObject comicData = data.optJSONObject("comic_info").optJSONObject("comic_data");
                    if (comicData != null) {
                        Gson gson = new Gson();
                        ComicInfo comicInfo = gson.fromJson(comicData.toString(), ComicInfo.class);
                        callBack.getComicInfo(comicInfo);
                    }
                }
            }
            callBack.getError();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private GetComicInfoCallBack callBack;

    public void setCallBack(GetComicInfoCallBack callBack) {
        this.callBack = callBack;
    }

    public interface GetComicInfoCallBack {
        void getComicInfo(ComicInfo info);

        void getError();
    }
}
