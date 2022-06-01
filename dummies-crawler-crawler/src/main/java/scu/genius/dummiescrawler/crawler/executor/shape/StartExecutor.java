package scu.genius.dummiescrawler.crawler.executor.shape;

import org.springframework.stereotype.Component;
import scu.genius.dummiescrawler.core.context.SpiderContext;
import scu.genius.dummiescrawler.core.executor.ShapeExecutor;
import scu.genius.dummiescrawler.core.model.SpiderNode;

import java.util.Map;

/**
 * 开始执行器
 *
 * @author Administrator
 */
@Component
public class StartExecutor implements ShapeExecutor {

    @Override
    public void execute(SpiderNode node, SpiderContext context, Map<String, Object> variables) {

    }

    @Override
    public String supportShape() {
        return "start";
    }

}
