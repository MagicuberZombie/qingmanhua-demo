package com.qf.demo.entities;

import java.util.List;

/**
 * Created by Administrator on 2016/11/11.
 */
public class WeeklyUpdateData {
    private List<List<ComicData>> week_update_comic_arr;
    private List<String> week_update_pic_arr;

    public WeeklyUpdateData() {
    }

    public WeeklyUpdateData(List<List<ComicData>> week_update_comic_arr, List<String> week_update_pic_arr) {
        this.week_update_comic_arr = week_update_comic_arr;
        this.week_update_pic_arr = week_update_pic_arr;
    }

    public List<List<ComicData>> getWeek_update_comic_arr() {
        return week_update_comic_arr;
    }

    public void setWeek_update_comic_arr(List<List<ComicData>> week_update_comic_arr) {
        this.week_update_comic_arr = week_update_comic_arr;
    }

    public List<String> getWeek_update_pic_arr() {
        return week_update_pic_arr;
    }

    public void setWeek_update_pic_arr(List<String> week_update_pic_arr) {
        this.week_update_pic_arr = week_update_pic_arr;
    }

    @Override
    public String toString() {
        return "WeeklyUpdateData{" +
                "week_update_comic_arr=" + week_update_comic_arr +
                ", week_update_pic_arr=" + week_update_pic_arr +
                '}';
    }
}
