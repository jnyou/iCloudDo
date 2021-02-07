package org.jnyou.gmall.orderservice.config;

import com.alibaba.druid.pool.DruidDataSource;
import io.seata.rm.datasource.DataSourceProxy;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.EnableAsync;

import javax.sql.DataSource;

/**
 * 分布式事务seata
 * @Author JnYou
 */
//@Configuration
public class MySeataConfig {

//    @Bean
//	public DataSource dataSource(DataSourceProperties dataSourceProperties){
//		HikariDataSource dataSource = dataSourceProperties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
//		if(StringUtils.hasText(dataSourceProperties.getName())){
//			dataSource.setPoolName(dataSourceProperties.getName());
//		}
//		return new DataSourceProxy(dataSource);
//	}

//    @Bean
//    @ConfigurationProperties(prefix = "spring.datasource")
//    public DruidDataSource druidDataSource() {
//        return new DruidDataSource();
//    }
//
//    /**
//     * 需要将 DataSourceProxy 设置为主数据源，否则事务无法回滚
//     *
//     * @param druidDataSource The DruidDataSource
//     * @return The default datasource
//     */
//    @Primary
//    @Bean("dataSource")
//    public DataSource dataSource(DruidDataSource druidDataSource) {
//        return new DataSourceProxy(druidDataSource);
//    }
}
