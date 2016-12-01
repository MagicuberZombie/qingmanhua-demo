package com.qf.demo.entities;

/**
 * Created by Administrator on 2016/11/9.
 */
public class ContentData {
    private String comic_id;
    private String id;
    private String is_fix;
    private String order_idx;
    private String pager_idx;
    private String pager_pic;
    private String pager_pic_height;
    private String pager_pic_width;

    public ContentData() {
    }

    public ContentData(String comic_id, String id, String is_fix, String order_idx, String pager_idx, String pager_pic, String pager_pic_height, String pager_pic_width) {
        this.comic_id = comic_id;
        this.id = id;
        this.is_fix = is_fix;
        this.order_idx = order_idx;
        this.pager_idx = pager_idx;
        this.pager_pic = pager_pic;
        this.pager_pic_height = pager_pic_height;
        this.pager_pic_width = pager_pic_width;
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

    public String getIs_fix() {
        return is_fix;
    }

    public void setIs_fix(String is_fix) {
        this.is_fix = is_fix;
    }

    public String getOrder_idx() {
        return order_idx;
    }

    public void setOrder_idx(String order_idx) {
        this.order_idx = order_idx;
    }

    public String getPager_idx() {
        return pager_idx;
    }

    public void setPager_idx(String pager_idx) {
        this.pager_idx = pager_idx;
    }

    public String getPager_pic() {
        return pager_pic;
    }

    public void setPager_pic(String pager_pic) {
        this.pager_pic = pager_pic;
    }

    public String getPager_pic_height() {
        return pager_pic_height;
    }

    public void setPager_pic_height(String pager_pic_height) {
        this.pager_pic_height = pager_pic_height;
    }

    public String getPager_pic_width() {
        return pager_pic_width;
    }

    public void setPager_pic_width(String pager_pic_width) {
        this.pager_pic_width = pager_pic_width;
    }
}
