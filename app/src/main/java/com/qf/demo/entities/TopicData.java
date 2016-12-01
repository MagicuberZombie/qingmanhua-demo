package com.qf.demo.entities;

/**
 * Created by Administrator on 2016/11/4.
 */
public class TopicData {
    private String id;
    private String is_show;
    private String topic_name;
    private String topic_pic_url;
    private String topic_tag_name;
    private String type;

    public TopicData() {
    }

    public TopicData(String id, String is_show, String topic_name, String topic_pic_url, String topic_tag_name, String type) {
        this.id = id;
        this.is_show = is_show;
        this.topic_name = topic_name;
        this.topic_pic_url = topic_pic_url;
        this.topic_tag_name = topic_tag_name;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIs_show() {
        return is_show;
    }

    public void setIs_show(String is_show) {
        this.is_show = is_show;
    }

    public String getTopic_name() {
        return topic_name;
    }

    public void setTopic_name(String topic_name) {
        this.topic_name = topic_name;
    }

    public String getTopic_pic_url() {
        return topic_pic_url;
    }

    public void setTopic_pic_url(String topic_pic_url) {
        this.topic_pic_url = topic_pic_url;
    }

    public String getTopic_tag_name() {
        return topic_tag_name;
    }

    public void setTopic_tag_name(String topic_tag_name) {
        this.topic_tag_name = topic_tag_name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
