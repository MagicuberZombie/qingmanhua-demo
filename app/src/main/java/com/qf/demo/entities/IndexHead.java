package com.qf.demo.entities;

import java.util.List;

/**
 * Created by Administrator on 2016/11/4.
 */
public class IndexHead {
    private List<HeadData> index_head_arr;

    public IndexHead() {
    }

    public IndexHead(List<HeadData> index_head_arr) {
        this.index_head_arr = index_head_arr;
    }

    public List<HeadData> getIndex_head_arr() {
        return index_head_arr;
    }

    public void setIndex_head_arr(List<HeadData> index_head_arr) {
        this.index_head_arr = index_head_arr;
    }
}
