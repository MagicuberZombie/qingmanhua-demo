package com.qf.demo.entities;

/**
 * Created by Administrator on 2016/11/4.
 */
public class HeadData {
    private String active_desc_url;
    private String active_info;
    private String active_name;
    private String active_pic_url_1;
    private String active_pic_url_2;
    private String active_type;
    private String comic_id;
    private String id;
    private String is_head;
    private String is_show;
    private String order_jdx;
    private String rank;

    public HeadData() {
    }

    public HeadData(String active_desc_url, String active_info, String active_name, String active_pic_url_1, String active_pic_url_2, String active_type, String comic_id, String id, String is_head, String is_show, String order_jdx, String rank) {
        this.active_desc_url = active_desc_url;
        this.active_info = active_info;
        this.active_name = active_name;
        this.active_pic_url_1 = active_pic_url_1;
        this.active_pic_url_2 = active_pic_url_2;
        this.active_type = active_type;
        this.comic_id = comic_id;
        this.id = id;
        this.is_head = is_head;
        this.is_show = is_show;
        this.order_jdx = order_jdx;
        this.rank = rank;
    }

    public String getActive_desc_url() {
        return active_desc_url;
    }

    public void setActive_desc_url(String active_desc_url) {
        this.active_desc_url = active_desc_url;
    }

    public String getActive_info() {
        return active_info;
    }

    public void setActive_info(String active_info) {
        this.active_info = active_info;
    }

    public String getActive_name() {
        return active_name;
    }

    public void setActive_name(String active_name) {
        this.active_name = active_name;
    }

    public String getActive_pic_url_1() {
        return active_pic_url_1;
    }

    public void setActive_pic_url_1(String active_pic_url_1) {
        this.active_pic_url_1 = active_pic_url_1;
    }

    public String getActive_pic_url_2() {
        return active_pic_url_2;
    }

    public void setActive_pic_url_2(String active_pic_url_2) {
        this.active_pic_url_2 = active_pic_url_2;
    }

    public String getActive_type() {
        return active_type;
    }

    public void setActive_type(String active_type) {
        this.active_type = active_type;
    }

    public String getComic_id() {
        return comic_id;
    }

    public void setComic_id(String comic_id) {
        this.comic_id = comic_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIs_head() {
        return is_head;
    }

    public void setIs_head(String is_head) {
        this.is_head = is_head;
    }

    public String getIs_show() {
        return is_show;
    }

    public void setIs_show(String is_show) {
        this.is_show = is_show;
    }

    public String getOrder_jdx() {
        return order_jdx;
    }

    public void setOrder_jdx(String order_jdx) {
        this.order_jdx = order_jdx;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }
}
