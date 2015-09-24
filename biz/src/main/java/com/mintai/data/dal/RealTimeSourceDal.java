package com.mintai.data.dal;

import com.mintai.data.biz.model.RealTimeRegionDO;
import com.mintai.data.biz.model.RealTimeSourceDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 * 文件描述：
 *
 * @author leiteng
 *         Date 2015/9/24.
 */
@Repository
public class RealTimeSourceDal {
    @Autowired
    private RealTimeSourceDao realTimeSourceDao;
    @Autowired
    private RealTimeRegionDao realTimeRegionDao;

    public long createPlatformSource(RealTimeSourceDO sourceDO) {
        return realTimeSourceDao.insert(sourceDO);
    }

    public long createRegionSource(RealTimeRegionDO regionDO) {
        return realTimeRegionDao.insert(regionDO);
    }
}
