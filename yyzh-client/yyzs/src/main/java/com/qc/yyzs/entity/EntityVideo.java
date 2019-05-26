package com.qc.yyzs.entity;

import java.io.Serializable;

public class EntityVideo implements Serializable{
    private int id;
    private String name;
    private long duration;
    private long size;
    private String data;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    private String desc;
    private String imageUrl;
    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "EntityVideo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", duration=" + duration +
                ", size=" + size +
                ", data='" + data + '\'' +
                ", desc='" + desc + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", bitmapPath='" + bitmapPath + '\'' +
                '}';
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getBitmapPath() {
        return bitmapPath;
    }

    public void setBitmapPath(String bitmapPath) {
        this.bitmapPath = bitmapPath;
    }

    private String bitmapPath;
}
