package com.qf.demo.entities;

import java.util.List;

/**
 * Created by Administrator on 2016/11/4.
 */
public class IndexTop {
    private List<TopicData> index_topic_arr;

    public IndexTop() {
    }

    public IndexTop(List<TopicData> index_topic_arr) {
        this.index_topic_arr = index_topic_arr;
    }

    public List<TopicData> getIndex_topic_arr() {
        return index_topic_arr;
    }

    public void setIndex_topic_arr(List<TopicData> index_topic_arr) {
        this.index_topic_arr = index_topic_arr;
    }
}
