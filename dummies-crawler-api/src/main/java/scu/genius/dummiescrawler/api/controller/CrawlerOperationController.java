package scu.genius.dummiescrawler.api.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import scu.genius.dummiescrawler.api.service.CrawlerFlowService;
import scu.genius.dummiescrawler.core.pojo.Response;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author 11214
 * @since 2022/6/1 20:13
 */
@RestController
@RequestMapping("/crawler")
public class CrawlerOperationController {
    @Resource
    private CrawlerFlowService crawlerFlowService;

    @RequestMapping("/run/{id}")
    public Mono<Response> runCrawler(@PathVariable Long id,
                                     @RequestBody(required = false) Map<String, Object> params) {
        Response response = crawlerFlowService.run(id, params);
        return Mono.just(response);
    }

    @RequestMapping("/runAsync/{id}")
    public Mono<Response> runAsyncCrawler(@PathVariable Long id) {
        Response response = crawlerFlowService.runAsync(id);
        return Mono.just(response);
    }
}
