package scu.genius.dummiescrawler.api.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import scu.genius.dummiescrawler.core.entity.CrawlerTask;
import scu.genius.dummiescrawler.core.mapper.CrawlerTaskMapper;
import scu.genius.dummiescrawler.core.pojo.Response;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 11214
 * @since 2022/6/1 16:23
 */
@Service
@Slf4j
public class CrawlerTaskService {
    @Resource
    private CrawlerTaskMapper crawlerTaskMapper;

    public Response get(Long id) {
        QueryWrapper<CrawlerTask> query = Wrappers.query();
        query.eq("id", id).eq("enable", true);
        CrawlerTask crawlerTask = crawlerTaskMapper.selectOne(query);
        Response.ResponseBuilder builder = Response.builder();
        if (crawlerTask == null) {
            builder.code(404).message("未查询到相应的爬虫任务");
        } else {
            builder.code(200).message("爬虫任务查询成功").data(crawlerTask);
        }
        return builder.build();
    }

    public Response delete(Long id) {
        QueryWrapper<CrawlerTask> query = Wrappers.query();
        query.eq("id", id).eq("enable", true);
        CrawlerTask crawlerTask = crawlerTaskMapper.selectOne(query);
        Response.ResponseBuilder builder = Response.builder();
        if (crawlerTask == null) {
            builder.code(404).message("未查询到相应的爬虫任务");
        } else {
            UpdateWrapper<CrawlerTask> update = Wrappers.update();
            update.eq("id", id).set("enable", false);
            int row = crawlerTaskMapper.update(null, update);
            if (row == 1) {
                builder.code(200).message("删除爬虫任务成功");
            } else {
                builder.code(500).message("删除爬虫任务失败");
            }
        }
        return builder.build();
    }

    public Response list(Integer page, Integer pageSize, Long flowId) {
        QueryWrapper<CrawlerTask> query = Wrappers.query();
        query.eq("enable", true).eq("flow_id", flowId);
        Page<CrawlerTask> crawlerTaskPage = crawlerTaskMapper.selectPage(new Page<>(page, pageSize), query);
        Map<String, Object> result = new HashMap<>();
        result.put("crawlerTasks", crawlerTaskPage.getRecords());
        result.put("count", crawlerTaskPage.getTotal());
        return Response.builder().code(200).message("查询爬虫任务成功").data(result).build();
    }

    public CrawlerTask insert(Long flowId) {
        CrawlerTask crawlerTask = new CrawlerTask();
        crawlerTask.setFlowId(flowId);
        crawlerTask.setBeginTime(new Timestamp(System.currentTimeMillis()));
        crawlerTaskMapper.insert(crawlerTask);
        return crawlerTask;
    }
}
