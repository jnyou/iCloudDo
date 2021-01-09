package io.jnyou.springsecurity.mapper;

import io.jnyou.springsecurity.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface UserMapper {

    List<User> list();

    User loadUserByUsername(@Param("username") String username);
}