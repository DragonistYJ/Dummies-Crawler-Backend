package scu.genius.dummiescrawler.core.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class SpiderOutput {

    /**
     * 节点名称
     */
    private String nodeName;

    /**
     * 节点Id
     */
    private String nodeId;

    /**
     * 输出项的名
     */
    private List<String> outputNames = new ArrayList<>();

    /**
     * 输出项的值
     */
    private List<Object> values = new ArrayList<>();

    public void addOutput(String name, Object value) {
        this.outputNames.add(name);
        this.values.add(value);
    }
}
