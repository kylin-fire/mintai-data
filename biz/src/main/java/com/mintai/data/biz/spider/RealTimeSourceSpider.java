package com.mintai.data.biz.spider;

import com.alibaba.common.lang.StringUtil;
import com.google.common.base.Throwables;
import com.google.common.collect.Lists;
import com.mintai.data.biz.model.RealTimeData;
import com.mintai.data.biz.model.RealTimeItem;
import com.mintai.data.biz.model.RealTimeRegionItem;
import com.mintai.data.biz.model.RealTimeSourceItem;
import com.mintai.data.common.helper.CommonHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class RealTimeSourceSpider {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private WebDriver driver;
    private String baseUrl;

    public RealTimeSourceSpider() {

    }

    public WebDriver craw(String userName, String password) {
        driver = new FirefoxDriver();
        baseUrl = "http://beta.sycm.taobao.com/";
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

        // ʵʱֱ��
        driver.get(baseUrl + "index.htm");

        if (driver.getCurrentUrl().contains("login.htm")) {
            // ��¼
            while (true) {
                String text = driver.findElement(By.id("mod-banner")).getText();
                if (text != null && !text.isEmpty()) {
                    break;
                }
            }

            driver.switchTo().frame(0);
            driver.findElement(By.id("TPL_username_1")).sendKeys(userName);
            driver.findElement(By.id("TPL_password_1")).sendKeys(password);
            driver.findElement(By.id("J_SubmitStatic")).click();

            while (driver.getCurrentUrl().contains("login.taobao.com")) {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    logger.error(String.format("craw\01exception\02%s", Throwables.getRootCause(e)));
                }
            }

            driver.switchTo().defaultContent();
        }

        driver.findElement(By.linkText("实时直播")).click();

        // ʵʱ��Դ
        driver.findElement(By.linkText("实时来源")).click();

        logger.warn(String.format("spider\01url\02%s\01source\02%s", driver.getCurrentUrl(), driver.getPageSource()));

        return driver;
    }

    public List<RealTimeData> extractPlatform(WebDriver driver) {
        WebElement root = driver.findElement(By.id("page-live-source"));

        List<RealTimeData> list = Lists.newArrayList();

        // 抽取数据
        List<WebElement> platform = root.findElements(By.cssSelector("div.component-source-device"));

        for (WebElement each : platform) {
            RealTimeData source = getRealTimeSource(each);
            list.add(source);
        }

        logger.warn(String.format("spider\01platform\02%s", CommonHelper.toJson(list)));

        return list;
    }

    private static RealTimeData getRealTimeSource(WebElement element) {
        RealTimeData sourceDO = new RealTimeData();
        String platform = element.findElement(By.cssSelector("h4.navbar-header > span")).getText();
        sourceDO.setPlatform(platform);

        String time = element.findElement(By.cssSelector("div.mod-box-update-time > span > em")).getText();
        Date date = CommonHelper.toDate(time, "yyyy-MM-dd HH:mm:ss");
        sourceDO.setTime(date);

        List<WebElement> rows = element.findElements(By.cssSelector("div.table-body > div.row"));

        List<RealTimeItem> sourceList = getRealTimeItems(rows);
        sourceDO.setSources(sourceList);

        return sourceDO;
    }

    private static List<RealTimeItem> getRealTimeItems(List<WebElement> rows) {
        List<RealTimeItem> sourceList = null;
        if (rows != null && !rows.isEmpty()) {
            sourceList = Lists.newArrayList();

            for (WebElement row : rows) {
                RealTimeSourceItem item = getRealTimeSourceItem(row);
                sourceList.add(item);
            }
        }
        return sourceList;
    }

    private static RealTimeSourceItem getRealTimeSourceItem(WebElement row) {

        RealTimeSourceItem item = new RealTimeSourceItem();

        String source = row.findElement(By.cssSelector("span.source > span")).getText();
        String percent = row.findElement(By.cssSelector("span.percent")).getText();
        if (StringUtil.isNotBlank(percent) && percent.contains("%")) {
            percent = percent.replace("%", "");
        }
        String visitor = row.findElement(By.cssSelector("span.pv")).getText();

        item.setSource(source);
        item.setPercent(Double.valueOf(percent));
        item.setVisitor(Integer.valueOf(visitor));

        WebElement child = row.findElement(By.cssSelector("ul.row-children"));

        if (!child.isDisplayed()) {
            row.findElement(By.cssSelector("span.source")).click();
        }

        List<WebElement> children = child.findElements(By.cssSelector("li"));

        if (children != null && !children.isEmpty()) {
            List<RealTimeItem> details = getRealTimeChildItems(children);

            item.setDetail(details);
        }
        return item;
    }

    private static List<RealTimeItem> getRealTimeChildItems(List<WebElement> rows) {
        List<RealTimeItem> sourceList = null;
        if (rows != null && !rows.isEmpty()) {
            sourceList = Lists.newArrayList();

            for (WebElement row : rows) {
                RealTimeSourceItem item = getRealTimeChildSourceItem(row);
                if (item != null) {
                    sourceList.add(item);
                }
            }
        }
        return sourceList;
    }

    private static RealTimeSourceItem getRealTimeChildSourceItem(WebElement row) {
        String from = row.findElement(By.cssSelector("span.source")).getText();
        if (StringUtil.isNotBlank(from)) {
            RealTimeSourceItem item = new RealTimeSourceItem();

            String percent = row.findElement(By.cssSelector("span.percent")).getText();
            if (StringUtil.isNotBlank(percent) && percent.contains("%")) {
                percent = percent.replace("%", "");
            }
            String pv = row.findElement(By.cssSelector("span.pv")).getText();

            item.setSource(from);
            if (StringUtil.isNotBlank(percent)) {
                item.setPercent(Double.valueOf(percent));
            }
            if (StringUtil.isNotBlank(pv)) {
                item.setVisitor(Integer.valueOf(pv));
            }
            return item;
        }
        return null;
    }

    public RealTimeData extractRegion(WebDriver driver) {
        WebElement root = driver.findElement(By.id("page-live-source"));

        // 抽取数据
        WebElement region = root.findElement(By.cssSelector("div.component-source-region"));
        RealTimeData realTimeData = getRealTimeRegion(region);

        logger.warn(String.format("spider\01region\02%s", CommonHelper.toJson(realTimeData)));

        return realTimeData;
    }

    private static RealTimeData getRealTimeRegion(WebElement element) {
        RealTimeData sourceDO = new RealTimeData();
        String source = element.findElement(By.cssSelector("h4.navbar-header > span")).getText();
        sourceDO.setPlatform(source);

        String time = element.findElement(By.cssSelector("div.mod-box-update-time > span > em")).getText();
        Date date = CommonHelper.toDate(time, "yyyy-MM-dd HH:mm:ss");
        sourceDO.setTime(date);

        List<WebElement> rows = element.findElements(By.cssSelector("dl.map-rank-list > dd"));

        if (rows != null && !rows.isEmpty()) {
            List<RealTimeItem> sourceList = Lists.newArrayList();
            sourceDO.setSources(sourceList);
            for (WebElement row : rows) {
                RealTimeRegionItem item = new RealTimeRegionItem();
                String from = row.findElement(By.cssSelector("span.col-1")).getText();
                String pv = row.findElement(By.cssSelector("span.col-2")).getText();
                String paid = row.findElement(By.cssSelector("span.col-4")).getText();

                item.setSource(from);
                item.setPaid(Integer.valueOf(paid));
                item.setVisitor(Integer.valueOf(pv));
                sourceList.add(item);
            }
        }
        return sourceDO;
    }

    public void destroy() {
        driver.quit();
    }

}
