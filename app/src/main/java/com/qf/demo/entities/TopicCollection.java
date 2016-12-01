package com.qf.demo.entities;

import java.util.List;

/**
 * Created by ZombieFan on 2016/11/13.
 */
public class TopicCollection {
    private List<TopicCollectionData> topic_list;

    public TopicCollection() {
    }

    public TopicCollection(List<TopicCollectionData> topic_list) {
        this.topic_list = topic_list;
    }

    public List<TopicCollectionData> getTopic_list() {
        return topic_list;
    }

    public void setTopic_list(List<TopicCollectionData> topic_list) {
        this.topic_list = topic_list;
    }
}
