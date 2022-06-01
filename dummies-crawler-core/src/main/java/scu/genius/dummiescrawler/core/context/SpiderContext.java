package scu.genius.dummiescrawler.core.context;

import lombok.Getter;
import lombok.Setter;
import scu.genius.dummiescrawler.core.concurrent.SpiderFlowThreadPoolExecutor;
import scu.genius.dummiescrawler.core.model.SpiderNode;
import scu.genius.dummiescrawler.core.model.SpiderOutput;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 爬虫上下文
 *
 * @author jmxd
 */
@Getter
@Setter
public class SpiderContext extends HashMap<String, Object> {
    private String id = UUID.randomUUID().toString().replace("-", "");

    /**
     * 流程ID
     */
    private Long flowId;

    /**
     * 流程执行线程
     */
    private SpiderFlowThreadPoolExecutor.SubThreadPoolExecutor threadPool;

    /**
     * 根节点
     */
    private SpiderNode rootNode;

    /**
     * 爬虫是否运行中
     */
    private volatile boolean running = true;

    /**
     * Future队列
     */
    private LinkedBlockingQueue<Future<?>> futureQueue = new LinkedBlockingQueue<>();

    /**
     * Cookie上下文
     */
    private CookieContext cookieContext = new CookieContext();

    public List<SpiderOutput> getOutputs() {
        return Collections.emptyList();
    }

    public <T> T get(String key) {
        return (T) super.get(key);
    }

    public <T> T get(String key, T defaultValue) {
        T value = this.get(key);
        return value == null ? defaultValue : value;
    }

    public LinkedBlockingQueue<Future<?>> getFutureQueue() {
        return futureQueue;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public void addOutput(SpiderOutput output) {

    }

    public SpiderFlowThreadPoolExecutor.SubThreadPoolExecutor getThreadPool() {
        return threadPool;
    }

    public void setThreadPool(SpiderFlowThreadPoolExecutor.SubThreadPoolExecutor threadPool) {
        this.threadPool = threadPool;
    }

    public SpiderNode getRootNode() {
        return rootNode;
    }

    public void setRootNode(SpiderNode rootNode) {
        this.rootNode = rootNode;
    }

    public String getId() {
        return id;
    }

    public CookieContext getCookieContext() {
        return cookieContext;
    }

    public void pause(String nodeId, String event, String key, Object value) {
    }

    public void resume() {
    }

    public void stop() {
    }
}
