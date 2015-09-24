package com.mintai.data.biz.spider.model;

import java.util.Date;

/**
 * 文件描述：
 *
 * @author leiteng
 *         Date 2015/9/24.
 */
public class RealTimeSourceDO {
    private String source;
    private Date time;
    private String from;
    private int visitor;
    private double percent;

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

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public int getVisitor() {
        return visitor;
    }

    public void setVisitor(int visitor) {
        this.visitor = visitor;
    }

    public double getPercent() {
        return percent;
    }

    public void setPercent(double percent) {
        this.percent = percent;
    }
}
