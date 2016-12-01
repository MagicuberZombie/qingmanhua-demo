package com.qf.demo.entities;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by ZombieFan on 2016/11/13.
 */
@DatabaseTable(tableName = "tag")
public class HistoryTag implements Serializable {
    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField
    private String tag;

    public HistoryTag() {
    }

    public HistoryTag(int id, String tag) {
        this.id = id;
        this.tag = tag;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
