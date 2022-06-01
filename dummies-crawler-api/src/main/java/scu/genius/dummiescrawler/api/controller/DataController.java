package scu.genius.dummiescrawler.api.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import scu.genius.dummiescrawler.api.service.CrawlerFlowService;
import scu.genius.dummiescrawler.api.service.CrawlerTaskService;
import scu.genius.dummiescrawler.api.service.ResultService;
import scu.genius.dummiescrawler.core.entity.CrawlerFlow;
import scu.genius.dummiescrawler.core.pojo.Response;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author 11214
 * @since 2022/6/1 15:46
 */
@RestController
public class DataController {
    @Resource
    private CrawlerFlowService crawlerFlowService;
    @Resource
    private CrawlerTaskService crawlerTaskService;
    @Resource
    private ResultService resultService;

    @PostMapping("/crawler-flow/{id}/get")
    public Mono<Response> crawlerFlowGet(@PathVariable("id") Long id) {
        Response response = crawlerFlowService.get(id);
        return Mono.just(response);
    }

    @PostMapping("/crawler-flow/list")
    public Mono<Response> crawlerFlowList(@RequestBody Map<String, String> body) {
        String userId = body.get("userId");
        if (userId == null) {
            return Mono.just(Response.builder().code(400).message("缺少关键参数:userId").build());
        }

        Integer page = Integer.valueOf(body.getOrDefault("page", "0"));
        Integer pageSize = Integer.valueOf(body.getOrDefault("pageSize", "1"));
        String name = body.get("name");
        String startTime = body.get("startTime");
        String endTime = body.get("endTime");
        Response response = crawlerFlowService.list(page, pageSize, name, startTime, endTime, Long.valueOf(userId));
        return Mono.just(response);
    }

    @PostMapping("/crawler-flow/{id}/delete")
    public Mono<Response> crawlerFlowDelete(@PathVariable("id") Long id) {
        Response response = crawlerFlowService.delete(id);
        return Mono.just(response);
    }

    @PostMapping("/crawler-flow/{id}/update")
    public Mono<Response> crawlerFlowUpdate(@PathVariable("id") Long id, @RequestBody CrawlerFlow crawlerFlow) {
        if (crawlerFlow.getId() != null && !id.equals(crawlerFlow.getId())) {
            return Mono.just(Response.builder().code(400).message("id与实体id不匹配").build());
        }
        Response response = crawlerFlowService.update(id, crawlerFlow);
        return Mono.just(response);
    }

    @PostMapping("/crawler-flow/insert")
    public Mono<Response> crawlerFlowInsert(@RequestBody CrawlerFlow crawlerFlow) {
        Response response = crawlerFlowService.insert(crawlerFlow);
        return Mono.just(response);
    }

    @PostMapping("/crawler-task/{id}/get")
    public Mono<Response> crawlerTaskGet(@PathVariable("id") Long id) {
        Response response = crawlerTaskService.get(id);
        return Mono.just(response);
    }

    @PostMapping("/crawler-task/list")
    public Mono<Response> crawlerTaskList(@RequestBody Map<String, String> body) {
        String flowId = body.get("flowId");
        if (flowId == null) {
            return Mono.just(Response.builder().code(400).message("缺少关键参数:flowId").build());
        }

        Integer page = Integer.valueOf(body.getOrDefault("page", "0"));
        Integer pageSize = Integer.valueOf(body.getOrDefault("pageSize", "1"));
        Response response = crawlerTaskService.list(page, pageSize, Long.valueOf(flowId));
        return Mono.just(response);
    }

    @PostMapping("/crawler-task/{id}/delete")
    public Mono<Response> crawlerTaskDelete(@PathVariable("id") Long id) {
        Response response = crawlerTaskService.delete(id);
        return Mono.just(response);
    }

    @PostMapping("/result/{id}/get")
    public Mono<Response> resultGet(@PathVariable("id") Long id) {
        Response response = resultService.get(id);
        return Mono.just(response);
    }

    @PostMapping("/result/list")
    public Mono<Response> resultList(@RequestBody Map<String, String> body) {
        String taskId = body.get("taskId");
        if (taskId == null) {
            return Mono.just(Response.builder().code(400).message("缺少关键参数:taskId").build());
        }

        Integer page = Integer.valueOf(body.getOrDefault("page", "0"));
        Integer pageSize = Integer.valueOf(body.getOrDefault("pageSize", "1"));
        Response response = resultService.list(page, pageSize, Long.valueOf(taskId));
        return Mono.just(response);
    }
}

