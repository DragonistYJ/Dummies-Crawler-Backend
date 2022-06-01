package scu.genius.dummiescrawler.api.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import scu.genius.dummiescrawler.api.service.CrawlerFlowService;
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
}
