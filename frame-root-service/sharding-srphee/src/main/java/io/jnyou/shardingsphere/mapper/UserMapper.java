package io.jnyou.shardingsphere.mapper;

import io.jnyou.shardingsphere.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * 代码千万行，注释第一行，
 * 注释不规范，同事泪两行。
 *
 * @author JnYou
 */
@Component
public interface UserMapper {

    User searchUser(@Param("username") String username);
}
