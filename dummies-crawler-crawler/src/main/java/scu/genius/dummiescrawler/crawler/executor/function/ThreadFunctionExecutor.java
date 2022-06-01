package scu.genius.dummiescrawler.crawler.executor.function;

import org.springframework.stereotype.Component;
import scu.genius.dummiescrawler.core.annotation.Comment;
import scu.genius.dummiescrawler.core.annotation.Example;
import scu.genius.dummiescrawler.core.executor.FunctionExecutor;

/**
 * Created on 2019-12-06
 *
 * @author Octopus
 */
@Component
@Comment("thread常用方法")
public class ThreadFunctionExecutor implements FunctionExecutor {
    @Override
    public String getFunctionPrefix() {
        return "thread";
    }

    @Comment("线程休眠")
    @Example("${thread.sleep(1000L)}")
    public static void sleep(Long sleepTime) {
        try {
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
