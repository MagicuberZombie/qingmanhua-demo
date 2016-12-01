package com.qf.demo.entities;

import java.util.List;

/**
 * Created by ZombieFan on 2016/11/13.
 */
public class TopicComicData {
    private List<ComicData> comic_arr;

    public TopicComicData() {
    }

    public TopicComicData(List<ComicData> comic_arr) {
        this.comic_arr = comic_arr;
    }

    public List<ComicData> getComic_arr() {
        return comic_arr;
    }

    public void setComic_arr(List<ComicData> comic_arr) {
        this.comic_arr = comic_arr;
    }
}
