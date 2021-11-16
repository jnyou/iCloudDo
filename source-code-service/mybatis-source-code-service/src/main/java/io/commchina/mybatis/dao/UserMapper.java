package io.commchina.mybatis.dao;

import io.commchina.mybatis.entity.UserEntity;
import org.apache.catalina.User;
import org.apache.ibatis.annotations.Select;

public interface UserMapper {
    @Select({"select * from users where id = #{id}"})
    UserEntity selectByid(Integer id);
}