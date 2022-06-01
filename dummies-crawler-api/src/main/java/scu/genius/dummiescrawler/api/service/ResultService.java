package scu.genius.dummiescrawler.api.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import scu.genius.dummiescrawler.core.entity.Result;
import scu.genius.dummiescrawler.core.mapper.ResultMapper;
import scu.genius.dummiescrawler.core.pojo.Response;

import javax.annotation.Resource;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 11214
 * @since 2022/6/1 16:34
 */
@Service
@Slf4j
public class ResultService {
    @Resource
    private ResultMapper resultMapper;

    public Response get(Long id) {
        QueryWrapper<Result> query = Wrappers.query();
        query.eq("id", id);
        Result result = resultMapper.selectOne(query);
        Response.ResponseBuilder builder = Response.builder();
        if (result == null) {
            builder.code(404).message("未查询到相应的结果");
        } else {
            String encode = Base64.getEncoder().encodeToString(result.getContent());
            result.setContentString(encode);
            builder.code(200).message("爬虫结果查询成功").data(result);
        }
        return builder.build();
    }

    public Response list(Integer page, Integer pageSize, Long taskId) {
        QueryWrapper<Result> query = Wrappers.query();
        query.select("id", "type", "key", "task_id").eq("task_id", taskId);
        Page<Result> resultPage = resultMapper.selectPage(new Page<>(page, pageSize), query);
        Map<String, Object> result = new HashMap<>();
        result.put("results", resultPage.getRecords());
        result.put("count", resultPage.getTotal());
        return Response.builder().code(200).message("查询爬虫结果成功").data(result).build();
    }
}
