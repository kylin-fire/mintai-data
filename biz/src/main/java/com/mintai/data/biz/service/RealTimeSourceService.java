package com.mintai.data.biz.service;

import com.mintai.data.dal.RealTimeSourceDal;
import com.mintai.data.biz.model.RealTimeData;
import com.mintai.data.biz.model.RealTimeItem;
import com.mintai.data.biz.model.RealTimeRegionDO;
import com.mintai.data.biz.model.RealTimeSourceDO;
import com.mintai.data.biz.model.RealTimeSourceItem;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * 文件描述：
 *
 * @author leiteng
 *         Date 2015/9/24.
 */
@Service
public class RealTimeSourceService {
    private static ScheduledExecutorService executor = Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors());
    @Resource
    private RealTimeSourceDal realTimeSourceDal;

    public boolean persistPlatform(List<RealTimeData> platformSource) {
        if (CollectionUtils.isEmpty(platformSource)) {
            return false;
        }

        for (RealTimeData platform : platformSource) {
            persistSources(platform, platform.getSources(), 0L);
        }
        return true;
    }

    private void persistSources(RealTimeData platform, List<RealTimeItem> sources, long parent) {

        for (RealTimeItem sourceItem : sources) {
            // 构造对象
            RealTimeSourceDO sourceDO = new RealTimeSourceDO();
            BeanUtils.copyProperties(platform, sourceDO);
            BeanUtils.copyProperties(sourceItem, sourceDO);
            sourceDO.setParent(parent);

            // 持久化
            long count = realTimeSourceDal.createPlatformSource(sourceDO);

            // 入库成功
            if (count > 0) {
                List<RealTimeItem> detail = ((RealTimeSourceItem) sourceItem).getDetail();

                if (!CollectionUtils.isEmpty(detail)) {
                    // 递归处理子来源
                    persistSources(platform, detail, sourceDO.getId());
                }
            }
        }
    }

    public boolean persistRegion(RealTimeData region) {
        if (region == null) {
            return false;
        }

        persistRegion(region, region.getSources(), 0L);
        return true;
    }

    private void persistRegion(RealTimeData region, List<RealTimeItem> sources, long l) {
        for (RealTimeItem sourceItem : sources) {
            // 构造对象
            RealTimeRegionDO regionDO = new RealTimeRegionDO();
            BeanUtils.copyProperties(region, regionDO);
            BeanUtils.copyProperties(sourceItem, regionDO);

            // 持久化
            realTimeSourceDal.createRegionSource(regionDO);
        }
    }
}
