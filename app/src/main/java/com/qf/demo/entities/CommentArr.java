package com.qf.demo.entities;

import java.util.List;

/**
 * Created by Administrator on 2016/11/10.
 */
public class CommentArr {
    private List<CommentData> comment_arr;

    public CommentArr() {
    }

    public CommentArr(List<CommentData> comment_arr) {
        this.comment_arr = comment_arr;
    }

    public List<CommentData> getComment_arr() {
        return comment_arr;
    }

    public void setComment_arr(List<CommentData> comment_arr) {
        this.comment_arr = comment_arr;
    }
}
