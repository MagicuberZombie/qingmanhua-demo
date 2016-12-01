package com.qf.demo.entities;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/11/8.
 */
public class ComicCataLog implements Serializable{
    private String comic_id;
    private String day;
    private String month;
    private String order_idx;
    private String order_size;
    private String order_name;
    private String order_title;
    private String year;

    public ComicCataLog() {
    }

    public ComicCataLog(String comic_id, String day, String month, String order_idx, String order_name, String order_size, String order_title, String year) {
        this.comic_id = comic_id;
        this.day = day;
        this.month = month;
        this.order_idx = order_idx;
        this.order_name = order_name;
        this.order_size = order_size;
        this.order_title = order_title;
        this.year = year;
    }

    public String getComic_id() {
        return comic_id;
    }

    public void setComic_id(String comic_id) {
        this.comic_id = comic_id;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getOrder_idx() {
        return order_idx;
    }

    public void setOrder_idx(String order_idx) {
        this.order_idx = order_idx;
    }

    public String getOrder_name() {
        return order_name;
    }

    public void setOrder_name(String order_name) {
        this.order_name = order_name;
    }

    public String getOrder_size() {
        return order_size;
    }

    public void setOrder_size(String order_size) {
        this.order_size = order_size;
    }

    public String getOrder_title() {
        return order_title;
    }

    public void setOrder_title(String order_title) {
        this.order_title = order_title;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
}
