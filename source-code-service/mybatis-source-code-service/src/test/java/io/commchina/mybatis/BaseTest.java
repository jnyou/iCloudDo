package io.commchina.mybatis;

import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;

/**
 * <p>
 * BaseTest
 *
 * @program: icoding
 * @description:
 * @author: Mr.Nanke
 * @create: 2021-11-16 11:29
 **/
public class BaseTest {

    public SqlSessionFactory factory;
    public Configuration configuration;

    @Before
    public void inin(){
        // 获取构建器
        SqlSessionFactoryBuilder factoryBuilder = new SqlSessionFactoryBuilder();
        // 解析xml ,构建会话工厂
        factory = factoryBuilder.build(SqlSessionTest.class.getResourceAsStream("/mybatis-config.xml"));
        // 获取 configuration 这个大管家
        configuration = factory.getConfiguration();
    }

}