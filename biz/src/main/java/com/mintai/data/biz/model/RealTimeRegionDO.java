package com.mintai.data.biz.model;

import java.util.Date;

/**
 * 文件描述：
 *
 * @author leiteng
 *         Date 2015/9/24.
 */
public class RealTimeRegionDO {
    private long id;
    private Date gmtCreate;
    private Date time;
    private String platform;
    private String source;
    private int visitor;
    private int paid;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public int getVisitor() {
        return visitor;
    }

    public void setVisitor(int visitor) {
        this.visitor = visitor;
    }

    public int getPaid() {
        return paid;
    }

    public void setPaid(int paid) {
        this.paid = paid;
    }
}
