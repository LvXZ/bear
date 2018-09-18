package com.group3;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@MapperScan("com.group3.dao")
@EnableTransactionManagement
@SpringBootApplication
public class BearApplication {

    public static void main(String[] args) {
        SpringApplication.run(BearApplication.class, args);
    }
}
