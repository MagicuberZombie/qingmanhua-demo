package com.qf.demo.factory;

import com.google.gson.Gson;
import com.lzy.okhttputils.callback.StringCallback;
import com.qf.demo.entities.SearchResult;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by ZombieFan on 2016/11/13.
 */
public class SearchResultFactory extends StringCallback {
    private static SearchResultFactory mInstance = null;

    private SearchResultFactory() {

    }

    public static SearchResultFactory getInstance() {
        if (mInstance == null) {
            mInstance = new SearchResultFactory();
        }
        return mInstance;
    }

    @Override
    public void onSuccess(String s, Call call, Response response) {
        try {
            JSONObject json = new JSONObject(s.trim());
            if (json.optInt("code") == 200 && json.optString("message").equals("ok")) {
                JSONObject data = json.optJSONObject("data");
                if (data != null) {
                    Gson gson = new Gson();
                    SearchResult result = gson.fromJson(data.toString(), SearchResult.class);
                    callBack.getSearchResult(result);
                }
            } else if (json.optString("message").equals("noresult")) {
                JSONObject data = json.optJSONObject("data");
                if (data != null) {
                    Gson gson = new Gson();
                    SearchResult result = gson.fromJson(data.toString(), SearchResult.class);
                    callBack.getNoResult(result);
                }
            } else {
                callBack.getError();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private GetSearchResultCallBack callBack = null;

    public void setCallBack(GetSearchResultCallBack callBack) {
        this.callBack = callBack;
    }

    public interface GetSearchResultCallBack {
        void getSearchResult(SearchResult result);

        void getNoResult(SearchResult result);

        void getError();
    }
}
