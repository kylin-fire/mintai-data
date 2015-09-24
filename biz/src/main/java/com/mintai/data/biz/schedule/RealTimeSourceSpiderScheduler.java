package com.mintai.data.biz.schedule;

import com.google.common.base.Throwables;
import com.google.common.collect.Multimap;
import com.mintai.data.biz.service.RealTimeSourceService;
import com.mintai.data.biz.spider.RealTimeSourceSpider;
import com.mintai.data.biz.spider.model.RealTimeData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 文件描述：
 *
 * @author leiteng
 *         Date 2015/9/3.
 */
@Controller
public class RealTimeSourceSpiderScheduler implements InitializingBean {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private static ScheduledExecutorService executor = Executors.newScheduledThreadPool(2);
    private String userName = "user_name";
    private String password = "password";
    @Resource
    private RealTimeSourceSpider realTimeSourceSpider;
    @Resource
    private RealTimeSourceService realTimeSourceService;

    public void init() {
        logger.warn("initialling");

        Runnable runnable = new Runnable() {
            public void run() {
                logger.warn(String.format("spider\01userName\02%s", userName));

                boolean success = false;

                int loop = 0;
                while (loop++ < 5) {
                    try {
                        List<Multimap<String, RealTimeData>> result = realTimeSourceSpider.execute(userName, password);

                        success = realTimeSourceService.persist(result);
                    } catch (Exception e) {
                        logger.error(String.format("spider\01exception\02%s", Throwables.getRootCause(e)));
                    }
                    // 成功爬取，跳出
                    if (success) {
                        break;
                    }
                }

                // 记录爬取失败记录
                if (!success) {
                    logger.warn("spider\01message\02failure");
                }
            }
        };

        // 计算启动延迟时间
        long delay = calculateDelay();

        // 每隔10分钟调度一次
        int rate = 30;

        executor.scheduleAtFixedRate(runnable, 0, rate, TimeUnit.SECONDS);

        logger.warn(String.format("initialled\01delay\02%s\01rate\02%s", delay, rate));
    }

    private long calculateDelay() {
        Date date = new Date();

        // 当前时间转秒
        long current = TimeUnit.MILLISECONDS.toSeconds(date.getTime());

        // 精确到小时，对小时取整
        long hours = TimeUnit.SECONDS.toHours(current);
        // 换算为秒
        long seconds = TimeUnit.HOURS.toSeconds(hours);
        // 最早每个小时的58分开始调度执行
        seconds += 58 * 60;
        // 计算当前时间到58分的延迟
        long delay = seconds - current;
        // 当前时间在58分之前，delay执行；在58分之后，直接执行一次
        return delay > 0 ? delay : 0;
    }

    public void afterPropertiesSet() throws Exception {
        init();
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
