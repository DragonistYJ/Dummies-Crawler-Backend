package scu.genius.dummiescrawler.core.pojo;

/**
 * @author 11214
 * @since 2022/4/19 17:21
 */
public enum TaskTypeEnum {
    CRON("定时任务"),
    ONCE("一次性任务");

    public final String name;

    TaskTypeEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
