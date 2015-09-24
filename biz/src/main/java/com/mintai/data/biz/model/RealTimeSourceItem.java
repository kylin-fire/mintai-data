package com.mintai.data.biz.model;

import java.util.List;

/**
 * 文件描述：
 *
 * @author leiteng
 *         Date 2015/9/3.
 */
public class RealTimeSourceItem extends RealTimeItem {
    private double percent;

    private List<RealTimeItem> detail;

    public double getPercent() {
        return percent;
    }

    public void setPercent(double percent) {
        this.percent = percent;
    }

    public List<RealTimeItem> getDetail() {
        return detail;
    }

    public void setDetail(List<RealTimeItem> detail) {
        this.detail = detail;
    }
}
