package scu.genius.dummiescrawler.core.pojo;

/**
 * @author 11214
 * @since 2022/6/1 20:42
 */
public enum TaskStatus {
    RUNNING("正在运行"),
    FAIL("运行失败"),
    SUCCESS("运行完成");

    public final String name;

    TaskStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
