package br.senac.tads.pi4.projetoPI4.BD;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.sql.DriverManager;

@Configuration
public class Data {

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setDriverClassName("com.mysql.cj.jdbc.Driver");
        ds.setUrl("jdbc:mysql://localhost:3306/devspace?useTimezone=true&serverTimezone=UTC&useSSL=false");
        ds.setUsername("root");
        ds.setPassword("");
        return ds;
    }


}
