package com.cashregister.model.dao;

import com.cashregister.model.SettingsConstants;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class DBManager {
    private HikariConfig config;
    private HikariDataSource dataSource;
    private static DBManager instance;

    private DBManager(){
        Properties properties = new Properties();
        try {
            //properties.load(new FileReader(SettingsConstants.DB_SETTINGS));
            properties.load(DBManager.class.getResourceAsStream(SettingsConstants.DB_SETTINGS));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        config = new HikariConfig();

        config.setJdbcUrl( properties.getProperty("connection.url") );
        config.setUsername( properties.getProperty("user.name") );
        config.setPassword( properties.getProperty("user.pwd") );
        config.setDriverClassName( properties.getProperty("driver") );    //org.apache.derby.jdbc.EmbeddedDriver");
        config.addDataSourceProperty(  "maximumPoolSize", properties.getProperty("maximumPoolSize"));
        config.addDataSourceProperty( "cachePrepStmts" , "true" );
        config.addDataSourceProperty( "prepStmtCacheSize" , "250" );
        config.addDataSourceProperty( "prepStmtCacheSqlLimit" , "2048" );
        dataSource = new HikariDataSource( config );
    };
    public static DBManager getInstance(){
        if (instance == null) {
            instance = new DBManager();
        }
        return instance;
    };

    public Connection getConnection() {
        Connection con = null;
        try {
            con = dataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return con;
    }
}
