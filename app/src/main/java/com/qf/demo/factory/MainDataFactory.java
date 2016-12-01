package com.qf.demo.factory;

import com.google.gson.Gson;
import com.lzy.okhttputils.callback.StringCallback;
import com.qf.demo.entities.HeadData;
import com.qf.demo.entities.IndexComic;
import com.qf.demo.entities.IndexHead;
import com.qf.demo.entities.IndexTop;
import com.qf.demo.entities.TopicData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2016/11/4.
 */
public class MainDataFactory extends StringCallback {

    private IndexComic indexComic;
    private IndexHead indexHead;
    private IndexTop indexTop;

    private static MainDataFactory mInstance = null;

    private MainDataFactory() {
    }

    public static MainDataFactory getInstance() {
        if (mInstance == null) {
            mInstance = new MainDataFactory();
        }
        return mInstance;
    }

    @Override
    public void onSuccess(String s, Call call, Response response) {
        try {
            JSONObject json = new JSONObject(s.trim());
            int result = json.optInt("code");
            if (result == 200) {
                JSONObject data = json.getJSONObject("data");
                JSONObject indexComicArr = data.optJSONObject("index_comic_arr");
                JSONArray indexHeadArr = data.optJSONArray("index_head_arr");
                JSONArray indexTopicArr = data.optJSONArray("index_topic_arr");

                Gson gson = new Gson();
                if (indexComicArr != null) {
                    indexComic = gson.fromJson
                            (indexComicArr.toString(), IndexComic.class);
                    fetchIndexComicSuccess.fetchSuccess(indexComic);
                }
                if (indexHeadArr != null) {
                    HeadData[] headDataList = gson.fromJson
                            (indexHeadArr.toString(), HeadData[].class);
                    indexHead = new IndexHead();
                    indexHead.setIndex_head_arr(Arrays.asList(headDataList));
                    fetchIndexHeadSuccess.fetchSuccess(indexHead);
                }
                if (indexTopicArr != null) {
                    TopicData[] topicDataList = gson.fromJson
                            (indexTopicArr.toString(), TopicData[].class);
                    indexTop = new IndexTop();
                    indexTop.setIndex_topic_arr(Arrays.asList(topicDataList));
                    fetchIndexTopSuccess.fetchSuccess(indexTop);
                }

            } else {
                fetchError.fetchError();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private FetchIndexTopSuccess fetchIndexTopSuccess;

    public void setFetchIndexTopSuccess(FetchIndexTopSuccess fetchIndexTopSuccess) {
        this.fetchIndexTopSuccess = fetchIndexTopSuccess;
    }

    public interface FetchIndexTopSuccess {
        void fetchSuccess(IndexTop top);
    }


    private FetchIndexHeadSuccess fetchIndexHeadSuccess;

    public void setFetchIndexHeadSuccess(FetchIndexHeadSuccess fetchIndexHeadSuccess) {
        this.fetchIndexHeadSuccess = fetchIndexHeadSuccess;
    }

    public interface FetchIndexHeadSuccess {
        void fetchSuccess(IndexHead head);
    }

    private FetchIndexComicSuccess fetchIndexComicSuccess = null;

    public void setFetchIndexComicSuccess(FetchIndexComicSuccess fetchIndexComicSuccess) {
        this.fetchIndexComicSuccess = fetchIndexComicSuccess;
    }

    public interface FetchIndexComicSuccess {
        void fetchSuccess(IndexComic comic);
    }

    private FetchError fetchError = null;

    public void setFetchError(FetchError fetchError) {
        this.fetchError = fetchError;
    }

    private interface FetchError {
        void fetchError();
    }
}
