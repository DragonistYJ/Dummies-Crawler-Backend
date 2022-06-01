package scu.genius.dummiescrawler.core.listener;

import scu.genius.dummiescrawler.core.context.SpiderContext;

public interface SpiderListener {

    /**
     * 开始执行之前
     */
    void beforeStart(SpiderContext context);

    /**
     * 执行完毕之后
     */
    void afterEnd(SpiderContext context);

}
