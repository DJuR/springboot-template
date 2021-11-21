package com.jlearn.config;

import org.springframework.boot.jdbc.DataSourceBuilder;

import javax.sql.DataSource;

/**
 * @author dingjuru
 * @date 2021/11/16
 */
//@Configuration
public class DataSourceConfig {

    //@Bean
    public DataSource getDataSource() {
        //DruidDataSource druidDataSource = new DruidDataSource();

        return DataSourceBuilder.create().build();
    }
}
