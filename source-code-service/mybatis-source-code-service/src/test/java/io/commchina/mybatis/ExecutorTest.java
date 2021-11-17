package io.commchina.mybatis;

import io.commchina.mybatis.entity.UserEntity;
import lombok.SneakyThrows;
import org.apache.ibatis.executor.BatchExecutor;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.ReuseExecutor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.transaction.Transaction;
import org.apache.ibatis.transaction.jdbc.JdbcTransaction;
import org.junit.Test;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * ExecutorTest
 *
 * @program: icoding
 * @description: 执行器
 * @author: Mr.Nanke
 * @create: 2021-11-17 13:47
 **/
public class ExecutorTest extends BaseTest{

    private MappedStatement ms;
    private JdbcTransaction jdbcTransaction;

    /**
    * @Description: 可重用的执行器
    * @Param: []
    * @return: void
    * @Author: Mr.Nanke
    * @Date: 2021/11/17
    */
    @SneakyThrows
    @Test
    public void testReuseExecutor() {
        // 通过factory.openSession().getConnection()实例化JdbcTransaction ，用于构建ReuseExecutor
        jdbcTransaction = new JdbcTransaction(factory.openSession().getConnection());
        ms = configuration.getMappedStatement("io.commchina.mybatis.dao.UserMapper.selectByid");

        ReuseExecutor executor = new ReuseExecutor(configuration, jdbcTransaction);

        List<UserEntity> objects = executor.doQuery(ms, 1, RowBounds.DEFAULT, Executor.NO_RESULT_HANDLER, ms.getBoundSql(1));

        System.out.println(objects.get(0));

        List<UserEntity> userList2 = executor.doQuery(ms, 1, RowBounds.DEFAULT, Executor.NO_RESULT_HANDLER, ms.getBoundSql(1));
        System.out.println(userList2.get(0));


        /**
         * 相同的SQL使用可重用的执行器ReuseExecutor在一次会话中将会缓存对应的PrepareStatement到本地，
         * 缓存的生命周期： 会话有效期
         *
         * 缓存的map对象
         * private final Map<String, Statement> statementMap = new HashMap();
         *
         * Key 是 sql , Value 是 Statement
         *
         * 执行过程：executor.doQuery ----> prepareStatement(handler, ms.getStatementLog())
         *
         * 源码：
         *   private Statement prepareStatement(StatementHandler handler, Log statementLog) throws SQLException {
         *     Statement stmt;
         *     BoundSql boundSql = handler.getBoundSql();
         *     String sql = boundSql.getSql();
         *     if (hasStatementFor(sql)) {
         *       stmt = getStatement(sql);
         *       applyTransactionTimeout(stmt);
         *     } else {
         *       Connection connection = getConnection(statementLog);
         *       stmt = handler.prepare(connection, transaction.getTimeout());
         *       putStatement(sql, stmt);
         *     }
         *     handler.parameterize(stmt);
         *     return stmt;
         *   }
         *
         *
         * 注意这个缓存的声明周期 是仅限于本次会话。 会话结束后，这些缓存都会被销毁掉。
         *
         * 区别于SimpleExecutor的实现，多了个本地缓存。 推荐使用ReuseExecutor 。
         */
    }

    /**
    * @Description: 批处理执行器
    * @Param: []
    * @return: void
    * @Author: Mr.Nanke
    * @Date: 2021/11/17
     *
     * BatchExecutor 仅对修改操作（包括删除）有效哈 ，对 select操作是不起作用。
     *
     * BatchExecutor 主要是用于做批量更新操作的 ,底层会调用Statement的 executeBatch()方法实现批量操作
     *
    */
    @Test
    public void testBatchExecutor() throws SQLException {
        // 通过factory.openSession().getConnection()实例化JdbcTransaction ，用于构建BatchExecutor
        jdbcTransaction = new JdbcTransaction(factory.openSession().getConnection());

        // 实例化BatchExecutor
        BatchExecutor executor = new BatchExecutor(configuration, jdbcTransaction);

        // 映射SQL
        ms = configuration.getMappedStatement("io.commchina.mybatis.dao.UserMapper.selectByid");

        Map map = new HashMap();
        map.put("arg0",1);
        map.put("arg1","222");

        // 调用doUpdate
        executor.doUpdate(ms,map);
        executor.doUpdate(ms,map);

        // 刷新
        executor.doFlushStatements(false);
        // 提交  否则不生效
        executor.commit(true);

    }
}