package scu.genius.dummiescrawler.crawler.job;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;
import scu.genius.dummiescrawler.core.context.SpiderContextHolder;
import scu.genius.dummiescrawler.core.entity.CrawlerFlow;
import scu.genius.dummiescrawler.core.entity.CrawlerTask;
import scu.genius.dummiescrawler.core.mapper.CrawlerFlowMapper;
import scu.genius.dummiescrawler.core.mapper.CrawlerTaskMapper;
import scu.genius.dummiescrawler.crawler.Spider;
import scu.genius.dummiescrawler.crawler.model.SpiderFlow;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.Date;

/**
 * 爬虫定时执行
 *
 * @author Administrator
 */
@Component
@Slf4j
public class SpiderJob extends QuartzJobBean {
    @Resource
    private Spider spider;
    @Resource
    private CrawlerTaskMapper crawlerTaskMapper;
    @Resource
    private CrawlerFlowMapper crawlerFlowMapper;

    @Value("${spider.job.enable:true}")
    private boolean spiderJobEnable;

    @Value("${spider.workspace}")
    private String workspace;

    @Override
    protected void executeInternal(JobExecutionContext context) {
        if (!spiderJobEnable) {
            return;
        }
        JobDataMap dataMap = context.getMergedJobDataMap();
        SpiderFlow spiderFlow = (SpiderFlow) dataMap.get(SpiderJobManager.JOB_PARAM_NAME);
        if ("1".equalsIgnoreCase(spiderFlow.getEnabled())) {
            run(spiderFlow, context.getNextFireTime());
        }
    }

    public void run(Long crawlerFlowId) {
        CrawlerFlow crawlerFlow = crawlerFlowMapper.selectById(crawlerFlowId);
        run(crawlerFlow, null);
    }

    public void run(CrawlerFlow crawlerFlow, Date nextExecuteTime) {
        CrawlerTask crawlerTask = new CrawlerTask();
        crawlerTask.setFlowId(crawlerFlow.getId());
        crawlerTask.setBeginTime(new Timestamp(System.currentTimeMillis()));
        crawlerTaskMapper.insert(crawlerTask);
        run(crawlerFlow, crawlerTask, nextExecuteTime);
    }

    public void run(CrawlerFlow crawlerFlow, CrawlerTask crawlerTask, Date nextExecuteTime) {
        SpiderJobContext context = null;
        try {
            context = SpiderJobContext.create(this.workspace, crawlerFlow.getId(), crawlerTask.getId(), false);
            SpiderContextHolder.set(context);
            log.info("开始执行任务{}", crawlerFlow.getName());
            spider.run(crawlerFlow, context);
            log.info("执行任务{}完毕，下次执行时间：{}", crawlerFlow.getName(), nextExecuteTime == null ? null : DateFormatUtils.format(nextExecuteTime, "yyyy-MM-dd HH:mm:ss"));
        } catch (Exception e) {
            log.error("执行任务{}出错", crawlerFlow.getName(), e);
        } finally {
            if (context != null) {
                context.close();
            }

            crawlerTask.setEndTime(new Timestamp(System.currentTimeMillis()));
            crawlerTaskMapper.updateById(crawlerTask);
            SpiderContextHolder.remove();
        }
    }
}
