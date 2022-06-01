package scu.genius.dummiescrawler.core.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author 11214
 * @since 2022/6/1 15:39
 */
@TableName(value = "crawler_task")
@Setter
@Getter
@NoArgsConstructor
@ToString
public class Result {
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @TableField(value = "content")
    private byte[] content;

    @TableField(value = "type")
    private String type;

    @TableField(value = "key")
    private String key;

    @TableField(value = "task_id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long taskId;
}
