package scu.genius.dummiescrawler.api.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import scu.genius.dummiescrawler.core.entity.CrawlerFlow;
import scu.genius.dummiescrawler.core.entity.CrawlerTask;
import scu.genius.dummiescrawler.core.mapper.CrawlerFlowMapper;
import scu.genius.dummiescrawler.core.model.SpiderOutput;
import scu.genius.dummiescrawler.core.pojo.Response;
import scu.genius.dummiescrawler.crawler.Spider;
import scu.genius.dummiescrawler.crawler.job.SpiderJob;
import scu.genius.dummiescrawler.crawler.job.SpiderJobContext;
import scu.genius.dummiescrawler.crawler.job.SpiderJobManager;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 11214
 * @since 2022/6/1 15:53
 */
@Service
@Slf4j
public class CrawlerFlowService {
    @Resource
    private CrawlerFlowMapper crawlerFlowMapper;
    @Resource
    CrawlerTaskService crawlerTaskService;
    @Value("${spider.workspace}")
    private String workspace;
    @Resource
    private Spider spider;
    @Resource
    private SpiderJob spiderJob;
    @Resource
    private SpiderJobManager spiderJobManager;

    public Response get(Long id) {
        QueryWrapper<CrawlerFlow> query = Wrappers.query();
        query.eq("id", id).eq("enable", true);
        CrawlerFlow crawlerFlow = crawlerFlowMapper.selectOne(query);
        Response.ResponseBuilder builder = Response.builder();
        if (crawlerFlow == null) {
            builder.code(404).message("未查询到相应的爬虫流程");
        } else {
            builder.code(200).message("爬虫流程查询成功").data(crawlerFlow);
        }
        return builder.build();
    }

    public Response list(Integer page, Integer pageSize,
                         String name,
                         String startTime,
                         String endTime,
                         Long userId) {
        QueryWrapper<CrawlerFlow> query = Wrappers.query();
        query.eq("user_id", userId).eq("enable", true);
        if (name != null) {
            query.like("name", name);
        }
        if (startTime != null) {
            query.le("create_time", startTime);
        }
        if (endTime != null) {
            query.le("end_time", endTime);
        }
        Page<CrawlerFlow> crawlerFlowPage = crawlerFlowMapper.selectPage(new Page<>(page, pageSize), query);
        Map<String, Object> result = new HashMap<>();
        result.put("crawlerFlows", crawlerFlowPage.getRecords());
        result.put("count", crawlerFlowPage.getTotal());
        return Response.builder().code(200).message("查询爬虫流程成功").data(result).build();
    }

    public Response delete(Long id) {
        QueryWrapper<CrawlerFlow> query = Wrappers.query();
        query.eq("id", id).eq("enable", true);
        CrawlerFlow crawlerFlow = crawlerFlowMapper.selectOne(query);
        Response.ResponseBuilder builder = Response.builder();
        if (crawlerFlow == null) {
            builder.code(404).message("未查询到相应的爬虫流程");
        } else {
            UpdateWrapper<CrawlerFlow> update = Wrappers.update();
            update.eq("id", id).set("enable", false);
            int row = crawlerFlowMapper.update(null, update);
            if (row == 1) {
                builder.code(200).message("删除爬虫成功");
            } else {
                builder.code(500).message("删除爬虫失败");
            }
        }
        return builder.build();
    }

    public Response update(Long id, CrawlerFlow crawlerFlow) {
        crawlerFlow.setId(id);
        int row = crawlerFlowMapper.updateById(crawlerFlow);
        Response.ResponseBuilder builder = Response.builder();
        if (row == 1) {
            builder.code(200).message("更新爬虫成功").data(crawlerFlow);
        } else {
            builder.code(500).message("更新爬虫失败");
        }
        return builder.build();
    }

    public Response insert(CrawlerFlow crawlerFlow) {
        int row = crawlerFlowMapper.insert(crawlerFlow);
        Response.ResponseBuilder builder = Response.builder();
        if (row == 1) {
            builder.code(200).message("新建爬虫成功").data(crawlerFlow);
        } else {
            builder.code(500).message("新建爬虫失败");
        }
        return builder.build();
    }

    /**
     * 执行单次任务
     *
     * @param id     crawler-flow id
     * @param params 参数
     * @return Response
     */
    public Response run(Long id, Map<String, Object> params) {
        QueryWrapper<CrawlerFlow> query = Wrappers.query();
        query.eq("id", id).eq("enable", true);
        CrawlerFlow crawlerFlow = crawlerFlowMapper.selectOne(query);
        if (crawlerFlow == null) {
            return Response.builder().code(404).message("未找到该爬虫").build();
        }
        List<SpiderOutput> outputs;
        CrawlerTask crawlerTask = crawlerTaskService.insert(id);
        SpiderJobContext context = SpiderJobContext.create(workspace, id, crawlerTask.getId(), true);
        try {
            outputs = spider.run(crawlerFlow, context, params);
        } catch (Exception e) {
            log.error("执行爬虫失败", e);
            return Response.builder().code(500).message("爬虫运行失败").build();
        } finally {
            context.close();
        }
        return Response.builder().code(200).message("运行爬虫成功").data(outputs).build();
    }

    public Response runAsync(Long id) {
        CrawlerFlow crawlerFlow = crawlerFlowMapper.selectById(id);
        if (crawlerFlow == null) {
            return Response.builder().code(404).message("未找到该爬虫").build();
        }
        CrawlerTask crawlerTask = crawlerTaskService.insert(id);
        Spider.executorInstance.submit(() -> {
            spiderJob.run(crawlerFlow, crawlerTask, null);
        });
        return Response.builder().code(200).message("运行爬虫成功").build();
    }

    public Response startCron(Long id) {
        Response.ResponseBuilder builder = Response.builder();
        spiderJobManager.remove(id);
        CrawlerFlow crawlerFlow = crawlerFlowMapper.selectById(id);
        Date nextExecuteTime = spiderJobManager.addJob(crawlerFlow);
        if (nextExecuteTime != null) {
            builder.code(200).message("执行定时任务成功");
        } else {
            builder.code(500).message("执行定时任务失败");
        }
        return builder.build();
    }
}
