package com.mintai.data.biz.service;

import com.google.common.collect.Multimap;
import com.mintai.data.biz.dal.RealTimeSourceDal;
import com.mintai.data.biz.spider.model.RealTimeData;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.Set;
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

    public boolean persist(List<Multimap<String, RealTimeData>> result) {
        if (CollectionUtils.isEmpty(result)) {
            return false;
        }

        for (Multimap<String, RealTimeData> each : result) {

            Set<String> keys = each.keySet();

            for (String key : keys) {
                Collection<RealTimeData> sources = each.get(key);

                for (RealTimeData source : sources) {

                }
            }
        }
        return true;
    }
}
