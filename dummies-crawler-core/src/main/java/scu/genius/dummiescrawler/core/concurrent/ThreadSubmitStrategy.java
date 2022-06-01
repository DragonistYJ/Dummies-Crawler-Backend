package scu.genius.dummiescrawler.core.concurrent;

import scu.genius.dummiescrawler.core.model.SpiderNode;

import java.util.Comparator;

public interface ThreadSubmitStrategy {

    Comparator<SpiderNode> comparator();

    void add(SpiderFutureTask<?> task);

    boolean isEmpty();

    SpiderFutureTask<?> get();
}
