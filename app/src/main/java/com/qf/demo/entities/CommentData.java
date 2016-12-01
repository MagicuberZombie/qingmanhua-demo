package com.qf.demo.entities;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/11/10.
 */
public class CommentData implements Serializable {
    private String comic_id;
    private String hot_number;
    private String id;
    private String is_hot;
    private String order_comment_msg;
    private String order_comment_time;
    private String reply_number;
    private String user_id;
    private String user_nickname;
    private String user_pic;

    public CommentData() {
    }

    public CommentData(String comic_id, String hot_number, String id, String is_hot, String order_comment_msg, String order_comment_time, String reply_number, String user_id, String user_nickname, String user_pic) {
        this.comic_id = comic_id;
        this.hot_number = hot_number;
        this.id = id;
        this.is_hot = is_hot;
        this.order_comment_msg = order_comment_msg;
        this.order_comment_time = order_comment_time;
        this.reply_number = reply_number;
        this.user_id = user_id;
        this.user_nickname = user_nickname;
        this.user_pic = user_pic;
    }

    public String getComic_id() {
        return comic_id;
    }

    public void setComic_id(String comic_id) {
        this.comic_id = comic_id;
    }

    public String getHot_number() {
        return hot_number;
    }

    public void setHot_number(String hot_number) {
        this.hot_number = hot_number;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIs_hot() {
        return is_hot;
    }

    public void setIs_hot(String is_hot) {
        this.is_hot = is_hot;
    }

    public String getOrder_comment_msg() {
        return order_comment_msg;
    }

    public void setOrder_comment_msg(String order_comment_msg) {
        this.order_comment_msg = order_comment_msg;
    }

    public String getOrder_comment_time() {
        return order_comment_time;
    }

    public void setOrder_comment_time(String order_comment_time) {
        this.order_comment_time = order_comment_time;
    }

    public String getReply_number() {
        return reply_number;
    }

    public void setReply_number(String reply_number) {
        this.reply_number = reply_number;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_nickname() {
        return user_nickname;
    }

    public void setUser_nickname(String user_nickname) {
        this.user_nickname = user_nickname;
    }

    public String getUser_pic() {
        return user_pic;
    }

    public void setUser_pic(String user_pic) {
        this.user_pic = user_pic;
    }
}
