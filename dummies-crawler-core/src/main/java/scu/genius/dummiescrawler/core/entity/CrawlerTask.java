package scu.genius.dummiescrawler.core.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;

/**
 * @author 11214
 * @since 2022/6/1 15:33
 */
@TableName(value = "crawler_task")
@Setter
@Getter
@NoArgsConstructor
@ToString
public class CrawlerTask {
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @TableField(value = "flow_id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long flowId;

    @TableField(value = "begin_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp beginTime;

    @TableField(value = "end_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp endTime;

    @TableField(value = "type")
    private String type;

    @TableField(value = "enable")
    private Boolean enable;
}
