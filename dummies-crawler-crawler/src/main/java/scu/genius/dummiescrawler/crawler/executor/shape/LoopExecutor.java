package scu.genius.dummiescrawler.crawler.executor.shape;

import org.springframework.stereotype.Component;
import scu.genius.dummiescrawler.core.context.SpiderContext;
import scu.genius.dummiescrawler.core.executor.ShapeExecutor;
import scu.genius.dummiescrawler.core.model.SpiderNode;

import java.util.Map;

/**
 * 循环执行器
 *
 * @author Administrator
 */
@Component
public class LoopExecutor implements ShapeExecutor {

    public static final String LOOP_ITEM = "loopItem";

    public static final String LOOP_START = "loopStart";

    public static final String LOOP_END = "loopEnd";

    @Override
    public void execute(SpiderNode node, SpiderContext context, Map<String, Object> variables) {
    }

    @Override
    public String supportShape() {
        return "loop";
    }
}
