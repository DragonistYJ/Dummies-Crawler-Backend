package scu.genius.dummiescrawler.crawler.executor.shape;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import scu.genius.dummiescrawler.core.context.SpiderContext;
import scu.genius.dummiescrawler.core.entity.CrawlerFlow;
import scu.genius.dummiescrawler.core.executor.ShapeExecutor;
import scu.genius.dummiescrawler.core.mapper.CrawlerFlowMapper;
import scu.genius.dummiescrawler.core.model.SpiderNode;
import scu.genius.dummiescrawler.crawler.Spider;
import scu.genius.dummiescrawler.crawler.utils.SpiderFlowUtils;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 子流程执行器
 *
 * @author Administrator
 */
@Component
@Slf4j
public class ProcessExecutor implements ShapeExecutor {
    @Resource
    private CrawlerFlowMapper crawlerFlowMapper;
    @Resource
    private Spider spider;

    @Override
    public void execute(SpiderNode node, SpiderContext context, Map<String, Object> variables) {
        String flowId = node.getStringJsonValue("flowId");
        CrawlerFlow crawlerFlow = crawlerFlowMapper.selectById(Long.parseLong(flowId));
        if (crawlerFlow != null) {
            log.info("执行子流程:{}", crawlerFlow.getName());
            SpiderNode root = SpiderFlowUtils.loadXMLFromString(crawlerFlow.getFlowContent());
            spider.executeNode(null, root, context, variables);
        } else {
            log.info("执行子流程:{}失败，找不到该子流程", flowId);
        }
    }

    @Override
    public String supportShape() {
        return "process";
    }

}
