package com.qf.demo.entities;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * 记录浏览历史的实体
 * Created by Administrator on 2016/11/11.
 */

@DatabaseTable
public class SubData implements Serializable {
    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField
    private String comicId;
    @DatabaseField
    private String orderId;
    @DatabaseField
    private String comicName;
    @DatabaseField
    private String comicPagNum;
    @DatabaseField
    private String comicCover;
    @DatabaseField
    private String comicAuthor;
    @DatabaseField
    private boolean isSub = false;

    public SubData() {
    }

    public SubData(String comicAuthor, String comicCover, String comicId, String comicName, String comicPagNum, int id, boolean isSub, String orderId) {
        this.comicAuthor = comicAuthor;
        this.comicCover = comicCover;
        this.comicId = comicId;
        this.comicName = comicName;
        this.comicPagNum = comicPagNum;
        this.id = id;
        this.isSub = isSub;
        this.orderId = orderId;
    }

    public String getComicAuthor() {
        return comicAuthor;
    }

    public void setComicAuthor(String comicAuthor) {
        this.comicAuthor = comicAuthor;
    }

    public String getComicCover() {
        return comicCover;
    }

    public void setComicCover(String comicCover) {
        this.comicCover = comicCover;
    }

    public String getComicId() {
        return comicId;
    }

    public void setComicId(String comicId) {
        this.comicId = comicId;
    }

    public String getComicName() {
        return comicName;
    }

    public void setComicName(String comicName) {
        this.comicName = comicName;
    }

    public String getComicPagNum() {
        return comicPagNum;
    }

    public void setComicPagNum(String comicPagNum) {
        this.comicPagNum = comicPagNum;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isSub() {
        return isSub;
    }

    public void setIsSub(boolean isSub) {
        this.isSub = isSub;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    @Override
    public String toString() {
        return "History{" +
                "comicAuthor='" + comicAuthor + '\'' +
                ", id=" + id +
                ", comicId='" + comicId + '\'' +
                ", orderId='" + orderId + '\'' +
                ", comicName='" + comicName + '\'' +
                ", comicPagNum='" + comicPagNum + '\'' +
                ", comicCover='" + comicCover + '\'' +
                ", isSub=" + isSub +
                '}';
    }
}
