package com.qf.demo.factory;

import com.qf.demo.entities.HeadData;
import com.qf.demo.entities.IndexHead;
import com.qf.demo.http.BasicAttributes;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/11/4.
 */
public class IndexHeadDataFactory implements MainDataFactory.FetchIndexHeadSuccess {

    private MainDataFactory dataFactory;
    private static IndexHeadDataFactory mInstance;

    private IndexHeadDataFactory() {
        dataFactory = MainDataFactory.getInstance();
        dataFactory.setFetchIndexHeadSuccess(this);
    }

    public static IndexHeadDataFactory getInstance() {
        if (mInstance == null) {
            mInstance = new IndexHeadDataFactory();
        }
        return mInstance;
    }

    @Override
    public void fetchSuccess(IndexHead head) {
        callBack.getIndexHeadPics(head.getIndex_head_arr());
    }

    private GetIndexHeadDataCallBack callBack;

    public void setCallBack(GetIndexHeadDataCallBack callBack) {
        this.callBack = callBack;
    }

    public interface GetIndexHeadDataCallBack {
        void getIndexHeadPics(List<HeadData> data);
    }

}
