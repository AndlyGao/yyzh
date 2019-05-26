package com.qc.yyzs.entity;

public class Music {
    private int id;
    private String name;
    private int type;
    private String time;
    private int renqi;
    private String url;
    private String coverurl;
    private long duration;

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public Music(int id, String name, int type, String time, int renqi, String url, String coverurl, long duration) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.time = time;
        this.renqi = renqi;
        this.url = url;
        this.coverurl = coverurl;
        this.duration = duration;
    }

    public Music() {
    }

    @Override
    public String toString() {
        return "Music{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", time='" + time + '\'' +
                ", renqi=" + renqi +
                ", url='" + url + '\'' +
                ", coverurl='" + coverurl + '\'' +
                '}';
    }

    public int getId() {
        return id;
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getRenqi() {
        return renqi;
    }

    public void setRenqi(int renqi) {
        this.renqi = renqi;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCoverurl() {
        return coverurl;
    }

    public void setCoverurl(String coverurl) {
        this.coverurl = coverurl;
    }
}
