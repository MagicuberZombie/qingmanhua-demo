package com.qf.demo.entities;

import java.util.List;

/**
 * Created by Administrator on 2016/11/4.
 */
public class HotComic {
    private List<ComicData> hot_comic_arr;

    public HotComic() {
    }

    public HotComic(List<ComicData> hot_comic_arr) {
        this.hot_comic_arr = hot_comic_arr;
    }

    public List<ComicData> getHot_comic_arr() {
        return hot_comic_arr;
    }

    public void setHot_comic_arr(List<ComicData> hot_comic_arr) {
        this.hot_comic_arr = hot_comic_arr;
    }
}
