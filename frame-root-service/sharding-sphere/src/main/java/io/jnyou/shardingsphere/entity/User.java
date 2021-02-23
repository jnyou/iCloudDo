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
 */
@Data
@ToString
public class User {

    private Long id;

    private String name;

    private String username;

    private String password;

    private String status;

    private LocalDateTime createTime;

}
