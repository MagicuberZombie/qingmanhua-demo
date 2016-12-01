package com.qf.demo.entities;

import java.util.List;

/**
 * Created by Administrator on 2016/11/4.
 */
public class RecommendComic {
    private List<ComicData> recommend_comic_arr;

    public RecommendComic() {
    }

    public RecommendComic(List<ComicData> recommend_comic_arr) {
        this.recommend_comic_arr = recommend_comic_arr;
    }

    public List<ComicData> getRecommend_comic_arr() {
        return recommend_comic_arr;
    }

    public void setRecommend_comic_arr(List<ComicData> recommend_comic_arr) {
        this.recommend_comic_arr = recommend_comic_arr;
    }
}
