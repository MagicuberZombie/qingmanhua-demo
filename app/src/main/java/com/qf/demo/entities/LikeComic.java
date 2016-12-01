package com.qf.demo.entities;

import java.util.List;

/**
 * Created by Administrator on 2016/11/4.
 */
public class LikeComic {
    private List<ComicData> like_comic_arr;

    public LikeComic() {
    }

    public LikeComic(List<ComicData> like_comic_arr) {
        this.like_comic_arr = like_comic_arr;
    }

    public List<ComicData> getLike_comic_arr() {
        return like_comic_arr;
    }

    public void setLike_comic_arr(List<ComicData> like_comic_arr) {
        this.like_comic_arr = like_comic_arr;
    }
}
