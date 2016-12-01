package com.qf.demo.entities;

import java.util.List;

/**
 * Created by Administrator on 2016/11/5.
 */
public class MonthRankData {
    private List<ComicData> newest_comic_list;
    private List<ComicData> rank_comic_info;

    public MonthRankData() {
    }

    public MonthRankData(List<ComicData> newest_comic_list, List<ComicData> rank_comic_info) {
        this.newest_comic_list = newest_comic_list;
        this.rank_comic_info = rank_comic_info;
    }

    public List<ComicData> getNewest_comic_list() {
        return newest_comic_list;
    }

    public void setNewest_comic_list(List<ComicData> newest_comic_list) {
        this.newest_comic_list = newest_comic_list;
    }

    public List<ComicData> getRank_comic_info() {
        return rank_comic_info;
    }

    public void setRank_comic_info(List<ComicData> rank_comic_info) {
        this.rank_comic_info = rank_comic_info;
    }
}
