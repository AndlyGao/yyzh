package com.qc.yyzh.entity;

import java.util.Date;

/**
 * @author qc
 * @date 2019/4/26
 */

public class Music {
    private int id;
    private String name;
    private int type;
    private int renqi;
    private String time;
    private String url;

    public Music() {
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

    public int getRenqi() {
        return renqi;
    }

    public void setRenqi(int renqi) {
        this.renqi = renqi;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
