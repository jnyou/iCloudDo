package io.jnyou.shardingsphere.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * 代码千万行，注释第一行，
 * 注释不规范，同事泪两行。
 *
 * @author JnYou
 * @version 1.0.0
 */
@Data
@ToString
public class SysLog {

    private Integer id;

    private Integer operationId;

    private Integer value;

    private LocalDateTime createTime;
}
