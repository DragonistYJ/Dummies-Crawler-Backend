package scu.genius.dummiescrawler.crawler.job;

import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.stereotype.Component;
import scu.genius.dummiescrawler.core.entity.CrawlerFlow;
import scu.genius.dummiescrawler.crawler.Spider;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 爬虫定时执行管理
 *
 * @author Administrator
 */
@Component
@Slf4j
public class SpiderJobManager {
    private final static String JOB_NAME = "SPIDER_TASK_";

    public final static String JOB_PARAM_NAME = "SPIDER_FLOW";

    @Resource
    private SpiderJob spiderJob;

    @Resource
    private Scheduler scheduler;

    private JobKey getJobKey(String id) {
        return JobKey.jobKey(JOB_NAME + id);
    }

    private TriggerKey getTriggerKey(String id) {
        return TriggerKey.triggerKey(JOB_NAME + id);
    }

    /**
     * 新建定时任务
     *
     * @param crawlerFlow 爬虫流程图
     * @return boolean true/false
     */
    public Date addJob(CrawlerFlow crawlerFlow) {
        try {
            JobDetail job = JobBuilder.newJob(SpiderJob.class).withIdentity(getJobKey(String.valueOf(crawlerFlow.getId()))).build();
            job.getJobDataMap().put(JOB_PARAM_NAME, crawlerFlow);

            CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(crawlerFlow.getCron()).withMisfireHandlingInstructionDoNothing();

            CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(getTriggerKey(String.valueOf(crawlerFlow.getId()))).withSchedule(cronScheduleBuilder).build();

            return scheduler.scheduleJob(job, trigger);
        } catch (SchedulerException e) {
            log.error("创建定时任务出错", e);
            return null;
        }
    }

    public void run(Long crawlerFlowId) {
        Spider.executorInstance.submit(() -> {
            spiderJob.run(crawlerFlowId);
        });
    }

    public boolean remove(Long id) {
        try {
            scheduler.deleteJob(getJobKey(String.valueOf(id)));
            return true;
        } catch (SchedulerException e) {
            log.error("删除定时任务失败", e);
            return false;
        }
    }

}
