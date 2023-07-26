package com.icchance.q91;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan(basePackages = {"com.icchance.q91.mapper"})
@SpringBootApplication
public class Q91Application {
    public static void main(String[] args) {
        SpringApplication.run(Q91Application.class, args);
    }
}
