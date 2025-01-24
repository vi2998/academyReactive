package it.reactive.torneoDemo.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Configuration
public class ConfigurazioneDB {

//    @Value("${spring.datasource.username}")
//    private String user;
//
//    @Value("${spring.datasource.password}")
//    private String psw;
//
//    @Value("${spring.datasource.url}")
//    private String url;
//
//    Connection con;
//
//    @Bean
//    @Scope("prototype")
//    public Connection init(){
//        try {
//            con = DriverManager.getConnection(url, user, psw);
//            con.setAutoCommit(false);
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//        return con;
//    }

    @Bean
    public PlatformTransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}
