package com.blithe.cms.config.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.interceptor.DefaultTransactionAttribute;

/**
 * @ClassName RedisDataSoureceTransaction
 * @Description: 包装了redis事务和数据库事务，一起开启事务，一起提交事务，一起回滚事务
 * @Author: 夏小颜
 * @Date: 12:24
 * @Version: 1.0
 **/
@Component
@Scope(ConfigurableListableBeanFactory.SCOPE_PROTOTYPE)
public class RedisDataSoureceTransaction {

    /**
     * 业务的大致模板代码：
     *
     * @authwared
     * private RedisDataSoureceTransaction manualTransaction
     *
     * TransactionStatus transactionStatus = null;
     * try {
     *  // // ####开启手动事务
     *  transactionStatus = manualTransaction.begin();
     *
     *  // 删除或者更新数据库的数据
     *  ...........(业务代码省略)
     *
     *  // 删除或者更新redis的值
     *  .............(业务代码省略)
     *
     *  // #######提交事务
     *  manualTransaction.commit(transactionStatus);
     *
     * } catch (Exception e) {
     *  try {
     *  // 回滚事务
     *  manualTransaction.rollback(transactionStatus);
     *  } catch (Exception e1) {
     *  }
     *
     * }
     */


    @Autowired
    private Redisutil redisUtil;
    /**
     * 数据源事务管理器
     */
    @Autowired
    private DataSourceTransactionManager dataSourceTransactionManager;

    /**
     * 开始事务 采用默认传播行为
     *
     * @return
     */
    public TransactionStatus begin() {
        // 手动begin数据库事务
        TransactionStatus transaction = dataSourceTransactionManager.getTransaction(new DefaultTransactionAttribute());
        redisUtil.begin();//
        return transaction;
    }

    /**
     * 提交事务
     *
     * @param transactionStatus 事务传播行为
     * @throws Exception
     */
    public void commit(TransactionStatus transactionStatus) throws Exception {
        if (transactionStatus == null) {
            throw new Exception("transactionStatus is null");
        }
        // 支持Redis与数据库事务同时提交
        dataSourceTransactionManager.commit(transactionStatus);
        redisUtil.exec();
    }

    /**
     * 回滚事务
     *
     * @param transactionStatus
     * @throws Exception
     */
    public void rollback(TransactionStatus transactionStatus) throws Exception {
        if (transactionStatus == null) {
            throw new Exception("transactionStatus is null");
        }
        dataSourceTransactionManager.rollback(transactionStatus);
        redisUtil.discard();
    }

}