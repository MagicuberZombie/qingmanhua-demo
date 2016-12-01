package com.qf.demo.entities;

import java.util.List;

/**
 * Created by Administrator on 2016/11/4.
 */
public class IndexComic {
    private List<ComicData> hot_comic_arr;
    private List<ComicData> like_comic_arr;
    private List<ComicData> recommend_comic_arr;

    private String hot_title_pic_url;
    private String like_title_pic_url;
    private String recommend_title_pic_url;
    private String weekfollow_pic_url;

    public IndexComic() {
    }

    public IndexComic(List<ComicData> hot_comic_arr, String hot_title_pic_url, List<ComicData> like_comic_arr, String like_title_pic_url, List<ComicData> recommend_comic_arr, String recommend_title_pic_url, String weekfollow_pic_url) {
        this.hot_comic_arr = hot_comic_arr;
        this.hot_title_pic_url = hot_title_pic_url;
        this.like_comic_arr = like_comic_arr;
        this.like_title_pic_url = like_title_pic_url;
        this.recommend_comic_arr = recommend_comic_arr;
        this.recommend_title_pic_url = recommend_title_pic_url;
        this.weekfollow_pic_url = weekfollow_pic_url;
    }

    public List<ComicData> getHot_comic_arr() {
        return hot_comic_arr;
    }

    public void setHot_comic_arr(List<ComicData> hot_comic_arr) {
        this.hot_comic_arr = hot_comic_arr;
    }

    public String getHot_title_pic_url() {
        return hot_title_pic_url;
    }

    public void setHot_title_pic_url(String hot_title_pic_url) {
        this.hot_title_pic_url = hot_title_pic_url;
    }

    public List<ComicData> getLike_comic_arr() {
        return like_comic_arr;
    }

    public void setLike_comic_arr(List<ComicData> like_comic_arr) {
        this.like_comic_arr = like_comic_arr;
    }

    public String getLike_title_pic_url() {
        return like_title_pic_url;
    }

    public void setLike_title_pic_url(String like_title_pic_url) {
        this.like_title_pic_url = like_title_pic_url;
    }

    public List<ComicData> getRecommend_comic_arr() {
        return recommend_comic_arr;
    }

    public void setRecommend_comic_arr(List<ComicData> recommend_comic_arr) {
        this.recommend_comic_arr = recommend_comic_arr;
    }

    public String getRecommend_title_pic_url() {
        return recommend_title_pic_url;
    }

    public void setRecommend_title_pic_url(String recommend_title_pic_url) {
        this.recommend_title_pic_url = recommend_title_pic_url;
    }

    public String getWeekfollow_pic_url() {
        return weekfollow_pic_url;
    }

    public void setWeekfollow_pic_url(String weekfollow_pic_url) {
        this.weekfollow_pic_url = weekfollow_pic_url;
    }
}
