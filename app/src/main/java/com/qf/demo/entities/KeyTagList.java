package com.qf.demo.entities;

import java.util.List;

/**
 * Created by ZombieFan on 2016/11/13.
 */
public class KeyTagList {
    private List<KeyTag> tag_arr;

    public KeyTagList() {
    }

    public KeyTagList(List<KeyTag> tag_arr) {
        this.tag_arr = tag_arr;
    }

    public List<KeyTag> getTag_arr() {
        return tag_arr;
    }

    public void setTag_arr(List<KeyTag> tag_arr) {
        this.tag_arr = tag_arr;
    }
}
