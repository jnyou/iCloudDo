package io.commchina.mybatis;

import io.commchina.mybatis.entity.UserEntity;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.List;

public class SqlSessionTest  extends  BaseTest{

    private MappedStatement ms;

    @Test
    public void sqlSessionTest(){

        // 获取statement SQL映射
        ms = configuration.getMappedStatement("com.artisan.UserMapper.selectByid");

        // 开启会话  第一个参数 执行器类型  第二个参数 是否自动提交
        SqlSession sqlSession = factory.openSession(ExecutorType.SIMPLE, true);
        // 门面模式 屏蔽了底层调用的复杂用  统一对接sqlSession
        List<UserEntity> users = sqlSession.selectList(ms.getId(), 1);
        System.out.println(users.get(0));

    }
}