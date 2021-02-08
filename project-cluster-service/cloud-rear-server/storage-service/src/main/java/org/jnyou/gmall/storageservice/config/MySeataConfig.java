package org.jnyou.gmall.storageservice.config;

import com.zaxxer.hikari.HikariDataSource;
import io.seata.rm.datasource.DataSourceProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;


/**
 * 代码千万行，注释第一行
 * 注释不规范，同事泪两行
 * <p>
 * MySeataConfig
 *
 * @version 1.0.0
 * @author: JnYou
 **/
@Configuration
public class MySeataConfig {

//    @Autowired
//    DataSourceProperties dataSourceProperties;

    /**
     * 包装本服务的数据源到seata的代理数据源中
     * @param dataSourceProperties
     * @Author JnYou
     */
//    @Bean
//    public DataSource dataSource(DataSourceProperties dataSourceProperties) {
//        HikariDataSource dataSource = dataSourceProperties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
//        if(StringUtils.hasText(dataSourceProperties.getName())){
//            dataSource.setPoolName(dataSourceProperties.getName());
//        }
//        return new DataSourceProxy(dataSource);
//    }

}