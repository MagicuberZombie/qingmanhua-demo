package com.qf.demo.factory;

import com.qf.demo.entities.ComicData;
import com.qf.demo.entities.IndexComic;

import java.util.List;

/**
 * Created by Administrator on 2016/11/4.
 */
public class IndexComicDataFactory implements MainDataFactory.FetchIndexComicSuccess {

    private MainDataFactory dataFactory;
    private static IndexComicDataFactory mInstance;

    private IndexComicDataFactory() {
        dataFactory = MainDataFactory.getInstance();
        dataFactory.setFetchIndexComicSuccess(this);
    }

    public static IndexComicDataFactory getInstance() {
        if (mInstance == null) {
            mInstance = new IndexComicDataFactory();
        }
        return mInstance;
    }

    @Override
    public void fetchSuccess(IndexComic comic) {
        String hotTitlePicUrl = comic.getHot_title_pic_url();
        String likeTitlePicUrl = comic.getLike_title_pic_url();
        String recommendTitlePicUrl = comic.getRecommend_title_pic_url();
        String weekfollowPicUrl = comic.getWeekfollow_pic_url();

        List<ComicData> hotComicArr = comic.getHot_comic_arr();
        List<ComicData> likeComicArr = comic.getLike_comic_arr();
        List<ComicData> recommendComicArr = comic.getRecommend_comic_arr();

        callBack.getIndexComicData(new String[]{hotTitlePicUrl, likeTitlePicUrl, recommendTitlePicUrl,
                weekfollowPicUrl}, hotComicArr, likeComicArr, recommendComicArr);
    }

    private GetComicDataCallBack callBack;

    public void setCallBack(GetComicDataCallBack callBack) {
        this.callBack = callBack;
    }

    public interface GetComicDataCallBack {
        void getIndexComicData(String[] titlePicUrl, List<ComicData> hotComicArr,
                               List<ComicData> likeComicArr, List<ComicData> recommendComicArr);
    }
}
