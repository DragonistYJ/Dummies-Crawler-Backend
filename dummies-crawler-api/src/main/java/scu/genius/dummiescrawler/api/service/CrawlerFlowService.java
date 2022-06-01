package scu.genius.dummiescrawler.api.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import scu.genius.dummiescrawler.core.entity.CrawlerFlow;
import scu.genius.dummiescrawler.core.mapper.CrawlerFlowMapper;
import scu.genius.dummiescrawler.core.pojo.Response;

import javax.annotation.Resource;
import java.util.HashMap;
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
}
