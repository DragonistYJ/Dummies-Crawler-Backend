package scu.genius.dummiescrawler.core.concurrent;

import scu.genius.dummiescrawler.core.model.SpiderNode;

import java.util.concurrent.FutureTask;

public class SpiderFutureTask<V> extends FutureTask {

    private SpiderFlowThreadPoolExecutor.SubThreadPoolExecutor executor;

    private SpiderNode node;

    public SpiderFutureTask(Runnable runnable, V result, SpiderNode node, SpiderFlowThreadPoolExecutor.SubThreadPoolExecutor executor) {
        super(runnable, result);
        this.executor = executor;
        this.node = node;
    }

    public SpiderFlowThreadPoolExecutor.SubThreadPoolExecutor getExecutor() {
        return executor;
    }

    public SpiderNode getNode() {
        return node;
    }
}
