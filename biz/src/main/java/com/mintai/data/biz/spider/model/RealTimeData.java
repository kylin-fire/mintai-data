package com.mintai.data.biz.spider.model;

import java.util.Date;
import java.util.List;

/**
 * 文件描述：实时数据来源
 *
 * @author leiteng
 *         Date 2015/9/3.
 */
public class RealTimeData {
    private String source;
    private Date time;
    private List<RealTimeItem> sources;

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

    public List<RealTimeItem> getSources() {
        return sources;
    }

    public void setSources(List<RealTimeItem> sources) {
        this.sources = sources;
    }
}
