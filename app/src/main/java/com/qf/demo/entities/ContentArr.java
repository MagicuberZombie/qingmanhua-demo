package com.qf.demo.entities;

import java.util.List;

/**
 * Created by Administrator on 2016/11/9.
 */
public class ContentArr {
    private List<ContentData> content_arr;

    public ContentArr() {
    }

    public ContentArr(List<ContentData> content_arr) {
        this.content_arr = content_arr;
    }

    public List<ContentData> getContent_arr() {
        return content_arr;
    }

    public void setContent_arr(List<ContentData> content_arr) {
        this.content_arr = content_arr;
    }
}
