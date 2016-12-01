package com.qf.demo.factory;

import com.qf.demo.entities.ComicData;
import com.qf.demo.entities.IndexTop;
import com.qf.demo.entities.TopicData;

import java.util.List;

/**
 * Created by Administrator on 2016/11/5.
 */
public class IndexTopDataFactory implements MainDataFactory.FetchIndexTopSuccess {
    private MainDataFactory dataFactory;
    private static IndexTopDataFactory mInstance;

    private IndexTopDataFactory() {
        dataFactory = MainDataFactory.getInstance();
        dataFactory.setFetchIndexTopSuccess(this);
    }

    public static IndexTopDataFactory getInstance() {
        if (mInstance == null) {
            mInstance = new IndexTopDataFactory();
        }
        return mInstance;
    }

    @Override
    public void fetchSuccess(IndexTop top) {
        List<TopicData> indexTopicArr = top.getIndex_topic_arr();
        callBack.getIndexTopicData(indexTopicArr);
    }

    private GetTopDataCallBack callBack;

    public void setCallBack(GetTopDataCallBack callBack) {
        this.callBack = callBack;
    }

    public interface GetTopDataCallBack {
        void getIndexTopicData(List<TopicData> topic);
    }
}
