package com.fobov.fobov.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.sqlite.SQLiteDataSource;

import javax.sql.DataSource;
import java.util.Optional;

/**
 * Classe destinada às configurações do banco de dados
 */
@Configuration
public class Database {
    @Autowired
    Environment env;

    /**
     * Define a URL do banco de dados
     *
     * @return data source dessa URL
     */
    @Bean
    public DataSource dataSource() {
        SQLiteDataSource dataSource = new SQLiteDataSource();
        String url = Optional.ofNullable(env.getProperty("url")).orElse("");
        String dataSourceUrl = !url.isEmpty() ? ("jdbc:sqlite:" + url) :
                "jdbc:sqlite:src/main/resources/database/database.db";
        dataSource.setUrl(dataSourceUrl);
        return dataSource;
    }
}
